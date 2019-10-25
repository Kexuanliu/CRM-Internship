package com.xuebei.crm.opportunity;

import com.xuebei.crm.customer.Customer;
import com.xuebei.crm.customer.CustomerMapper;
import com.xuebei.crm.customer.CustomerService;
import com.xuebei.crm.customer.Department;
import com.xuebei.crm.department.DeptService;
import com.xuebei.crm.journal.JournalService;
import com.xuebei.crm.journal.VisitRecord;
import com.xuebei.crm.member.Member;
import com.xuebei.crm.member.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("opportunityService")
public class OpportunityServiceImpl implements OpportunityService {
    @Autowired
    private OpportunityMapper opportunityMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private JournalService journalService;

    @Autowired
    private MemberService memberService;

    @Override
    public List<Customer> getMyCustomers(String userId) {
        List<Customer> customerList = new ArrayList<>();
        try {
            customerList = customerService.getMyCustomers(userId);
            Set<String> members = journalService.getAllSubordinatesUserId(userId);
            for (String member : members) {
                if (!member.equals(userId)) {
                    customerList.addAll(customerService.getMyCustomers(member));
                }
            }
            List<Member> subMemberList = memberService.searchSubMemberList(userId);
            for (Customer customer : customerList) {
                List<Department> departmentList = deptService.myDepartmentList(customer.getCustomerId(), userId,subMemberList);
                customer.setContacts(departmentList);
            }
            return customerList;
        } catch (Exception e) {
            return customerList;
        }
    }

    @Override
    public List<Customer> getSimpleMyCustomers(String userId) {
        List<Customer> customerList = new ArrayList<>();
        try {
            Set<String> members = new HashSet<>(); //journalService.getAllSubordinatesUserId(userId);
            members.add(userId);
            customerList = customerService.getCustomerBySet(members);
            Set<String> customerIdSet = new HashSet<>();
            for (Customer customer : customerList) {
                customerIdSet.add(customer.getCustomerId());
            }
            Map<String, List<Department>> custDepart = deptService.myDepartmentListSimple(customerIdSet);
            for (Customer customer : customerList) {
                List<Department> departmentList = custDepart.get(customer.getCustomerId());
                customer.setContacts(departmentList);
            }
            return customerList;
        } catch (Exception e) {
            return customerList;
        }
    }

    @Override
    public Integer addSale(Opportunity opportunity) {
        if (StringUtils.isNotEmpty(opportunity.getAgentId())) {
            String agentName = customerService.getAgentNameByAgentId(opportunity.getAgentId());
            opportunity.setAgent(agentName);
        }
        Integer opportunityId = opportunityMapper.addSale(opportunity);
        return opportunityId;
    }

    @Override
    public void addOpportunityContact(Integer opportunityId, String contactId) {
        opportunityMapper.addOpportunityContact(opportunityId, contactId);
    }

    @Override
    public List<Opportunity> queryOpportunity(OpportunitySearchParam opportunitySearchParam) {
        List<Opportunity> opportunities = opportunityMapper.queryOpportunity(opportunitySearchParam);
        return opportunities;
    }

    @Override
    public List<Opportunity> queryOpportunityByAgentId(String scene, String agentId) {
        List<Opportunity> opportunities = opportunityMapper.queryOpportunityByAgentId(scene, agentId);
        return opportunities;
    }


    @Override
    public Opportunity opportunityDetail(String opportunityId) {
        return opportunityMapper.opportunityDetail(opportunityId);
    }

    @Override
    public String queryOpportunityCreator(String opportunityId) {
        return opportunityMapper.queryOpportunityCreator(opportunityId);
    }

    @Override
    public void modifyOpportunity(Opportunity opportunity) {
        opportunityMapper.modifyOpportunity(opportunity);
    }

    @Override
    public void addModificationRecord(int opportunityId, String userId,String oldStatus,String newStatus) {
        opportunityMapper.addModificationRecord(opportunityId, userId);
        System.out.println(oldStatus);
        if (oldStatus!=null&&!oldStatus.equals(""))
        {
           opportunityMapper.addStatusRecord(opportunityId,oldStatus,newStatus);
        }
    }

    @Override
    public List<OpportunityModify> queryModifyRecord(int opportunityId) {
        return opportunityMapper.queryModifyRecord(opportunityId);
    }

    @Override
    public List<VisitRecord> queryVisitRecord(int opportunityId) {
        return opportunityMapper.queryVisitRecord(opportunityId);
    }

    @Override
    public List<Support> queryApplySupport(int opportunityId){
      return opportunityMapper.queryApplySupport(opportunityId);
    }


    @Override
    public void insertFailReason(int opportunityId, String failReason) {
        opportunityMapper.insertFailReason(opportunityId, failReason);
    }

    @Override
    public void deleteOpportunity(int opportunityId) {
        opportunityMapper.deleteOpportunity(opportunityId);
    }

    @Override
    public void convertOpportunity(int opportunityId) {
        opportunityMapper.convertOpportunity(opportunityId);
    }

    @Override
    public Opportunity getOppByName(String oppName) {
        return opportunityMapper.getOppByName(oppName);
    }
}