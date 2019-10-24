package com.xuebei.crm.customer;

import com.xuebei.crm.company.CompanyMapper;
import com.xuebei.crm.customer.agent.AgentLinkSimpleModel;
import com.xuebei.crm.customer.agent.AgentLinkViewModel;
import com.xuebei.crm.customer.agent.CrmAgent;
import com.xuebei.crm.customer.agent.CrmAgentLink;
import com.xuebei.crm.exception.DepartmentNameDuplicatedException;
import com.xuebei.crm.journal.JournalService;
import com.xuebei.crm.sample.SampleService;
import com.xuebei.crm.utils.UUIDGenerator;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private JournalService journalService;

    @Override
    public Boolean isUserHasCustomer(String userId, String customerId) {
        return customerMapper.isUserHasCustomer(userId, customerId);
    }

    /**
     * 商机详情页查询二级学院和客户联系人信息
     * @param opportunityId
     * @return
     */
    @Override
    public Contacts queryOpportunityDetail(String opportunityId){
        return customerMapper.queryOpportunityDetail(opportunityId);
    }


    /**
     * 获取所有的视图列表模型
     *
     * @author zxl
     * @date 10:40 2019/3/19
     * @return
     * @throws
     * @since
    */
    @Override
    public List<AgentLinkViewModel> getAgentLinkViewModelList(String userId, boolean needSub) {
        List<CrmAgent> crmAgentList = queryCrmAgentByIds(userId, needSub);
        List<AgentLinkViewModel> resList = new ArrayList();
        if (crmAgentList != null) {
            for (CrmAgent crmAgent : crmAgentList) {
                AgentLinkViewModel agentLinkViewModel = new AgentLinkViewModel();
                agentLinkViewModel.setCrmAgent(crmAgent);
                List<CrmAgentLink> crmAgentLinkList = queryCrmAgentLinkList(crmAgent.getAgentId());
                agentLinkViewModel.setCrmAgentLinkList(crmAgentLinkList);
                resList.add(agentLinkViewModel);
            }
        }
        return resList;
    }

    /**
     * 获取轻量级代理商联系人模型
     *
     * @author zxl
     * @date 13:57 2019/3/20
     * @return
     * @throws
     * @since
    */
    @Override
    public AgentLinkSimpleModel getAgentLinkSimpleModel(String agentLinkId) {
        AgentLinkSimpleModel agentLinkSimpleModel = new AgentLinkSimpleModel();
        CrmAgentLink crmAgentLink = queryCrmAgentLink(agentLinkId);
        if (crmAgentLink != null) {
            agentLinkSimpleModel.setPosition(crmAgentLink.getLinkPosition());
            agentLinkSimpleModel.setAgentId(crmAgentLink.getAgentId());
            agentLinkSimpleModel.setAgentLinkId(crmAgentLink.getLinkUserId());
            agentLinkSimpleModel.setLinkName(crmAgentLink.getLinkName());
            CrmAgent crmAgent = queryCrmAgent(crmAgentLink.getAgentId());
            agentLinkSimpleModel.setCompanyName(crmAgent.getCompanyName());
        }
        return agentLinkSimpleModel;
    }

    /**
     * 批量获取联系人
     *
     * @param linkUserIds:联系人ID
     * @return
     * @throws
     * @author zxl
     * @date 11:48 2019/3/21
     * @since
     */
    @Override
    public List<CrmAgentLink> getCrmAgentLink(List<String> linkUserIds) {
        List<CrmAgentLink> res = new ArrayList<>();
        if (linkUserIds != null && linkUserIds.size() > 0) {
            res = customerMapper.getCrmAgentLink(linkUserIds);
        }
        return res;
    }

    @Override
    public boolean checkCrmAgentExistByName(String agentName) {
        return customerMapper.checkCrmAgentExistByName(agentName)>0;
    }

    @Override
    public CrmAgent getAgentByAgentName(String agentName) {
        return customerMapper.getAgentByAgentName(agentName);
    }

    @Override
    public CrmAgentLink getAgentByAgentIdAndName(String agentId, String linkName) {
        return customerMapper.getAgentByAgentIdAndName(agentId, linkName);
    }

    @Override
    public Customer getCrmCustomerByName(String schoolName) {
        return customerMapper.getCrmCustomerByName(schoolName);
    }

    @Override
    public Department getCrmDepartmentByName(String customerId, String departName) {
        return customerMapper.getCrmDepartmentByName(customerId, departName);
    }

    @Override
    public Contacts getContactsByName(String departId, String name) {
        return customerMapper.getContactsByName(departId, name);
    }

    @Override
    public String getAgentNameByAgentId(String agentId) {
        return customerMapper.getAgentNameByAgentId(agentId);
    }

    /**
     * 增加二级学院
     *
     * @param department
     * @throws DepartmentNameDuplicatedException
     */
    @Override
    public void addTopDepartment(Department department) throws DepartmentNameDuplicatedException {
        if (customerMapper.isTopDepartNameExist(department.getCustomer().getCustomerId(), department.getDeptName())) {

            throw new DepartmentNameDuplicatedException("二级学院名已存在，请重新输入");
        }

        customerMapper.insertDepartment(department);
    }

    @Override
    public void addSubDepartment(String parentDeptId, String deptName,String userId) {
        Department prtDept = customerMapper.queryDepartmentById(parentDeptId);
        Department dept = new Department();
        dept.setDeptId(UUIDGenerator.genUUID());
        dept.setDeptName(deptName);
        dept.setEnclosureStatus(EnclosureStatusEnum.NORMAL);
        dept.setCustomer(prtDept.getCustomer());
        dept.setParent(prtDept);
        dept.setCreateId(userId);
        String userName = companyMapper.getUserNameByCompanyUserId(userId);
        dept.setCreateName(userName);
        customerMapper.insertDepartment(dept);
    }

    /**
     * 新建学校
     */
    @Override
    public void newSchool(CustomerViewModel customerViewModel) {
        customerMapper.newSchool(customerViewModel);
        String companyId = companyMapper.queryCompanyIdByUserId(customerViewModel.getCreatorId());
        customerMapper.insertCustomerCompanyRelation(customerViewModel.getCustomerId(), companyId);
    }


    /**
     * @author zxl
     * @date
     * @param customerViewModel 视图模型
     * @return
     * @throws
     * @since
    */
    @Override
    public void editSchool(CustomerViewModel customerViewModel) {
        customerMapper.updateSchool(customerViewModel);
    }

    @Override
    public boolean newAgent(CrmAgent crmAgent) {
        return customerMapper.newAgent(crmAgent) > 0;
    }

    @Override
    public boolean editAgent(CrmAgent crmAgent) {
        return customerMapper.updateCrmAgent(crmAgent) > 0;
    }

    @Override
    public boolean delAgent(String agentId) {
        return customerMapper.delCrmAgent(agentId) > 0;
    }

    @Override
    public CrmAgent queryCrmAgent(String agentId) {
        return customerMapper.queryCrmAgent(agentId);
    }

    public List<CrmAgent> queryCrmAgentByIds(String userId, boolean needSub) {
        List<String> userIds = new ArrayList<>();
        userIds.add(userId);
        if (needSub) {
            Set<String> userGroup = journalService.getAllSubordinatesUserId(userId);
            List<String> tmp = new ArrayList<>(userGroup);
            userIds.addAll(tmp);
        }
        if (CollectionUtils.isEmpty(userIds)) {
            return new ArrayList<>();
        }
        return customerMapper.querySelfCrmAgent(userIds);
    }


    @Override
    public List<CrmAgent> queryCrmAgentByKeyWord(String searchWord) {
        return customerMapper.queryCrmAgentByKeyWord(searchWord);
    }

    /**
     * 添加代理商联系人
     *
     * @param crmAgentLink: 代理商联系人
     * @return 成功行数
     * @throws
     * @author zxl
     * @date 17:45 2019/3/13
     * @since
     */
    @Override
    public boolean newAgentLink(CrmAgentLink crmAgentLink) {
        return customerMapper.newAgentLink(crmAgentLink) > 0;
    }

    /**
     * 更新代理商联系人
     *
     * @param crmAgentLink: 联系人模型
     * @return
     * @throws
     * @author zxl
     * @date 17:47 2019/3/13
     * @since
     */
    @Override
    public boolean updateCrmAgentLink(CrmAgentLink crmAgentLink) {
        return customerMapper.updateCrmAgentLink(crmAgentLink) > 0;
    }

    /**
     * 软删除代理商联系人
     *
     * @param linkUserId:id
     * @return
     * @throws
     * @author zxl
     * @date 17:47 2019/3/13
     * @since
     */
    @Override
    public boolean delCrmAgentLink(String linkUserId) {
        return customerMapper.delCrmAgentLink(linkUserId) > 0;
    }

    /**
     * 查询代理商联系人
     *
     * @author zxl
     * @date 17:48 2019/3/13
     * @param linkUserId: id
     * @return
     * @throws
     * @since
     */
    @Override
    public CrmAgentLink queryCrmAgentLink(String linkUserId) {
        return customerMapper.queryCrmAgentLink(linkUserId);
    }

    /**
     * 通过代理商ID获取所有未删除的联系人
     *
     * @author zxl
     * @date 17:49 2019/3/13
     * @param agentId:代理商ID
     * @return
     * @throws
     * @since
     */
    @Override
    public List<CrmAgentLink> queryCrmAgentLinkList(String agentId) {
        return customerMapper.queryCrmAgentLinkList(agentId);
    }

    /**
     * @return 是否成功
     * @throws
     * @author zxl
     * @date 19/03/11
     * @since
     */
    @Override
    public boolean delSchool(String customer_id) {
        int res = customerMapper.delSchool(customer_id);
        return res > 0;
    }


    @Override
    public List<Customer> queryCustomerInfo(String searchWord, String userId) {

        List<Customer> customerList = new ArrayList<>();
        try {
            List<String> createIds = sampleService.getUserIdsByUserName(searchWord);
            customerList = customerMapper.queryCustomerInfo(searchWord, userId,createIds);
            for (Customer customer : customerList) {
                customer.setLastTs(lastFollowTs(customer.getCustomerId()));
            }
            return customerList;
        } catch (Exception e) {
            return customerList;
        }
    }

    @Override
    public List<Department> queryDepartment(String customerId, String userId) {
        List<Department> departmentList = customerMapper.queryDepartment(customerId,userId);
//        for(Department department:departmentList){
//            if(department.getEnclosureApply().getStatusCd().equals("PERMITTED")){
//                List<Department> subDepartmentList = customerMapper.querySubDepartment(department.getDeptId());
//                List<Contacts> contactsList = customerMapper.queryContacts(department.getDeptId());
//                if(subDepartmentList !=null && !subDepartmentList.isEmpty()){
//
//                }
//            }
//
//        }
        List<Contacts> contactsList = customerMapper.queryContacts(customerId);
        for (Department department : departmentList) {
            setEnclosureStatus(userId, department);
        }
        Map<String, Department> departmentMap = new HashMap<>();
        for (Department department : departmentList) {
            departmentMap.put(department.getDeptId(), department);
        }
        for (Contacts contacts : contactsList) {
            String totalName = "";
            String departmentId = contacts.getDepartmentId();
            Department department = departmentMap.get(departmentId);
            Department temp = department;
            while (temp != null) {
                String deptName = temp.getDeptName();
                if (deptName != null && deptName != "") {
                    totalName = deptName + "-" + totalName;
                }
                if (temp.getParent() != null) {
                    String prtId = temp.getParent().getDeptId();
                    temp = departmentMap.get(prtId);
                } else {
                    temp = null;
                }
            }
            //合成联系人的部门院校名称
            totalName = contacts.getCustomerName() + "-" + totalName + (contacts.getTypeName() != null? contacts.getTypeName() : "无") + "-" + contacts.getRealName()
                    + ":" + contacts.getContactsId() + ":" + contacts.getCustomerId();
            contacts.setTotalName(totalName);
            department.addSubContact(contacts);
            department.addContact(1);
        }
        List<Department> rltList = new ArrayList<>();
        for (Department department : departmentList) {
            if (department.getParent() == null) {
                rltList.add(department);
            } else {
                String prtId = department.getParent().getDeptId();
                Department prtDept = departmentMap.get(prtId);
                prtDept.addSubDept(department);
                prtDept.addContact(department.getContactNumber());
            }
        }
        //TODO 判断哪些部门可以显示，哪些部门或联系人可以搜索
        /*for (Department department : rltList) {
            if (department.getEnclosureStatus().equals(EnclosureStatusEnum.MINE) ||
                    department.getEnclosureStatus().equals(EnclosureStatusEnum.NORMAL)) {
                department.setCanUnFold(true);
            }
        }*/
        return rltList;
    }

    private void setEnclosureStatus(String userId, Department department) {
        //该部门收到的最新圈地请求
        EnclosureApply enclosureApply = customerMapper.queryNewEnclosureApply(department.getDeptId());
        if (enclosureApply == null) {
            return;
        }
        //是我的圈地
        if (enclosureApply.getUserId().equals(userId)) {
            //获取圈地开始后所有的拜访记录
            List<Visit> visitList = customerMapper.queryMyVisit(department.getDeptId(), enclosureApply.getStartTime(), userId);
            int diffDays;
            int followTimes = 0;
            String lastTime;
            //从未拜访
            if (visitList.isEmpty()) {
                //比较申请圈地时间与当前时间的间隔
                diffDays = diffDays(enclosureApply.getStartTime());
                lastTime = enclosureApply.getStartTime();
            }
            //拜访过
            else {
                Visit visit = visitList.get(0);
                //比较最新的一次拜访时间与当前时间的间隔
                diffDays = diffDays(visit.getVisitTime());
                followTimes = visitList.size();
                lastTime = visit.getVisitTime();

            }
            //申请圈地后未到83天
            if (diffDays < 83) department.setEnclosureStatus(EnclosureStatusEnum.MINE);
                //距离申请圈地后83天 90天以内
            else if (diffDays >= 83 && diffDays < 90) {
                OpenSeaWarning openSeaWarning = new OpenSeaWarning();
                openSeaWarning.setCreatedTime(enclosureApply.getStartTime());
                openSeaWarning.setFollowTimes(followTimes);
                openSeaWarning.setLastTimeFollow(lastTime);
                openSeaWarning.setDeptName(department.getDeptName());
                openSeaWarning.setDeptId(department.getDeptId());
                department.setEnclosureStatus(EnclosureStatusEnum.MINE);
            }
            //距离申请圈地后超过90天
            else {
                department.setEnclosureStatus(EnclosureStatusEnum.NORMAL);
            }
        }
        //不是我圈的地
        else {
            Visit visit = customerMapper.queryElseVisit(department.getDeptId(), enclosureApply.getStartTime(), userId);
            int diffDays;
            if (visit == null) {
                diffDays = diffDays(enclosureApply.getStartTime());
            } else {
                diffDays = diffDays(visit.getVisitTime());
            }
            if (diffDays < 90) department.setEnclosureStatus(EnclosureStatusEnum.ENCLOSURE);
            else department.setEnclosureStatus(EnclosureStatusEnum.NORMAL);
        }
    }

    private static int diffDays(String visitTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int days = Integer.MAX_VALUE;
        try {
            Date visitDate = format.parse(visitTime);
            days = (int) ((System.currentTimeMillis() - visitDate.getTime()) / (1000 * 3600 * 24));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    @Override
    public List querySearchList(List<Department> deptList) {
        List<String> searchList = new ArrayList<>();
        querySearchList(deptList, searchList);
        return searchList;
    }

    @Override
    public void enclosureDelayApply(String deptId) {
        EnclosureApply enclosureApply = customerMapper.queryNewEnclosureApply(deptId);
        customerMapper.insertEnclosureDelayApply(enclosureApply);
    }

    private void querySearchList(List<Department> deptList, List<String> searchList) {
        for (Department department : deptList) {
            if (department.getEnclosureStatus().equals(EnclosureStatusEnum.MINE) ||
                    department.getEnclosureStatus().equals(EnclosureStatusEnum.NORMAL)) {
                searchList.add(department.getDeptName());
                if (!department.getContactsList().isEmpty() || department.getContactsList() != null) {
                    for (Contacts contacts : department.getContactsList()) {
                        searchList.add(contacts.getRealName());
                    }
                }
                if (!department.getDepartmentList().isEmpty() || department.getDepartmentList() != null) {
                    querySearchList(department.getDepartmentList(), searchList);
                }
            }
        }
    }

    @Override
    public List<String> searchSchool(String keyword) {
        return customerMapper.searchSchool(keyword);
    }


    @Override
    public List<Customer> getMyCustomers(String userId) {

        List<Customer> customerList = new ArrayList<>();
        try {
            customerList = customerMapper.getMyCustomers(userId);
            for (Customer customer : customerList) {
                customer.setLastTs(lastFollowTs(customer.getCustomerId()));
            }
            return customerList;
        } catch (Exception e) {
            return customerList;
        }
    }

    @Override
    public List<Customer> getCustomerBySet(Set<String> userIdSet) {
        if (CollectionUtils.isNotEmpty(userIdSet)) {
            List<Customer> customerList = customerMapper.getCustomerBySet(userIdSet);
            return customerList;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Customer> getCommonCustomers(String userId) {

        List<Customer> customerList = new ArrayList<>();
        try {
            customerList = customerMapper.getCommonCustomers(userId);
            for (Customer customer : customerList) {
                customer.setLastTs(lastFollowTs(customer.getCustomerId()));
            }
            return customerList;
        } catch (Exception e) {
            return customerList;
        }
    }

    @Override
    public Boolean isDepartmentNameDuplicated(String customerId, String deptName) {
        return null;
    }

    @Override
    public String lastFollowTs(String customerId) {
        return customerMapper.lastFollowTs(customerId);
    }

    /**
     * 检查机构名是否重复
     * 检查规则：同一学校（企业）下不能有重名机构
     *
     * @param customerId 客户ID
     * @param deptName   新增机构名
     */
    public Boolean isTopDepartmentNameDuplicated(String customerId, String deptName) {
        return customerMapper.isTopDepartNameExist(customerId, deptName);
    }

    /**
     * 检查子机构名是否重复
     * 创建子机构时，参数只有父机构的ID；由于同一学校（企业）下都不能有重名机构，因此检查机构名重复调用相同的接口
     */
    public Boolean isSubDepartmentNameDuplicated(String parentDeptId, String deptName) {

        return customerMapper.isSubDepartNameExist(parentDeptId, deptName);
    }

}
