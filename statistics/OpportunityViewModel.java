package com.xuebei.crm.statistics;

import com.google.gson.annotations.Expose;

public class OpportunityViewModel {

    @Expose
    // 新增时间
    private String createTime;
    @Expose
    private String userId;
    @Expose
    // 员工
    private String employee;
    @Expose
    // 代理商名称
    private String agent;
    @Expose
    // 代理商ID
    private String agentId;
    @Expose
    // 院校
    private String college;
    @Expose
    // 院校ID
    private String collegeId;
    @Expose
    // 二级学院
    private String secondCollege;
    @Expose
    private String secondCollegeId;
    @Expose
    // 是否圈地
    private String whetherEnclosure;
    @Expose
    // 报备项目
    private String project;
    @Expose
    // 报备项目ID
    private String projectId;
    @Expose
    // 计划成交金
    private double money;
    @Expose
    // 计划招标时间
    private String inviteTime;
    @Expose
    // 项目级别
    private String projectLevel;
    @Expose
    // 联系人/代理商销售
    private String contact;
    @Expose
    // 联系人ID
    private String contactId;
    @Expose
    // 项目决策人
    private String decisionMaker;
    @Expose
    // 项目决策人ID
    private String decisionMakerId;
    @Expose
    // 跟进1
    private String follow1;
    @Expose
    // 跟进2
    private String follow2;
    @Expose
    // 跟进3
    private String follow3;

    public String getCreateTime() { return createTime; }

    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getEmployee() { return employee; }

    public void setEmployee(String employee) { this.employee = employee; }

    public String getAgent() { return agent; }

    public void setAgent(String agent) { this.agent = agent; }

    public String getAgentId() { return agentId; }

    public void setAgentId(String agentId) { this.agentId = agentId; }

    public String getCollege() { return college; }

    public void setCollege(String college) { this.college = college; }

    public String getCollegeId() { return collegeId; }

    public void setCollegeId(String collegeId) { this.collegeId = collegeId; }

    public String getSecondCollege() { return secondCollege; }

    public void setSecondCollege(String secondCollege) { this.secondCollege = secondCollege; }

    public String getSecondCollegeId() { return secondCollegeId; }

    public void setSecondCollegeId(String secondCollegeId) { this.secondCollegeId = secondCollegeId; }

    public String getWhetherEnclosure() { return whetherEnclosure; }

    public void setWhetherEnclosure(String whetherEnclosure) { this.whetherEnclosure = whetherEnclosure; }

    public String getProject() { return project; }

    public void setProject(String project) { this.project = project; }

    public String getProjectId() { return projectId; }

    public void setProjectId(String projectId) { this.projectId = projectId; }

    public String getMoney() { return ""+money; }

    public void setMoney(double money) { this.money = money; }

    public String getInviteTime() { return inviteTime; }

    public void setInviteTime(String inviteTime) { this.inviteTime = inviteTime; }

    public String getProjectLevel() { return projectLevel; }

    public void setProjectLevel(String projectLevel) { this.projectLevel = projectLevel; }

    public String getContact() { return contact; }

    public void setContact(String contact)  { this.contact = contact; }

    public String getContactId() { return contactId; }

    public void setContactId(String contactId) { this.contactId = contactId;}

    public String getDecisionMaker() { return decisionMaker; }

    public void setDecisionMaker(String decisionMaker) { this.decisionMaker = decisionMaker; }

    public String getDecisionMakerId() { return decisionMakerId; }

    public void setDecisionMakerId(String decisionMakerId) { this.decisionMakerId = decisionMakerId; }

    public String getFollow1() { return follow1; }

    public void setFollow1(String follow1) { this.follow1 = follow1; }

    public String getFollow2() { return follow2; }

    public void setFollow2(String follow2) { this.follow2 = follow2; }

    public String getFollow3() { return follow3; }

    public void setFollow3(String follow3) { this.follow3 = follow3; }

    // 商机ID
    private String oppoId;

    public String getOppoId() { return oppoId; }

    public void setOppoId(String oppoId) { this.oppoId = oppoId; }
}
