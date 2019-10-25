package com.xuebei.crm.opportunity;

import com.xuebei.crm.project.ProjectContacts;
import com.xuebei.crm.project.ProjectDetail;
import com.xuebei.crm.project.ProjectService;
import com.xuebei.crm.sample.SampleServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/7/24.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OpportunityServiceImplTest {

    @Autowired
    private OpportunityService opportunityService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SampleServiceImpl sampleService;


    @Test
    public void testQueryOpportunityByAgentId() {
        String user_id = "fb23af43cc5d44bfb6b3e0a9e5144070";
        String agentId = "59ac85a9d123450396be340766a828e6";
        List<Opportunity> res = opportunityService.queryOpportunityByAgentId(user_id, agentId);


    }

    @Test
    public void testProject() {
        String opportunityId = "1";
        ProjectDetail contact = projectService.getProjectDetail(opportunityId);

    }


    @Test
    public void testOpp() {
        OpportunitySearchParam opportunitySearchParam = new OpportunitySearchParam();
        opportunitySearchParam.setScene("fb23af43cc5d44bfb6b3e0a9e5144070");
        opportunitySearchParam.setCurUserId("fb23af43cc5d44bfb6b3e0a9e5144070");
        opportunitySearchParam.setKeyWord("管理员");
        List<String> arr=new ArrayList<>();
        arr.add("000");
        opportunitySearchParam.setSubUserIdWhole(arr);

        List<String> createIds = sampleService.getUserIdsByUserName("管理员1");
        opportunitySearchParam.setCreateIds(createIds);

        List<Opportunity> opportunities = opportunityService.queryOpportunity(opportunitySearchParam);
        System.out.println(opportunities.size());
    }


    @Test
    public void testGetIds() {
        List<String> res = sampleService.getUserIdsByUserName("管理员");
        System.out.println(res.size());
    }

    @Test
    public void testProjectSearch(){

    }
}