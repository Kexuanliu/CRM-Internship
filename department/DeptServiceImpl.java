package com.xuebei.crm.department;

import com.xuebei.crm.customer.Contacts;
import com.xuebei.crm.customer.Department;
import com.xuebei.crm.customer.EnclosureApply;
import com.xuebei.crm.customer.EnclosureStatusEnum;
import com.xuebei.crm.customer.OpenSeaWarning;
import com.xuebei.crm.member.Member;
import com.xuebei.crm.member.MemberService;
import com.xuebei.crm.utils.CrmMapBuildUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/8/16.
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private MemberService memberService;

    /**
     * 组织机构列表中显示我申请过的机构.
     *
     * @param customerId
     * @param userId
     * @return
     */
    @Override
    public List<Department> departmentList(String customerId, String userId) {

        List<Department> departmentList = deptMapper.searchDepts(customerId, userId);
        //设置圈地状态
        setEnclosureStatus(departmentList);
        //添加联系人和三级机构
        setSubDeptAndContacts(departmentList);
        setEnclosureStatus(departmentList);
        //按圈地状态分类dept
        EnumMap<EnclosureStatusEnum, List<Department>> deptMap = CrmMapBuildUtils
                .buildEnumListMap(EnclosureStatusEnum.class, departmentList, Department::getEnclosureStatus);
        //下属人员
        List<Member> subMemberList = memberService.searchSubMemberList(userId);
        for (Member member : subMemberList) {
            //下属圈地
            List<Department> subMyDepts = deptMapper.searchMyDepts(customerId, member.getMemberId());
            setEnclosureStatus(subMyDepts);
            setSubDeptAndContacts(subMyDepts);
            for (Department department : subMyDepts) {
                department.setApplyName(member.getMemberName());
            }

            //下属申请
            List<Department> subApplyingDepts = deptMapper.searchApplyingDepts(customerId, member.getMemberId());
            setEnclosureStatus(subApplyingDepts);
            setSubDeptAndContacts(subApplyingDepts);
            for (Department department : subApplyingDepts) {
                department.setApplyName(member.getMemberName());
            }

            deptMap.get(EnclosureStatusEnum.MINE).addAll(subMyDepts);
            deptMap.get(EnclosureStatusEnum.APPLYING).addAll(subApplyingDepts);
        }

        List<Department> rltList = new ArrayList<>();
        rltList.addAll(deptMap.get(EnclosureStatusEnum.MINE));
        rltList.addAll(deptMap.get(EnclosureStatusEnum.APPLYING));
        rltList.addAll(deptMap.get(EnclosureStatusEnum.ENCLOSURE));
        rltList.addAll(deptMap.get(EnclosureStatusEnum.NORMAL));
        return rltList;

    }

    /**
     * 添加二级机构页面中显示我的二级机构.
     *
     * @param customerId
     * @param userId
     * @return
     */
    @Override
    public List<Department> myDepartmentList(String customerId, String userId,List<Member> subMemberList) {
        List<Department> myDepartmentList = new ArrayList<>();
        List<String> ids = memberService.getSubMemberList(userId);
        ids.add(userId);
        if (CollectionUtils.isNotEmpty(ids)) {
            List<Department> subMyDepts = deptMapper.searchDeptsByUsers(customerId, ids);
            setEnclosureStatus(subMyDepts);
            setSubDeptAndContacts(subMyDepts);
            myDepartmentList.addAll(subMyDepts);
        }
        return myDepartmentList;
    }

    @Override
    public Map<String, List<Department>> myDepartmentListSimple(Set<String> customerIdSet) {
        Map<String, List<Department>> res = new HashMap<>();
        List<Department> myDepartmentList = deptMapper.searchDeptsByCustomerIds(customerIdSet);
        for (Department item : myDepartmentList) {
            List<Department> tmp = res.get(item.getCustomerId());
            if (tmp == null) {
                tmp = new ArrayList<>();
                tmp.add(item);
                res.put(item.getCustomerId(), tmp);
            }else{
                tmp.add(item);
            }
        }
        return res;
    }


    @Override
    public List<Department> getLastDepartBySet(Set<String> customerSet) {
        List<Department> departmentList = deptMapper.getLastDepartBySet(customerSet);
        return departmentList;
    }

    public void setEnclosureStatus(List<Department> deptList) {
        for (Department department : deptList) {
            switch (department.getStatusCd()) {
                case "PERMITTED":
                    department.setEnclosureStatus(EnclosureStatusEnum.MINE);
                    checkOpenSeaWarning(department);
                    break;
                case "APPLYING":
                    department.setEnclosureStatus(EnclosureStatusEnum.APPLYING);
                    break;
                case "EXPIRED":
                case "REJECTED":
                    if (department.getApplyByOthers() == 0) {
                        //未圈
                        department.setEnclosureStatus(EnclosureStatusEnum.NORMAL);
                    } else {
                        department.setEnclosureStatus(EnclosureStatusEnum.ENCLOSURE);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void checkOpenSeaWarning(Department department) {
        EnclosureApply enclosureApply = department.getEnclosureApply();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diffMillis = 0;
        int diffDays = 0;
        int diffHours = 0;
        try {
            Date endTime = dateFormat.parse(enclosureApply.getEndTime());
            diffMillis = (endTime.getTime() - System.currentTimeMillis());
            diffDays = (int) (diffMillis / (1000 * 3600 * 24));
            diffHours = (int) (diffMillis / (1000 * 3600) - diffDays * 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (diffMillis <= 1000 * 3600 * 24 * 7 && diffMillis >= 0) {
            try {
                String delayApplyStatus = deptMapper.delayApplyStatus(department.getDeptId());
                switch (delayApplyStatus) {
                    case "APPLYING":
                    case "REJECTED":
                        OpenSeaWarning openSeaWarning = new OpenSeaWarning();
                        openSeaWarning.setDeptId(department.getDeptId());
                        openSeaWarning.setCreatedTime(enclosureApply.getStartTime());
                        openSeaWarning.setLastTimeFollow(enclosureApply.getUpdateTime());
                        openSeaWarning.setDeptName(department.getDeptName());
                        openSeaWarning.setLeftDays(String.valueOf(diffDays));
                        openSeaWarning.setLeftHours(String.valueOf(diffHours));
                        openSeaWarning.setDelayApplied(true);
                        department.setOpenSeaWarning(openSeaWarning);
                        break;
                    case "PERMITTED":
                    default:
                        break;
                }
            } catch (NullPointerException e) {
                OpenSeaWarning openSeaWarning = new OpenSeaWarning();
                openSeaWarning.setDeptId(department.getDeptId());
                openSeaWarning.setCreatedTime(enclosureApply.getStartTime());
                openSeaWarning.setLastTimeFollow(enclosureApply.getUpdateTime());
                openSeaWarning.setDeptName(department.getDeptName());
                openSeaWarning.setLeftDays(String.valueOf(diffDays));
                openSeaWarning.setLeftHours(String.valueOf(diffHours));
                openSeaWarning.setDelayApplied(false);
                department.setOpenSeaWarning(openSeaWarning);
            }
        }
        if (diffMillis < 0) {
            department.setEnclosureStatus(EnclosureStatusEnum.NORMAL);
            deptMapper.setMyDeptExpired(department.getDeptId());
        }
    }


    @Override
    public Department getDepartmentLink(String departId) {
        Department department=new Department();
        department.setDeptId(departId);
        addSubDeptAndContacts(department, true);
        return department;
    }

    private void setSubDeptAndContacts(List<Department> departmentList) {
        //二级机构
        for (Department department : departmentList) {
            if (department.getEnclosureStatus() == EnclosureStatusEnum.MINE) {
                addSubDeptAndContacts(department, false);
            }
        }
    }

    @Override
    public void addSubDeptAndContacts(Department department, boolean needSub) {
        //二级机构联系人总人数
        Integer contactsNum = 0;
        String deptId = department.getDeptId();
        //三级机构
        List<Department> subDeptList = deptMapper.searchSubDepts(deptId);
        if (subDeptList != null && !subDeptList.isEmpty()) {
            for (Department subDepartment : subDeptList) {
                String subDeptId = subDepartment.getDeptId();
                //三级机构联系人
                List<Contacts> subContactsList = deptMapper.searchContacts(subDeptId);
                if (subContactsList != null && !subContactsList.isEmpty()) {
                    for (Contacts contacts : subContactsList) {
                        String totalName = "";
                        String deptName = department.getDeptName();
                        if (!StringUtils.isEmpty(deptName)) {
                            totalName = deptName + "-" + totalName;
                        }
                        String subDeptName = subDepartment.getDeptName();
                        if (!StringUtils.isEmpty(subDeptName)) {
                            totalName = totalName + "-" + subDeptName;
                        }
                        //合成联系人的部门院校名称
                        totalName = contacts.getCustomerName() + "-" + totalName + "-" + (contacts.getTypeName() != null ? contacts.getTypeName() : "无") + "-" + contacts.getRealName()
                                + ":" + contacts.getContactsId() + ":" + contacts.getCustomerId();
                        contacts.setTotalName(totalName);
                    }
                    subDepartment.setContactsList(subContactsList);
                    //三级机构添加联系人总人数
                    subDepartment.setContactNumber(subContactsList.size());
                    contactsNum += subContactsList.size();
                }
            }
            department.setDepartmentList(subDeptList);
        }
        //二级机构联系人
        List<Contacts> contactsList = null;
        if (needSub) {
            contactsList = deptMapper.searchContactsAll(deptId);
        } else {
            contactsList = deptMapper.searchContacts(deptId);
        }
        if (contactsList != null && !contactsList.isEmpty()) {
            for (Contacts contacts : contactsList) {
                String totalName = "";
                String deptName = department.getDeptName();
                if (!StringUtils.isEmpty(deptName)) {
                    totalName = deptName + "-" + totalName;
                }
                //合成联系人的部门院校名称
                totalName = contacts.getCustomerName() + "-" + totalName + (contacts.getTypeName() != null ? contacts.getTypeName() : "无") + "-" + contacts.getRealName()
                        + ":" + contacts.getContactsId() + ":" + contacts.getCustomerId();
                contacts.setTotalName(totalName);
            }
            department.setContactsList(contactsList);
            contactsNum += contactsList.size();
        }
        //二级机构添加联系人总人数
        department.setContactNumber(contactsNum);
    }

    /**
     * 添加二级机构前的检查
     *
     * @param deptName   编辑的二级机构
     * @param customerId
     * @param userId
     * @return 被我申请过，被别人申请过，没人申请过
     */
    @Override
    public WarningBeforeCreateEnum warningBeforeCreate(String deptName, String customerId, String userId) {
        List<Department> myDepts = deptMapper.searchMyAppliedDepts(customerId, userId);
        List<Department> othersDepts = deptMapper.searchOthersAppliedDepts(customerId, userId);
        for (Department department : myDepts) {
            if (department.getDeptName().equals(deptName)) {
                return WarningBeforeCreateEnum.APPLY_BY_ME;
            }
        }
        for (Department department : othersDepts) {
            if (department.getDeptName().equals(deptName)) {
                return WarningBeforeCreateEnum.APPLY_BY_OTHERS;
            }
        }
        return WarningBeforeCreateEnum.NO_ONE_APPLY;
    }

    @Override
    public void enclosureApply(String deptId, String userId, String reasons) {
        deptMapper.applyDepartment(deptId, userId, reasons);
    }

    @Override
    public void enclosureDelayApply(String deptId, String userId, String reasons) {
        deptMapper.delayApplyDepartment(deptId, userId, reasons);
    }

    @Override
    public boolean filterDepartmentList(List<Department> departmentList, String keyword) {
        departmentList.removeIf(department -> {
            if (department.getDeptName().contains(keyword)) {
                return false;
            } else {
                if (!CollectionUtils.isEmpty(department.getContactsList())) {
                    department.getContactsList().removeIf(contacts -> !contacts.getRealName().contains(keyword)
                            && (contacts.getTypeName() == null || !contacts.getTypeName().contains(keyword)));
                }
                if (!CollectionUtils.isEmpty(department.getDepartmentList())) {
                    filterDepartmentList(department.getDepartmentList(), keyword);
                }
                return CollectionUtils.isEmpty(department.getDepartmentList())
                        && CollectionUtils.isEmpty(department.getContactsList());
            }
        });
        return !departmentList.isEmpty();
    }

    /**
     * 获取部门创建人
     *
     * @author zxl
     * @date 18:16 2019/3/15
     * @param customerId: id
     * @return
     * @throws
     * @since
    */
    @Override
    public String getLastDeptCreatName(String customerId) {
        return deptMapper.getLastDeptCreatName(customerId);
    }


    /**
    *   获取所有的学校下的所有二级学院
    */
    @Override
    public List<Department> getAllDepart(String customerId) {
        return deptMapper.getAllDepart(customerId);
    }

    /**
    *   获取最后一次的申请人名称
    */
    @Override
    public String getDepartApplyName(String departId){
       return deptMapper.getLastApplyName(departId);
    }
}
