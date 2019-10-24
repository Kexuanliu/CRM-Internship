/**
 * @author ZXL
 * @date 2019/6/6 16:43
 */
package com.xuebei.crm.dataRepair;

import com.xuebei.crm.company.CompanyMapper;
import com.xuebei.crm.company.CompanyService;
import com.xuebei.crm.company.CompanyUser;
import com.xuebei.crm.customer.*;
import com.xuebei.crm.journal.Journal;
import com.xuebei.crm.journal.JournalMapper;
import com.xuebei.crm.opportunity.Opportunity;
import com.xuebei.crm.opportunity.OpportunityMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataRepairServiceImpl implements DataRepairService {


    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private OpportunityMapper opportunityMapper;

    private static final String SPLIT_CHARACTER = "/";

    //正书记、副书记、正校长、副校长、正院长、副院长、正主任、副主任、正处长、副处长、老师
    private final static Map<String, Integer> contactTypeMap = new HashMap<>();

    static {
        contactTypeMap.put("正书记", 1);
        contactTypeMap.put("副书记", 2);
        contactTypeMap.put("正校长", 3);
        contactTypeMap.put("副校长", 4);
        contactTypeMap.put("正院长", 5);
        contactTypeMap.put("副院长", 6);
        contactTypeMap.put("正主任", 7);
        contactTypeMap.put("副主任", 8);
        contactTypeMap.put("正处长", 9);
        contactTypeMap.put("副处长", 10);
        contactTypeMap.put("老师", 11);
    }
    /**
     * 此方法用于修复companyUser的relsFullPath的数据
     */
    @Override
    public void repairCompanyUserLeadPath() {
        List<CompanyUser> companyUserList = companyMapper.getAllCompanyUser();
        Map<String, CompanyUser> companyUserMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(companyUserList)) {
            for (CompanyUser companyUser : companyUserList) {
                companyUserMap.put(companyUser.getUserId(), companyUser);
            }
            List<String> arr = new ArrayList();
            for (CompanyUser companyUser : companyUserList) {
                getLeadArr(companyUser.getUserId(), companyUserMap, arr);
                Collections.reverse(arr);
                String path = String.join(SPLIT_CHARACTER, arr);
                companyMapper.repairCompanyUser(companyUser.getUserId(), path);
                arr.clear();
            }
        }
    }

    private void getLeadArr(String userId, Map<String, CompanyUser> companyUserMap, List<String> arr) {
        if (StringUtils.isNotBlank(userId)) {
            arr.add(userId);
            CompanyUser companyUser = companyUserMap.get(userId);
            if (companyUser != null && StringUtils.isNotBlank(companyUser.getLeaderId())) {
                getLeadArr(companyUser.getLeaderId(), companyUserMap, arr);
            }
        }
    }


    /**
     * 此方法用于修复之前职位错乱的问题
     */
    @Override
    public void repairPosition() {
        List<Contacts> contactsList = customerMapper.getAllContacts();//一次获取所有的客户联系人
        for (Contacts item : contactsList) {
            ContactsType contactType = customerMapper.queryContactsTypeById(item.getContactsTypeId());
            if (contactType != null) {
                Integer res = contactTypeMap.get(contactType.getTypeName());
                if (res == null) {
                    res = 11;
                }
                customerMapper.updateContactsType(item.getContactsId(),res);
            }
        }
        //清除contactsType表
        customerMapper.truncateContactsType();
        //重新按照map里数据插入数据
        for (Map.Entry<String, Integer> entry : contactTypeMap.entrySet()) {
            customerMapper.addContactsType(entry.getValue(),entry.getKey());
        }
    }

    /**
     * 此方法用于修复联系人的创建人以及创建时间
     * 使用部门创建时间
     * 如果不存在使用日志最早时间
     * 如果不存在使用默认时间
     * 默认时间2019/1/1
     * 同时更新部门创建时间以及联系人时间
     */
    @Override
    public void repairContacts() {
        List<Contacts> contactsList = customerMapper.getAllContacts();
        for (Contacts item : contactsList) {
            Date createTime = new Date();
            String createId = "";
            String createName = "";
            Department department = customerMapper.queryDepartmentById(item.getDepartmentId());
            //一般来说department不为空, 如果为空可能出问题
            if (department != null) {
                //如果部门本身的创建者ID都不存在, 说明这是之前的数据, 需要查找历史记录
                if (StringUtils.isEmpty(department.getCreateId())) {
                    Journal journal = journalMapper.getFirstVisitRecord(item.getContactsId());
                    if (journal != null) {
                        createName = companyService.getUserNameByCompanyUserId(journal.getUserId());
                        createId = journal.getUserId();
                        createTime = journal.getCreateTs();
                    } else {
                        Customer customer = customerMapper.getCrmCustomerById(department.getCustomer().getCustomerId());
                        createId = customer.getCreateId();
                        createName = companyService.getUserNameByCompanyUserId(createId);
                        createTime = customer.getCreateTime();
                    }
                    if(createTime==null){
                        createTime=new Date(1546272000000L);
                    }
                    customerMapper.repairCustomerDept(item.getContactsId(), createTime, createId, createName);
                } else {
                    createId = department.getCreateId();
                    createName = companyService.getUserNameByCompanyUserId(createId);
                    createTime = department.getCreateTs();
                }
            } else {
                System.out.println("未找到部门,ID:" + item.getDepartmentId());
            }
            if(createTime==null){
                createTime=new Date(1546272000000L);
            }
            customerMapper.repairContacts(item.getContactsId(), createTime, createId, createName);
        }
    }

    /**
    *   修复之前部门缺少的数据
    */
    public void repairDepart(){
//        List<Department> departments=customerMapper.getAllDeparts();
    }


    /**
    *   修复之前历史数据, 商机里代理商只保存了代理商ID
    */
    public void repairSaleOppAgent(){
        List<Opportunity> result =  opportunityMapper.getUpdateOppAgent();
        for(Opportunity item : result){
            String agentName=customerMapper.getAgentNameByAgentId(item.getAgentId());
            opportunityMapper.updateOppAgent(item.getOpportunityId(),agentName);
        }
    }
}
