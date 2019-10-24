package com.xuebei.crm.customer;

import com.xuebei.crm.customer.agent.AgentLinkSimpleModel;
import com.xuebei.crm.customer.agent.AgentLinkViewModel;
import com.xuebei.crm.customer.agent.CrmAgent;
import com.xuebei.crm.customer.agent.CrmAgentLink;
import com.xuebei.crm.exception.DepartmentNameDuplicatedException;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface CustomerService {

    Boolean isUserHasCustomer(String userId, String customerId);

    void addTopDepartment(Department department) throws DepartmentNameDuplicatedException;

    void addSubDepartment(String parentDeptId, String deptName,String userId);

    void newSchool(CustomerViewModel customerViewModel);

    boolean delSchool(String customer_id);

    List<Customer> queryCustomerInfo(String searchWord, String userId);

    List<Department> queryDepartment(String customerId,String userId);

    List querySearchList(List<Department> deptList);

    void enclosureDelayApply(String deptId);

    List<String> searchSchool(String keyword);

    List<Customer> getMyCustomers(String userId);

    List<Customer> getCustomerBySet(Set<String> userIdSet);

    List<Customer> getCommonCustomers(String userId);

    Boolean isDepartmentNameDuplicated(String customerId, String deptName);

    Boolean isSubDepartmentNameDuplicated(String parentDeptId, String deptName);

    String lastFollowTs(String customerId);

    Contacts queryOpportunityDetail(String opportunityId);

    void editSchool(CustomerViewModel customerViewModel);

    boolean newAgent(CrmAgent crmAgent);

    boolean editAgent(CrmAgent crmAgent);

    boolean delAgent(String agentId);

    CrmAgent queryCrmAgent(String agentId);

    List<CrmAgent> queryCrmAgentByIds(String userId, boolean needSub);

    List<CrmAgent> queryCrmAgentByKeyWord(String searchWord);

    boolean newAgentLink(CrmAgentLink crmAgentLink);

    boolean updateCrmAgentLink(CrmAgentLink crmAgentLink);

    boolean delCrmAgentLink(@Param("linkUserId") String linkUserId);

    CrmAgentLink queryCrmAgentLink(@Param("linkUserId")String linkUserId);

    List<CrmAgentLink> queryCrmAgentLinkList(@Param("agentId") String agentId);

    /**
     *
     *
     * @param userId:  用户ID
     * @param needSub: 是否需要下属
     * @return
    */
    List<AgentLinkViewModel> getAgentLinkViewModelList(String userId,boolean needSub);

    AgentLinkSimpleModel getAgentLinkSimpleModel(String agentLinkId);

    List<CrmAgentLink> getCrmAgentLink(List<String> linkUserIds);

    /**
    *  通过名称判断之前是否存在重名的代理商
    */
    boolean checkCrmAgentExistByName(String agentName);

    /**
     * 通过代理商名称获取代理商
     */
    CrmAgent getAgentByAgentName(String agentName);

    /**
     * 通过代理商以及姓名获取联系人
     */
    CrmAgentLink getAgentByAgentIdAndName(String agentId, String linkName);

    /**
     * 通过学校名称获取学校
     */
    Customer getCrmCustomerByName(String schoolName);

    Department getCrmDepartmentByName(String customerId, String departName);

    Contacts getContactsByName(String departId, String name);

    String getAgentNameByAgentId(String agentId);
}
