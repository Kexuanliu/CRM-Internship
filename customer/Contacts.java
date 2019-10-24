package com.xuebei.crm.customer;

import com.google.gson.annotations.Expose;
import com.xuebei.crm.user.GenderEnum;

import java.util.Date;

public class Contacts {

    private final static String NOT_FILLED = "未填";

    @Expose
    private String contactsId;
    @Expose
    private String departmentId;
    @Expose
    private String departmentName;
    @Expose
    private String totalName;
    @Expose
    private String realName;
    @Expose
    private GenderEnum gender;
    @Expose
    private String phone;
    @Expose
    private String wechat;
    @Expose
    private String tel;
    @Expose
    private String QQ;
    @Expose
    private String email;
    @Expose
    private String officeAddr;
    @Expose
    private String profile;
    @Expose
    private String specialRelationship;
    @Expose
    private String typeName;

    @Expose
    private String customerId;

    @Expose
    private String customerName;

    @Expose
    private ContactsType contactsType;
    @Expose
    private Department department;

    @Expose
    private Integer contactsTypeId;
    @Expose
    private String createId;
    @Expose
    private Date createTs;
    @Expose
    private String createName;

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public Integer getContactsTypeId() {
        return contactsTypeId;
    }

    public void setContactsTypeId(Integer contactsTypeId) {
        this.contactsTypeId = contactsTypeId;
    }

    private Integer isExcelImport;

    public Integer getIsExcelImport() {
        return isExcelImport;
    }

    public void setIsExcelImport(Integer isExcelImport) {
        this.isExcelImport = isExcelImport;
    }

    private Integer visitLogId;

    public Integer getVisitLogId() {
        return visitLogId;
    }

    public void setVisitLogId(Integer visitLogId) {
        this.visitLogId = visitLogId;
    }

    private ContactsDept contactsDept;

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }

    public String getTotalName() {
        return totalName;
    }

    public void setTotalName(String totalName) {
        this.totalName = totalName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficeAddr() {
        return officeAddr;
    }

    public void setOfficeAddr(String officeAddr) {
        this.officeAddr = officeAddr;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getSpecialRelationship() {
        return specialRelationship;
    }

    public void setSpecialRelationship(String specialRelationship) {
        this.specialRelationship = specialRelationship;
    }

    public ContactsType getContactsType() {
        return contactsType;
    }

    public void setContactsType(ContactsType contactsType) {
        this.contactsType = contactsType;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public static String getNotFilled() {
        return NOT_FILLED;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public ContactsDept getContactsDept() {
        return contactsDept;
    }

    public void setContactsDept(ContactsDept contactsDept) {
        this.contactsDept = contactsDept;
        this.totalName = contactsDept.toString();
    }

    public String showContactsType() {
        if (contactsType == null || contactsType.getTypeName() == null) {
            return "无职位信息";
        } else {
            return contactsType.getTypeName();
        }
    }

    public String showGender() {
        if (gender == null) {
            return NOT_FILLED;
        } else if (gender == GenderEnum.FEMALE) {
            return "女";
        } else {
            return "男";
        }
    }

    public String showPhone() {
        if (phone == null || phone.trim().equals("")) {
            return NOT_FILLED;
        } else {
            return phone;
        }
    }

    public String showTel() {
        if (tel == null || tel.trim().equals("")) {
            return NOT_FILLED;
        } else {
            return tel;
        }
    }

    public String showWeChat() {
        if (wechat == null || wechat.trim().equals("")) {
            return NOT_FILLED;
        } else {
            return wechat;
        }
    }

    public String showQQ() {
        if (QQ == null || QQ.trim().equals("")) {
            return NOT_FILLED;
        } else {
            return QQ;
        }
    }

    public String showEmail() {
        if (email == null || email.trim().equals("")) {
            return NOT_FILLED;
        } else {
            return email;
        }
    }

    public String showOfficeAddr() {
        if (officeAddr == null || officeAddr.trim().equals("")) {
            return NOT_FILLED;
        } else {
            return officeAddr;
        }
    }

    public String showProfile() {
        if (profile == null || profile.trim().equals("")) {
            return NOT_FILLED;
        } else {
            return profile;
        }
    }

    public String showSpecialRelationship() {
        if (specialRelationship == null || specialRelationship.trim().equals("")) {
            return NOT_FILLED;
        } else {
            return specialRelationship;
        }
    }

    public Boolean isMale() {
        return gender == GenderEnum.MALE;
    }

}
