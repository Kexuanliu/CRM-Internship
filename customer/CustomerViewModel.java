/**
 * @author TRY
 * @date 2019/3/11 15:07
 */
package com.xuebei.crm.customer;

import com.google.gson.annotations.Expose;

public class CustomerViewModel {

    private String customerId;

    private String creatorId;

    private String createTs;

    private String updaterId;

    private String updateTs;

    private String schoolType;

    private String name;

    private String profile;

    private String website;

    private String customerFrom;

    private Integer isExcelImport;

    private String province;

    private String city;

    private String region;

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

    public Integer getIsExcelImport() {
        return isExcelImport;
    }

    public void setIsExcelImport(Integer isExcelImport) {
        this.isExcelImport = isExcelImport;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public void setUpdateTs(String updateTs) {
        this.updateTs = updateTs;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setCustomerFrom(String customerFrom) {
        this.customerFrom = customerFrom;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public String getCreateTs() {
        return createTs;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public String getUpdateTs() {
        return updateTs;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public String getName() {
        return name;
    }

    public String getProfile() {
        return profile;
    }

    public String getWebsite() {
        return website;
    }

    public String getCustomerFrom() {
        return customerFrom;
    }
}
