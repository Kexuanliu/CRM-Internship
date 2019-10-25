/**
 * @author TRY
 * @date 2019/3/11 13:54
 */
package com.xuebei.crm.customer;

import com.google.gson.Gson;
import com.xuebei.crm.customer.agent.CooperationEnum;
import com.xuebei.crm.customer.agent.CooperationTypeEnum;
import com.xuebei.crm.customer.agent.CrmAgent;
import com.xuebei.crm.customer.agent.CustomerLevelEnum;
import com.xuebei.crm.department.DeptMapper;
import com.xuebei.crm.department.DeptService;
import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.journal.JournalService;
import com.xuebei.crm.sample.SampleMapper;
import com.xuebei.crm.utils.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerTest {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SampleMapper sampleMapper;

    @Autowired
    private JournalService journalService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private DeptMapper deptMapper;

    @Test
    public void testSearchDeptsByUsers(){
        String customerId="dd9c419da6d3427b9ec14c46a9b87422";
        List<String> ids=new ArrayList<>();
        ids.add("04a5e608596d4dab9d8a2835f8a1a719");
        ids.add("095a63e650984a3aa7b80901a313cc88");
        ids.add("09ca450d7bfd4e0696f419bcf7513083");
        List<Department> res =  deptMapper.searchDeptsByUsers(customerId,ids);
        System.out.println(res.size());
    }

    @Test
    public void delSchool(){
        System.out.println(customerMapper.delSchool("f36286b3e74a4841b3d18d11914f4546"));
    }

    @Test
    public void newSchool(){
        CustomerViewModel customerViewModel=new CustomerViewModel();
        customerViewModel.setCustomerId(UUIDGenerator.genUUID());
        customerViewModel.setCreatorId("fb23af43cc5d44bfb6b3e0a9e5144070");
        customerViewModel.setCreateTs("");
        customerViewModel.setUpdaterId("fb23af43cc5d44bfb6b3e0a9e5144070");
        customerViewModel.setUpdateTs("");
        customerViewModel.setSchoolType("高职");
        customerViewModel.setName("zzz");
        customerViewModel.setProfile("zxc");
        customerViewModel.setWebsite("2222");
        customerViewModel.setCustomerFrom("转介绍");
        customerService.newSchool(customerViewModel);
    }

    @Test
    public void getCustomer() {
        Customer customer = customerMapper.queryCustomer("47c70eac63dd491e923e8f4537f9e3b7");
        Gson gson = new Gson();
        System.out.println(gson.toJson(customer));
    }

    @Test
    public void newAgent(){
        CrmAgent crmAgent=new CrmAgent();
        crmAgent.setAgentId("123456");
        crmAgent.setCompanyName("1");
        crmAgent.setProfile("2");
        crmAgent.setWebsite("3");
        crmAgent.setCustomerFrom(CustomerFromEnum.EXHIBITION);
        crmAgent.setCustomerLevel(CustomerLevelEnum.EXCELLENT);
        crmAgent.setCooperation(CooperationEnum.MIDDLE);
        crmAgent.setCooperationType(CooperationTypeEnum.PROCUREMENT);
        crmAgent.setCreateId("1");
        crmAgent.setUpdaterId("2");
        crmAgent.setCreateName("1");
//        crmAgent.setLink
        System.out.println(customerMapper.newAgent(crmAgent));
    }

    @Test
    public void test(){
        System.out.println(customerMapper.queryCrmAgentByKeyWord("da").size());

        System.out.println(customerMapper.querySelfCrmAgent(new ArrayList<>()).size());
    }

    @Test
    public void testGetById(){
        System.out.println(sampleMapper.getUserNameById("cc18cd40f5384491afb72510fedb59bb"));
    }

    @Test
    public void testGetCrmAgentLink(){
        List<String> ids=new ArrayList<>();
        ids.add("275ebfe4075442ce9c811422bccbd863");
        ids.add("69f89f93a8e546a9b892ae5bc2d95ada");
        ids.add("6f8fb376b6ac4051b0f7bba2a7eb47ee");
        ids.add("6f8fb376b6ac4051b0f7bba2a7eb47ee");
        System.out.println(customerMapper.getCrmAgentLink(ids).size());
    }

    /**
     * 测试代理商通过销售名字查询
     *
     * @param
     * @return
     * @throws
     * @author zxl
     * @date 10:22 2019/3/22
     * @since
     */
    @Test
    public void testAgentRes() {
        List<CrmAgent> crmAgents = customerMapper.queryCrmAgentByKeyWord("管理员");
        Gson gson=new Gson();
        System.out.println(gson.toJson(crmAgents));
    }

    /**
     * 测试商机通过销售名字查询
     *
     * @param
     * @return
     * @throws
     * @author zxl
     * @date 10:32 2019/3/22
     * @since
     */
    @Test
    public void testOpp() {
        List<Customer> res = customerService.queryCustomerInfo("旭日", "fb23af43cc5d44bfb6b3e0a9e5144070");
        System.out.println(res.size());
    }
}
