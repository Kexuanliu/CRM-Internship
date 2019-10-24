/**
 * @author TRY
 * @date 2019/3/12 16:53
 */
package com.xuebei.crm.customer.agent;

import com.google.gson.annotations.Expose;
import com.xuebei.crm.customer.CustomerFromEnum;

import java.util.Date;

public class CrmAgent {

    @Expose
    private String agentId;

    @Expose
    private String companyName;

    @Expose
    private String profile;

    @Expose
    private String website;

    @Expose
    private CustomerFromEnum customerFrom;

    @Expose
    private CustomerLevelEnum customerLevel;

    @Expose
    private CooperationEnum cooperation;

    @Expose
    private CooperationTypeEnum cooperationType;

    @Expose
    private String updaterId;

    @Expose
    private String createId;

    @Expose
    private String createTs;

    @Expose
    private String createName;

    @Expose
    private String province;
    @Expose
    private String city;
    @Expose
    private String region;

    @Expose
    private Integer isExcelImport;

    public Integer getIsExcelImport() {
        return isExcelImport;
    }

    public void setIsExcelImport(Integer isExcelImport) {
        this.isExcelImport = isExcelImport;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public CustomerFromEnum getCustomerFrom() {
        return customerFrom;
    }

    public void setCustomerFrom(CustomerFromEnum customerFrom) {
        this.customerFrom = customerFrom;
    }

    public CustomerLevelEnum getCustomerLevel() {
        return customerLevel;
    }

    public void setCustomerLevel(CustomerLevelEnum customerLevel) {
        this.customerLevel = customerLevel;
    }

    public CooperationEnum getCooperation() {
        return cooperation;
    }

    public void setCooperation(CooperationEnum cooperation) {
        this.cooperation = cooperation;
    }

    public CooperationTypeEnum getCooperationType() {
        return cooperationType;
    }

    public void setCooperationType(CooperationTypeEnum cooperationType) {
        this.cooperationType = cooperationType;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
}
