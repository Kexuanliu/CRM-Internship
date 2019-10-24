package com.xuebei.crm.journal;

import com.xuebei.crm.customer.Contacts;
import com.xuebei.crm.customer.Department;
import com.xuebei.crm.member.Member;
import com.xuebei.crm.statistics.StatisticsSearchParam;
import com.xuebei.crm.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Mapper
public interface JournalMapper {

    // functions for table journal
    Integer createJournal(Journal journal);

    Journal queryJournalById(@Param("journalId") Integer journalId);

    Boolean userHasJournal(@Param("userId") String userId,
                           @Param("journalId") Integer journalId);

    Integer updateJournal(Journal journal);

    Integer deleteJournal(@Param("userId") String userId,
                          @Param("journalId") Integer journalId);

    List<Journal> findJournalDraft(@Param("userId") String userId);

    List<ManageJournal> getJournalState(@Param("userId") String userId,@Param("monthStart") Date monthStart,@Param("endTs") Date endTs);

    Integer insertRepairDate(@Param("journalId") Integer journalId);

    List<String> queryCompanyUserIds(@Param("companyId") String companyId);

    String queryNameById(@Param("userId") String userId);

    Date queryRepairDate(@Param("journalId")Integer journalId);

    Integer deleteVisitLog(@Param("journalId") Integer journalId);

    Integer insertVisitLog(VisitRecord visitRecord);

    List<VisitRecord> queryVisitLogs(@Param("journalId") Integer journalId);


    Integer deleteVisitContacts(@Param("visitId") Integer visitId);

    Integer insertVisitContacts(@Param("visitId") Integer visitId,
                                @Param("contactsId") String contactsId);

    List<Contacts> queryVisitContacts(@Param("visitId") Integer visitId);

    // functions for table journal_receiver
    Integer deleteJournalReceiver(@Param("journalId") String journalId);

    Integer insertJournalReceiver(@Param("journalId") String journalId,
                                  @Param("receiverId") String receiverId);

    List<User> queryJournalReceiver(@Param("journalId") String journalId);

    Integer receiverDeleteJournal(@Param("journalId") Integer journalId,
                                  @Param("receiverId") String receiverId);

    // function for journal attachment
    Integer insertJournalPatch(@Param("journalId") Integer journalId,
                               @Param("content") String content);

    // other functions
    Boolean isUserSameCompany(@Param("userIdA") String userIdA,
                              @Param("userIdB") String userIdB);

    List<User> queryColleagues(@Param("userId") String userId);

    List<Journal> searchMyJournal(JournalSearchParam param);

    List<Journal> searchReceivedJournal(JournalSearchParam param);


    List<Journal> searchJournalForStatistics(StatisticsSearchParam param);

    /**
     *
     */
    Journal searchJournal(@Param("journalId") String journalId);

    List<User> searchUnread(@Param("journalId") String journalId);

    List<User> searchRead(@Param("journalId") String journalId);

    List<JournalCustomer> queryJournalCustomersByCompanyId(@Param("userId") String userId);

    List<Contacts> queryContactsByCustomerId(@Param("deptId") String deptId);

    List<JournalPatch> queryJournalPatch(@Param("journalId") Integer journalId);

    List<String> querySubordinatesByUserId(@Param("userId") String userId);

    Set<String> querySuberUserIdByFullpath(@Param("fullpath")String fullpath);

    String queryUserFullPathByUserId(@Param("userId")String userId);

    List<String> queryDeptIdByJournalId(@Param("journalId") Integer journalId);

    List<String> queryDeptId(@Param("userId") String userId,
                             @Param("customerId")String customerId);
    
    //客户组织机构列表中显示的机构
    List<Department> searchDepts(@Param("customerId") String customerId,
                                 @Param("userId") String userId);

    void addComment(@Param("comment") JournalComment comment, @Param("userId") String userId);

    List<JournalComment> getComments(@Param("journalId") Integer journalId);
    //添加拜访记录评论
    void addVisitComment(@Param("visitLogComment") VisitLogComment visitLogComment, @Param("userId") String userId);
    //获取拜访记录列表
    List<VisitLogComment> getVisitLogComments(@Param("visitLogId") Integer visitLogId);

    List<VisitAgentViewModel> getVisitAgentViewModelList(String agentId);

    Integer addVisitAgents(VisitAgents visitAgents);

    Integer deleteVisitAgents(@Param("visitLogId") Integer visitLogId);

    List<VisitAgents> queryVisitAgents(Integer parseInt);

    List<JournalComment> getCommentsByIds(@Param("journalIds") List<Integer> journalIds);

    List<RepairJournal> getRepairJournalList(@Param("journalIds")List<Integer> journalIds);

    List<JournalPatch> getJournalPatchByIds(@Param("journalIds")List<Integer> journalIds);

    List<VisitRecord> queryVisitLogsByIds(@Param("journalIds")List<Integer> journalIds);

    List<VisitRecord> queryVisitLogsByIdsSimple(@Param("journalIds") List<Integer> journalIds);


    List<VisitAgents> queryVisitAgentsByIds(@Param("visitIds")List<Integer> visitIds);

    List<VisitLogComment> getVisitLogCommentsByIds(@Param("visitIds")List<Integer> visitIds);

    List<Contacts> queryVisitContactsByIds(@Param("visitIds")List<Integer> visitIds);

    Journal getFirstVisitRecord(@Param("contactsId") String contactsId);

}
