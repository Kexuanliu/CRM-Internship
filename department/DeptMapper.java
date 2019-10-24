package com.xuebei.crm.department;

import com.xuebei.crm.customer.Contacts;
import com.xuebei.crm.customer.Department;
import com.xuebei.crm.customer.EnclosureApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/8/16.
 */
@Mapper
public interface DeptMapper {

    //客户组织机构列表中显示的机构
    List<Department> searchDepts(@Param("customerId") String customerId,
                                 @Param("userId") String userId);
    // 我的 二级机构 permitted
    List<Department> searchMyDepts(@Param("customerId") String customerId,
                                          @Param("userId") String userId);


    List<Department> searchDeptsByUsers(@Param("customerId") String customerId,
                                   @Param("userIds") List<String> userId);

    List<Department> searchDeptsByCustomerIds(@Param("customerIdSet") Set<String> customerIdSet);
    //待审核 二级机构 applying
    List<Department> searchApplyingDepts(@Param("customerId") String customerId,
                                   @Param("userId") String userId);

    //我的二级机构过期
    void setMyDeptExpired(@Param("deptId") String deptId);
    //二级机构下的三级机构
    List<Department> searchSubDepts(@Param("deptId") String deptId);

    //机构下的直接联系人
    List<Contacts> searchContacts(@Param("deptId") String deptId);

    List<Contacts> searchContactsAll(@Param("deptId") String deptId);

    //别人申请或已经圈走的二级机构  permitted applying
    List<Department> searchOthersAppliedDepts(@Param("customerId") String customerId,
                                       @Param("userId") String userId);

    //我申请过的二级机构 包括permitted  applying rejected
    List<Department> searchMyAppliedDepts(@Param("customerId") String customerId,
                                       @Param("userId") String userId);
    //申请圈地
//    void deleteApplyDepartment(@Param("deptId") String deptId,
//                               @Param("userId") String userId);
    void applyDepartment(@Param("deptId") String deptId,
                         @Param("userId") String userId,
                         @Param("applyReasons") String reasons);

    //公海警告延期申请
    void delayApplyDepartment(@Param("deptId") String deptId,
                              @Param("userId") String userId,
                              @Param("applyReasons") String reasons);

    //对公海警告提出申请后的状态
    String delayApplyStatus(@Param("deptId") String deptId);


    String getLastDeptCreatName(@Param("customerId")String customerId);

    //根据set获取最新的部门
    List<Department> getLastDepartBySet(@Param("customerSet") Set<String> customerSet);

    List<Department> getAllDepart(@Param("customerId") String customerId);

    String getLastApplyName(@Param("departId")String departId);

    List<Department> getDeptNameById(@Param("decisionMakerIds") Set<String> decisionMakerIds);
}
