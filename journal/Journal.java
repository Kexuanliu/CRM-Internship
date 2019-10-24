package com.xuebei.crm.journal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
import com.xuebei.crm.user.User;
import com.xuebei.crm.utils.CustomJsonDateDeserializer;
import com.xuebei.crm.utils.DateJsonSerializer;


import java.util.Date;
import java.util.List;

public class Journal {

    @Expose
    private Integer journalId;
    @Expose
    private String userId;
    @Expose
    private String userName;
    @Expose
    private JournalTypeEnum type;
    @Expose
    private String other;
    @Expose
    private String plan;
    @Expose
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date createTs;
    @Expose
    private Date modifyTs;
    @Expose
    private List<JournalComment> comments;

    @Expose
    private Date repairTs;
    @Expose
    private Boolean hasSubmitted;
    @Expose
    private List<VisitRecord> visitRecords;

    @Expose
    private User user;

    @Expose
    private Integer readNum;

    @Expose
    private List<JournalPatch> journalPatches;

    @Expose
    private Boolean isMine;

    @Expose
    private Boolean isToday = false;
    @Expose
    private String summary;

    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setJournalId(Integer journalId) {
        this.journalId = journalId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setType(JournalTypeEnum type) {
        this.type = type;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public void setModifyTs(Date modifyTs) {
        this.modifyTs = modifyTs;
    }

    public void setComments(List<JournalComment> comments) {
        this.comments = comments;
    }

    public void setRepairTs(Date repairTs) {
        this.repairTs = repairTs;
    }

    public void setHasSubmitted(Boolean hasSubmitted) {
        this.hasSubmitted = hasSubmitted;
    }

    public void setVisitRecords(List<VisitRecord> visitRecords) {
        this.visitRecords = visitRecords;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public void setJournalPatches(List<JournalPatch> journalPatches) {
        this.journalPatches = journalPatches;
    }

    public void setMine(Boolean mine) {
        isMine = mine;
    }

    public void setToday(Boolean today) {
        isToday = today;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getJournalId() {
        return journalId;
    }

    public String getUserId() {
        return userId;
    }

    public JournalTypeEnum getType() {
        return type;
    }

    public String getOther() {
        return other;
    }

    public String getPlan() {
        return plan;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public Date getModifyTs() {
        return modifyTs;
    }

    public List<JournalComment> getComments() {
        return comments;
    }

    public Date getRepairTs() {
        return repairTs;
    }

    public Boolean getHasSubmitted() {
        return hasSubmitted;
    }

    public List<VisitRecord> getVisitRecords() {
        return visitRecords;
    }

    public User getUser() {
        return user;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public List<JournalPatch> getJournalPatches() {
        return journalPatches;
    }

    public Boolean getMine() {
        return isMine;
    }

    public Boolean getToday() {
        return isToday;
    }

    public String getSummary() {
        return summary;
    }
}
