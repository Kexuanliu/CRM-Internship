package com.xuebei.crm.customer;

import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.List;

public class Customer {

    @Expose
    private String customerId;
    @Expose
    private String customerName;
    @Expose
    private CustomerTypeEnum customerType;
    @Expose
    private String profile;
    @Expose
    private String website;
    @Expose
    private List warningDetails;
    @Expose
    private List contacts;
    @Expose
    private List customer;
    @Expose
    private String lastTs;
    @Expose
    private String createTs;
    @Expose
    private String createName;

    @Expose
    private String province;

    private Date createTime;

    private String createId;

    @Expose
    private String city;

    @Expose
    private String region;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
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

    @Expose
    private CustomerFromEnum customerFrom;

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerType(CustomerTypeEnum customerType) {
        this.customerType = customerType;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setWarningDetails(List warningDetails) {
        this.warningDetails = warningDetails;
    }

    public void setContacts(List contacts) {
        this.contacts = contacts;
    }

    public void setCustomer(List customer) {
        this.customer = customer;
    }

    public void setLastTs(String lastTs) {
        this.lastTs = lastTs;
    }

    public void setCustomerFrom(CustomerFromEnum customerFrom) {
        this.customerFrom = customerFrom;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public CustomerTypeEnum getCustomerType() {
        return customerType;
    }

    public String getProfile() {
        return profile;
    }

    public String getWebsite() {
        return website;
    }

    public List getWarningDetails() {
        return warningDetails;
    }

    public List getContacts() {
        return contacts;
    }

    public List getCustomer() {
        return customer;
    }

    public String getLastTs() {
        return lastTs;
    }

    public CustomerFromEnum getCustomerFrom() {
        return customerFrom;
    }
}
