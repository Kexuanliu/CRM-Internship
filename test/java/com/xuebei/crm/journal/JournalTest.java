/**
 * @author TRY
 * @date 2019/3/20 17:06
 */
package com.xuebei.crm.journal;

import com.google.gson.Gson;
import com.xuebei.crm.company.CompanyMapper;
import com.xuebei.crm.company.CompanyService;
import com.xuebei.crm.customer.Customer;
import com.xuebei.crm.customer.CustomerMapper;
import com.xuebei.crm.customer.CustomerService;
import com.xuebei.crm.customer.Department;
import com.xuebei.crm.customer.agent.AgentLinkViewModel;
import com.xuebei.crm.dataRepair.DataRepairService;
import com.xuebei.crm.department.DeptService;
import com.xuebei.crm.exception.AuthenticationException;
import com.xuebei.crm.exception.InformationNotCompleteException;
import com.xuebei.crm.opportunity.Opportunity;
import com.xuebei.crm.opportunity.OpportunityService;
import com.xuebei.crm.sample.SampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JournalTest {

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private DeptService deptService;

    @Autowired
    CompanyService companyService;

    @Autowired
    DataRepairService dataRepairService;

    @Autowired
    private JournalService journalService;

    @Autowired
    private CustomerService customerService;

    @Test
    public void agentTest(){
        VisitAgents visitAgents=new VisitAgents();
        visitAgents.setVisitLogId(Integer.parseInt("123"));
        visitAgents.setAgentId("123");
        visitAgents.setAgentLinkId("456");
        visitAgents.setCreateId("123");
        visitAgents.setCreateName("hahah");
        visitAgents.setShowInfo("zxc");
        System.out.println(journalMapper.addVisitAgents(visitAgents));
    }

    @Test
    public void queryAgents() {
        List<VisitAgents> res = journalMapper.queryVisitAgents(221);
        System.out.println(res.size());
    }


    @Test
    public void testGetLastDepartBySet(){
        Set<String> customerSet =new HashSet<>();
        customerSet.add("d2973f82b72e4a959bc3bc8e7ad4ee55");
        customerSet.add("340601d7660c42a8ad4d88cd5d478cd9");
        List<Department> departments=deptService.getLastDepartBySet(customerSet);
        Gson gson=new Gson();
        System.out.println( gson.toJson(departments));
    }



    @Test
    public void testGetUserNameByCompanyUserId(){
        String realName = companyService.getUserNameByCompanyUserId("135fea151a1a420eafe67a68c65319bd");
        System.out.println(realName);
    }


    @Autowired
    private OpportunityService opportunityService;

    @Test
    public void testGetCustomers() {
        System.err.println(new Date());
     //   String userId = "afdcb19f374b42debd5d660a0b91008b";
    //    List<Customer> customerList = opportunityService.getSimpleMyCustomers(userId);

        String departId = "2565e6c3cf7f48479f69a5e7d5df1883";
        Department department =  deptService.getDepartmentLink(departId);
        Gson gson=new Gson();
        System.out.println(gson.toJson(department));
        System.err.println(new Date());
    }


    @Test
    public void testGetSubordinatesOpportunityByAgentAndCustomerList() {

        String userId = "afdcb19f374b42debd5d660a0b91008b";
        Set<String> userGroup = journalService.getAllSubordinatesUserId(userId);
        List<String> agents=new ArrayList<>();
        agents.add("d3c4cdcd4d864ef39a90cba1e185cd4f");
        agents.add("378bbe49de7349c3871cb8b08239d5c2");

        List<String> customerList=new ArrayList<>();
        customerList.add("123");
        List<String> userIds=new ArrayList<>();
        userIds.add("719068b8d3794f92a2ae77810faed543");
        List<Opportunity> list = journalService.getSubordinatesOpportunityByAgentAndCustomerList(userGroup, agents, customerList,userIds);
        System.out.println(list.size());
    }

    @Test
    public void testJournal() {
        String fullPath2 = journalMapper.queryUserFullPathByUserId("b53670c318a649169dd11337a316asdc8d5");
        String userId = "afdcb19f374b42debd5d660a0b91008b";
      //  String userId = "b53670c318a649169dd11337a316c8d5";
        JournalSearchParam param = new JournalSearchParam();
        param.setUserId(userId);
        param.setPageSize(10);
        param.setPageNo(1);
        Long now = System.currentTimeMillis();
        List<Journal> journals = journalService.searchJournalFaster(param);
        Gson gson = new Gson();
        String fasterJson = gson.toJson(journals);
        System.out.println(fasterJson);
        Long after = System.currentTimeMillis();
        System.err.println(after - now);
        System.out.println("===============================================");
        now = System.currentTimeMillis();
        List<Journal> journals1 = journalService.searchJournal(param);
        String normalJson = gson.toJson(journals1);
        after = System.currentTimeMillis();
        System.err.println(after - now);
        System.out.println("===============================================");
        System.out.println(normalJson);
        System.out.println(fasterJson);
        System.err.println(normalJson.equals(fasterJson));

    }

    @Test
    public void testGetChilds() {
        Long begin = System.currentTimeMillis();
        String userId = "b53670c318a649169dd11337a316c8d5";
        String fullPath = journalMapper.queryUserFullPathByUserId(userId);
        Long end = System.currentTimeMillis();
        System.err.println(end - begin);

        begin =System.currentTimeMillis();
        String fullPath2 = journalMapper.queryUserFullPathByUserId("b53670c318a649169dd11337a316asdc8d5");
        end=System.currentTimeMillis();
        System.err.println(end - begin);

        begin =System.currentTimeMillis();
        Set<String> res = journalMapper.querySuberUserIdByFullpath(fullPath);
        end=System.currentTimeMillis();
        System.err.println(end - begin);
     //   Gson gson=new Gson();
    //    System.out.println(gson.toJson(userGroup));
     //   System.out.println("==============================");
    //    System.out.println(gson.toJson(userGroup2));
    }

    @Test
    public void repairCompanyUserData() {
        dataRepairService.repairCompanyUserLeadPath();
    }


    @Test
    public void getAgentListSelef(){
        String userId = "b53670c318a649169dd11337a316c8d5";
        List<AgentLinkViewModel> agentLinkViewModelList = customerService.getAgentLinkViewModelList(userId,true);
        Gson gson=new Gson();
        System.out.println(gson.toJson(agentLinkViewModelList));
    }

    @Autowired
    private CompanyMapper companyMapper;

    private SampleService sampleService;


    public void addExcelJournal() throws AuthenticationException, InformationNotCompleteException {
        String userId = "b53670c318a649169dd11337a316c8d5";
        String companyUser = companyMapper.queryCrmUserId(userId);
        String userName = sampleService.getUserNameById(companyUser);
        Journal journal = new Journal();
        journal.setUserId(userId);
        journal.setUserName(userName);
        VisitRecord visitRecord = new VisitRecord();
        //这里需要创建被选的联系人
        visitRecord.setChosenContacts(new ArrayList<>());
        //这里需要创建被选的代理
        visitRecord.setChosenAgents(new ArrayList<>());
        visitRecord.setVisitResult("20190628插入1");
        //需要创建商机//
        visitRecord.setOpportunity(new Opportunity());

        visitRecord.setContactType(2);
        visitRecord.setVisitType("NORMAL_VISIT");

        List<VisitRecord> visitRecordList = new ArrayList<>();
        visitRecordList.add(visitRecord);

        journalService.createJournal(journal);
    }

    @Autowired
    private CustomerMapper customerMapper;

    @Test
    public void testJournalGetRecordByContactsId(){
        Customer customer = customerMapper.getCrmCustomerById("0ae5a5b098a24faba7bb321438f1d762");
        System.out.println();
    }
}
