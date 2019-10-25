package com.xuebei.crm.customer;

import com.xuebei.crm.company.CompanyMapper;
import com.xuebei.crm.journal.JournalService;
import com.xuebei.crm.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


/**
 * Created by Administrator on 2018/7/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Mock
    private JournalService journalService;

    @Mock
    private CompanyMapper companyMapper;

    @Mock
    private CustomerMapper customerMapper;

    MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testGetCustomers() throws Exception {

        User user = new User();
        user.setUserId("ksjdhfs");
        List<Customer> customers = new ArrayList<>();
        when(customerService.getMyCustomers(user.getUserId())).thenReturn(customers);

        String url = "/customer/queryCustomer";
        Customer customer = new Customer();
        customer.setCustomerId("123");

        mockMvc.perform(get(url)
                .param("searchWord","")
                .sessionAttr("userId",customer.getCustomerId()))
                .andExpect(jsonPath("successFlg").value(true))
                .andExpect(jsonPath("commonCustomers").exists())
                .andExpect(jsonPath("customerList").exists());
    }


    @Test
    public void testAddCustomer() throws Exception{
        String url = "/customer/new";
        mockMvc.perform(get(url)
                .param("schoolType",CustomerTypeEnum.COMPANY.getName())
                .param("name","aaaa")
                .param("profile","xxxx")
                .param("website","xxxxzz")
                .param("customerFrom",CustomerFromEnum.EXHIBITION.getName())
                .sessionAttr("userId",""))
                .andExpect(jsonPath("successFlg").value(true))
               ;
    }

}