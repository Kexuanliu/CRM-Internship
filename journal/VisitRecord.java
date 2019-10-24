package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;
import com.xuebei.crm.customer.Contacts;
import com.xuebei.crm.customer.agent.CrmAgentLink;
import com.xuebei.crm.opportunity.Opportunity;

import java.util.Date;
import java.util.List;

public class VisitRecord {

    @Expose
    private Integer visitId;
    @Expose
    private List<Contacts> chosenContacts;
    @Expose
    private List<CrmAgentLink> chosenAgents;
    @Expose
    private String visitType;

    @Expose
    private Integer contactType;

    @Expose
    private String visitResult;
    @Expose
    private Integer journalId;
    @Expose
    private Integer opportunityId;
    @Expose
    private String opportunityName;
    @Expose
    private Opportunity opportunity;
    @Expose
    private List<VisitLogComment> comments;
	@Expose
    private String realName;
    @Expose
    private Date updateTime;
    @Expose
    private Integer outType;
    @Expose
    private String outReason;

    private Integer isExcelImport;

    public Integer getOutType() {
        return outType;
    }

    public void setOutType(Integer outType) {
        this.outType = outType;
    }

    public String getOutReason() {
        return outReason;
    }

    public void setOutReason(String outReason) {
        this.outReason = outReason;
    }

    public Integer getIsExcelImport() {
        return isExcelImport;
    }

    public void setIsExcelImport(Integer isExcelImport) {
        this.isExcelImport = isExcelImport;
    }

    public Integer getContactType() {
        return contactType;
    }

    public void setContactType(Integer contactType) {
        this.contactType = contactType;
    }

    public List<CrmAgentLink> getChosenAgents() {
        return chosenAgents;
    }

    public void setChosenAgents(List<CrmAgentLink> chosenAgents) {
        this.chosenAgents = chosenAgents;
    }

    public Integer getVisitId() {
        return visitId;
    }

    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getVisitResult() {
        return visitResult;
    }

    public void setVisitResult(String visitResult) {
        this.visitResult = visitResult;
    }

    public Integer getJournalId() {
        return journalId;
    }

    public void setJournalId(Integer journalId) {
        this.journalId = journalId;
    }

    public List<Contacts> getChosenContacts() {
        return chosenContacts;
    }

    public void setChosenContacts(List<Contacts> chosenContacts) {
        this.chosenContacts = chosenContacts;
    }

    public Integer getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(Integer opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public List<VisitLogComment> getComments() {
        return comments;
    }

    public void setComments(List<VisitLogComment> comments) {
        this.comments = comments;
    }

    public Opportunity getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(Opportunity opportunity) {
        this.opportunity = opportunity;
    }
}
