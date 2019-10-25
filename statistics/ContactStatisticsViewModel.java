/**
 * @author ZXL
 * @date 2019/7/18 15:44
 */
package com.xuebei.crm.statistics;

import com.google.gson.annotations.Expose;

public class ContactStatisticsViewModel {

    //新增时间
    @Expose
    private String createTime;

    //员工姓名
    @Expose
    private String employeeName;

    //是否A级
    @Expose
    private String isALevel;

    //学校
    @Expose
    private String school;

    //学校ID
    @Expose
    private String schoolId;

    //二级学院
    @Expose
    private String subDept;

    //联系人
    @Expose
    private String linker;

    //联系人ID, 用于跳转
    @Expose
    private String linkerId;

    //职位
    @Expose
    private String position;

    //性别
    @Expose
    private String gender;

    //手机
    @Expose
    private String mobile;

    //座机
    @Expose
    private String phone;

    //微信
    @Expose
    private String weiChat;

    //qq
    @Expose
    private String QQ;

    //邮箱
    @Expose
    private String mail;

    public String getCreateTime() { return createTime; }

    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public String getEmployeeName() { return employeeName; }

    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getIsALevel() { return isALevel; }

    public void setIsALevel(String isALevel) { this.isALevel = isALevel; }

    public String getSchool() { return school; }

    public void setSchool(String school) { this.school = school; }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSubDept() { return subDept; }

    public void setSubDept(String subDept) { this.subDept = subDept; }

    public String getLinker() { return linker; }

    public void setLinker(String linker) { this.linker = linker; }

    public String getLinkerId() { return linkerId; }

    public void setLinkerId(String linkerId) { this.linkerId = linkerId; }

    public String getPosition() { return position; }

    public void setPosition(String position) { this.position = position; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getMobile() { return mobile; }

    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getWeiChat() { return weiChat; }

    public void setWeiChat(String weiChat) { this.weiChat = weiChat; }

    public String getQQ() { return QQ; }

    public void setQQ(String QQ) { this.QQ = QQ; }

    public String getMail() { return mail; }

    public void setMail(String mail) { this.mail = mail; }

}
