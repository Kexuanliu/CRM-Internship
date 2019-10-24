package com.xuebei.crm.department;

import com.xuebei.crm.customer.Department;
import com.xuebei.crm.member.Member;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/8/16.
 */
public interface DeptService {

    //我的组织机构列表中显示的东西，包括我的，待审核，未圈
    List<Department> departmentList(String customerId, String userId);

    //被我圈走的组织机构
    List<Department> myDepartmentList(String customerId, String userId,List<Member> subMemberList);

    WarningBeforeCreateEnum warningBeforeCreate(String deptName,String customerId, String userId);

    void enclosureApply(String deptId,String userId,String reasons);

    void enclosureDelayApply(String deptId, String userId,String reasons);
    void addSubDeptAndContacts(Department department,boolean needSub);
    void setEnclosureStatus(List<Department> deptList);
    boolean filterDepartmentList(List<Department> departmentList, String keyword);

    String getLastDeptCreatName(String customerId);

    Map<String,List<Department>> myDepartmentListSimple(Set<String> customerId);

    List<Department> getLastDepartBySet(Set<String> customerSet);

    //获取机构的联系人
    Department getDepartmentLink(String departId);

    List<Department> getAllDepart(String customerId);

    String getDepartApplyName(String departId);
}
