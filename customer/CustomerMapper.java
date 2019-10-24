package com.xuebei.crm.customer;

import com.xuebei.crm.customer.agent.CrmAgent;
import com.xuebei.crm.customer.agent.CrmAgentLink;
import com.xuebei.crm.statistics.StatisticsSearchParam;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Mapper
public interface CustomerMapper {

    Boolean isUserHasCustomer(@Param("userId") String userId,
                              @Param("customerId") String customerId);

    Boolean isTopDepartNameExist(@Param("customerId") String customerId,
                                 @Param("departmentName") String departmentName);

    Boolean isSubDepartNameExist(@Param("prtId")String parentId,
                                 @Param("departmentName")String departmentName);


    Department searchDeptByName(@Param("customerId") String customerId,
                                @Param("departmentName")String departmentName);

    void updateDept(@Param("website")String website,
                          @Param("profile") String profile,
                          @Param("deptId") String deptId);



    void insertDepartment(Department department);

    void newSchool(CustomerViewModel customerViewModel);

    Department queryDepartmentById(@Param("deptId") String deptId);

    ContactsType queryContactsTypeById(@Param("contactsTypeId") Integer contactsTypeId);

    ContactsType queryContactsTypeByName(@Param("typeName") String typeName);

    Contacts queryContactsById(@Param("contactsId") String contactsId);

    void insertContacts(Contacts contacts);

    void updateContacts(Contacts contacts);

    List<Customer> queryCustomerInfo(@Param("searchWord") String searchWord, @Param("userId") String userId, @Param("createIds") List<String> createIds);

    Customer queryCustomer(@Param("customerId") String customerId);

    List<Department> queryDepartment(@Param("customerId") String customerId,
                                     @Param("userId") String userId);

    List<Contacts> queryContacts(@Param("customerId") String customerId);
    Contacts queryContact(@Param("contactsId") String contactsId);
    void updateEnclosureApply(@Param("deptId") String deptId,
                              @Param("userId") String userId);

    void insertEnclosureApply(EnclosureApply enclosureApply);

    void insertEnclosureApplyOver(EnclosureApply enclosureApply);

    List<EnclosureApply> queryEnclosureApply(String deptId);

    EnclosureApply queryNewEnclosureApply(@Param("deptId")String deptId);

    List<Visit> queryMyVisit(@Param("deptId") String deptId,
                            @Param("startTime") String startTime,
                           @Param("userId") String userId
                            );

    Visit queryElseVisit(@Param("deptId") String deptId,
                       @Param("startTime") String startTime,
                       @Param("userId") String userId
    );

    void insertEnclosureDelayApply(EnclosureApply enclosureApply);

    List<String> searchSchool(@Param("keyword")String keyword);

    ContactsDept queryContactsDept(@Param("contactsId") String contactsId);

    List<Customer> getMyCustomers(@Param("userId") String userId);

    List<Customer> getCommonCustomers(@Param("userId") String userId);

    List<FollowUpRecord> queryFollowUpRecordsByContactsId(@Param("contactsId") String contactsId);

    String lastFollowTs(@Param("customerId") String customerId);

    Integer insertCustomerCompanyRelation(@Param("customerId") String customerId,
                                          @Param("companyId") String companyId);


    Integer insertContactsType(@Param("customerId") String customerId,
                               @Param("contactsTypeName") String contactsType);

    Contacts queryOpportunityDetail(String opportunityId);

    List<FollowUpRecord> queryFollowUpRecordsByProjectId(@Param("projectId") String projectId);

    void updateEnclosureApplyEndTs(@Param("userId") String userId,
                                   @Param("deptId") String deptId);

    /**
     * 软删除院校
     *
     * @return 成功行数
     * @throws
     * @author zxl
     * @date 19/03/11
     * @since
     */
    int delSchool(@Param("customerId") String customerId);

    /**
     * 更新学校信息
     *
     * @author zxl
     * @date

     * @return
     * @throws
     * @since
    */
    void updateSchool(CustomerViewModel customerViewModel);

    /**
     * 添加代理商
     *
     * @author zxl
     * @date

     * @return
     * @throws
     * @since
    */
    int newAgent(CrmAgent crmAgent);

    /**
     * 添加代理商联系人
     *
     * @author zxl
     * @date 17:45 2019/3/13
     * @param crmAgentLink: 代理商联系人
     * @return 成功行数
     * @throws
     * @since
    */
    int newAgentLink(CrmAgentLink crmAgentLink);

    /**
     * 更新代理商
     *
     * @author zxl
     * @date

     * @return
     * @throws
     * @since
     */
    int updateCrmAgent(CrmAgent crmAgent);


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
    List<CrmAgentLink> getCrmAgentLink(@Param("linkUserIds")List<String> linkUserIds);
    /**
     * 更新代理商联系人
     *
     * @author zxl
     * @date 17:47 2019/3/13
     * @param crmAgentLink: 联系人模型
     * @return
     * @throws
     * @since
    */
    int updateCrmAgentLink(CrmAgentLink crmAgentLink);

    /**
     * 软删除代理商
     *
     * @author zxl
     * @date

     * @return
     * @throws
     * @since
    */
    int delCrmAgent(@Param("agentId") String agentId);

    /**
     * 软删除代理商联系人
     *
     * @author zxl
     * @date 17:47 2019/3/13
     * @param linkUserId:id
     * @return
     * @throws
     * @since
    */
    int delCrmAgentLink(@Param("linkUserId") String linkUserId);

    /**
     * 获取代理商信息
     *
     * @author zxl
     * @date

     * @return
     * @throws
     * @since
    */
    CrmAgent queryCrmAgent(@Param("agentId") String agentId);

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
    CrmAgentLink queryCrmAgentLink(@Param("linkUserId")String linkUserId);


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
    List<CrmAgentLink> queryCrmAgentLinkList(@Param("agentId") String agentId);

    /**
     * 关键词搜索
     * 
     * @author zxl
     * @date 14:30 2019/3/13
     * @param searchWord: 关键词
     * @return * @return: null
     * @throws 
     * @since 
    */
    List<CrmAgent> queryCrmAgentByKeyWord(@Param("searchWord")String searchWord);

   
    /**
     * @author zxl
     * @date 14:28 2019/3/13
     * @param
     * @return * @return: null
     * @throws 
     * @since 
    */
    List<CrmAgent> querySelfCrmAgent(@Param("userIds") List<String> userIds);

    //通过Set获取Customer
    List<Customer> getCustomerBySet(@Param("userIdSet") Set<String> userIdSet);

    int checkCrmAgentExistByName(@Param("agentName") String agentName);

    CrmAgent getAgentByAgentName(@Param("agentName") String agentName);

    CrmAgentLink getAgentByAgentIdAndName(@Param("agentId") String agentId, @Param("linkName") String linkName);

    Customer getCrmCustomerByName(@Param("customerName") String customerName);

    Customer getCrmCustomerById(@Param("customerId")String customerId);

    Department getCrmDepartmentByName(@Param("customerId") String customerId, @Param("departName") String departName);

    Contacts getContactsByName(@Param("departId") String departId, @Param("name") String name);

    //一次获取所有的创建时间为空的联系人
    List<Contacts> getAllContacts();

    //一次性获取创建时间为空的联系人
    List<Department> getAllDeparts();

    //更新联系人的typeId
    void updateContactsType(@Param("contactsId") String contactsId,@Param("typeId") Integer typeId);
    //清空原始的contactType表
    void truncateContactsType();
    //添加新的contacts表
    void addContactsType(@Param("id") Integer id, @Param("typeName")String typeName);

    void repairCustomerDept(@Param("deptId") String deptId, @Param("createTs") Date createTs, @Param("createId") String createId, @Param("createName") String createName);

    void repairContacts(@Param("contactsId")String contactsId,@Param("createTs") Date createTs, @Param("createId") String createId, @Param("createName") String createName);

    List<CrmAgent> getCrmAgentByParam(StatisticsSearchParam param);

    List<CrmAgentLink> getNameAndPosition(@Param("agentIds") Set<String> agentIds);

    List<Customer> getCollegeNameById(@Param("collegeIds") Set<String> collegeIds);

    String getAgentNameByAgentId(@Param("agentId")String agentId);
}
