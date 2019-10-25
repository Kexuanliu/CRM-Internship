package com.xuebei.crm.project;

import com.xuebei.crm.company.CompanyUser;
import com.xuebei.crm.opportunity.Opportunity;
import com.xuebei.crm.opportunity.Support;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface ProjectMapper {

    Integer insertProject(Project project);

    Integer insertProjectStart(ProjectStart projectStart);

    void insertContract(Contract contract);

    Integer insertRefund(Refund refund);

    void updateProjectStart(ProjectStart projectStart);

    void updateContract(Contract contract);

    void updateRefund(Refund refund);

    void isRefunded(Refund refund);

    Integer getRefundStage(@Param("projectId")Integer projectId ,@Param("isRefunded")Integer isRefunded);

    void updateProgress(@Param("projectId") Integer projectId, @Param("progress") Integer progress);

    void refuseProject(@Param("projectId")Integer projectId);

    void passProject(@Param("projectId")Integer projectId);

    void endProject(@Param("projectId")Integer projectId);

    void updateProjectStatus(@Param("projectId")Integer projectId);

    void refundProject(@Param("projectId")Integer projectId);

    void assignLeader(@Param("projectId")Integer projectId, @Param("leader") String leader);

    ProjectStart getProjectStart(@Param("projectId")Integer projectId, @Param("userId") String userId);

    Contract getContract(Integer projectId);

    List<Refund> getRefunds(@Param("projectId") Integer projectId);

    List<Project> queryProjectsByUserId(String userId);

    List<Opportunity> queryOpportunitiesByUserId(String userId);

    List<Opportunity> queryOpportunitiesByUserIds(@Param("userIds") Set<String> userIds);

    Opportunity queryOpportunity(@Param( "opportunityid" ) Integer opportunityid);
    List<Opportunity> queryOpportunity2(@Param( "contactsId" ) String contactsId);
    String queryOpportunityNameByOpportunityId(Integer opportunityId);

    List<Project> searchProject(ProjectSearchParam param);

    ProjectDetail queryProjectDetailById(String projectId);

    ProjectDetail queryProjectDetailByChargId(@Param("chargeId")String chargeId);

    ProjectDetail queryProjectDetailByMakeId(@Param("makeId")String makeId);

    List<Support> querySupportsByProjectId(String projectId);

    List<ProjectDetail> queryMissionUn(@Param("userId") String userId,@Param("admin") boolean admin,@Param("subUserId") List<String> subUserId);

    List<ProjectDetail> queryMission(@Param("userId") String userId,@Param("admin") boolean admin,@Param("subUserId") List<String> subUserId);

    ProjectDetail queryMissionDetail(@Param("supportId") Integer supportId);

    List<Support> querySupportInformation(@Param("supportId")Integer supportId);

    void insertProgressInform(@Param("supportId")Integer supportId,
                              @Param("userId")String userId,
                              @Param("progress")String progress);

    void updateSupportProgress(@Param("userId")String userId,@Param("supportId")Integer supportId,@Param("progress")Integer progress);

    List<CompanyUser> queryCompanyUser(@Param("userId")String userId,@Param("keyword")String keyword);

    void setSupportLeader(@Param("userId")String userId,@Param("supportId")Integer supportId,@Param("leaderId")String leaderId);

    void modifyProject(Opportunity opportunity);


    //通过代理商以及院校查找商机
    List<Opportunity> getSubordinatesOpportunityByAgentAndCustomerList(@Param("userGroup") Set<String> userGroup,
                                                                       @Param("agents") List<String> agents,
                                                                       @Param("customerList") List<String> customerList,
                                                                       @Param("userIds") List<String> userIds);

    List<Opportunity> getOpportunityByIds(@Param("oppIds")List<Integer> oppIds);

    List<Opportunity> queryOpportunitySimple(@Param("contactsId") String contactsId);
}
