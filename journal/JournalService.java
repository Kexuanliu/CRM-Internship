package com.xuebei.crm.journal;

import com.xuebei.crm.customer.BigCustomer;
import com.xuebei.crm.exception.AuthenticationException;
import com.xuebei.crm.exception.InformationNotCompleteException;
import com.xuebei.crm.opportunity.Opportunity;
import com.xuebei.crm.project.Project;
import com.xuebei.crm.statistics.StatisticsSearchParam;

import java.util.List;
import java.util.Set;

public interface JournalService {

    void createJournal(Journal journal) throws InformationNotCompleteException, AuthenticationException;

    void modifyJournal(Journal journal) throws InformationNotCompleteException, AuthenticationException;

    void deleteJournalById(String userId, Integer journalId) throws AuthenticationException;

    Journal queryJournalById(String userId, Integer journalId) throws AuthenticationException;

    List<Journal> searchJournal(JournalSearchParam param);

    List<Journal> searchJournalFaster(JournalSearchParam param);

    List<Journal> searchJournalInTheSameDay(String journalId);

    List  searchDatail(String journalId);

    List<JournalCustomer> getAllContacts(String companyId,String userId);

    List<ManageJournal> getJournalState(String userId);

    List<FollowJournal> getJournalFollow(String userId);

    List<FollowJournal> getJournalFollow(String userId,int index,float delay,float miss);
    
    List<BigCustomer> getAllCustomers(String companyId,String userId);

    Set<String> getAllSubordinatesUserId(String userId);

    Set<Project> getAllSubordinatesProjects(Set<String> userGroup);

    List<Opportunity> getAllSubordinatesOpportunity(Set<String> userGroup);

    List<VisitAgentViewModel> getVisitAgentViewModelList(String agentId);

    //通过代理商以及院校查找商机
    List<Opportunity> getSubordinatesOpportunityByAgentAndCustomerList(Set<String> userGroup,List<String> agents,List<String> customerList,List<String> userIds );

    List<Journal> searchJournalForStatistics(StatisticsSearchParam param);
}
