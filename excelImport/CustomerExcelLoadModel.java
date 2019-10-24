/**
 * @author ZXL
 * @date 2019/7/1 14:49
 */
package com.xuebei.crm.excelImport;

import java.util.Date;

public class CustomerExcelLoadModel {

    private Date createTime;

    private String employeeName;

    private String region;

    private String province;

    private String city;

    private String schoolType;

    private String schoolName;

    private String schoolFrom;

    private String subSchoolName;

    private String getOwner;

    private String subSchoolLinker;

    private String sex;

    private String position;

    private String mobile;

    public String getGetOwner() {
        return getOwner;
    }

    public void setGetOwner(String getOwner) {
        this.getOwner = getOwner;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public Date getCreateTime() {
        return createTime;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolFrom() {
        return schoolFrom;
    }

    public void setSchoolFrom(String schoolFrom) {
        this.schoolFrom = schoolFrom;
    }

    public String getSubSchoolName() {
        return subSchoolName;
    }

    public void setSubSchoolName(String subSchoolName) {
        this.subSchoolName = subSchoolName;
    }

    public String getSubSchoolLinker() {
        return subSchoolLinker;
    }

    public void setSubSchoolLinker(String subSchoolLinker) {
        this.subSchoolLinker = subSchoolLinker;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
