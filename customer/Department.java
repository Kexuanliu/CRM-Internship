package com.xuebei.crm.customer;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Department {

    @Expose
    private String deptId;
    @Expose
    private String deptName;
    @Expose
    private String profile;
    @Expose
    private String website;
    @Expose
    private EnclosureStatusEnum enclosureStatus;
    @Expose
    private Customer customer;
    @Expose
    private Department parent;
    @Expose
    private List<Contacts> contactsList = new ArrayList<>();
    @Expose
    private List<Department> departmentList = new ArrayList<>();
    @Expose
    private OpenSeaWarning openSeaWarning;
    @Expose
    private Boolean canUnFold = false;
    @Expose
    private Integer contactNumber = 0;
    @Expose
    private EnclosureApply enclosureApply;
    @Expose
    private Integer applyByOthers;
    @Expose
    private String statusCd;
    @Expose
    private String applyName;
    @Expose
    private String createName;
    @Expose
    private String createId;

    private Date createTs;

    private Integer isExcelImport;

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public Integer getIsExcelImport() {
        return isExcelImport;
    }

    public void setIsExcelImport(Integer isExcelImport) {
        this.isExcelImport = isExcelImport;
    }

    @Expose
    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

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

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public Integer getApplyByOthers() {
        return applyByOthers;
    }

    public void setApplyByOthers(Integer applyByOthers) {
        this.applyByOthers = applyByOthers;
    }

    public EnclosureApply getEnclosureApply() {
        return enclosureApply;
    }

    public void setEnclosureApply(EnclosureApply enclosureApply) {
        this.enclosureApply = enclosureApply;
    }

    public Boolean getCanUnFold() {
        return canUnFold;
    }

    public void setCanUnFold(Boolean canUnFold) {
        this.canUnFold = canUnFold;
    }

    public Integer getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Integer contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void addContact(int contactNumber) {
        this.contactNumber += contactNumber;
    }

    private List<EnclosureApply> enclosureApplyList = new ArrayList<>();

    public List<EnclosureApply> getEnclosureApplyList() {
        return enclosureApplyList;
    }

    public void setEnclosureApplyList(List<EnclosureApply> enclosureApplyList) {
        this.enclosureApplyList = enclosureApplyList;
    }

    public List<Contacts> getContactsList() {
        return contactsList;
    }

    public void setContactsList(List<Contacts> contactsList) {
        this.contactsList = contactsList;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public OpenSeaWarning getOpenSeaWarning() {
        return openSeaWarning;
    }

    public void setOpenSeaWarning(OpenSeaWarning openSeaWarning) {
        this.openSeaWarning = openSeaWarning;
    }


    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public EnclosureStatusEnum getEnclosureStatus() {
        return enclosureStatus;
    }

    public void setEnclosureStatus(EnclosureStatusEnum enclosureStatus) {
        this.enclosureStatus = enclosureStatus;
    }

    public void addSubDept(Department department) {
        if (departmentList == null) {
            departmentList = new ArrayList<>();
        }
        departmentList.add(department);
    }

    public void addSubContact(Contacts contacts) {
        contactsList.add(contacts);
    }

    public void addEnclosureApply(EnclosureApply enclosureApply) {

        enclosureApplyList.add(enclosureApply);
    }

    public String showContactsNumber() {
        if (contactNumber > 0) {
            return "（" + contactNumber + "）";
        } else {
            return "";
        }
    }

    private String decisionMakerId;

    private String decisionMaker;

    public String getDecisionMakerId() { return decisionMakerId; }

    public void setDecisionMakerId(String decisionMakerId) { this.decisionMakerId = decisionMakerId; }

    public String getDecisionMaker() { return decisionMaker; }

    public void setDecisionMaker(String decisionMaker) { this.decisionMaker = decisionMaker; }

}
