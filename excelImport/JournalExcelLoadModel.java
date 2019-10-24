/**
 * @author ZXL
 * @date 2019/7/5 14:45
 */
package com.xuebei.crm.excelImport;

import java.util.Date;

public class JournalExcelLoadModel {
    private Date createTime;

    private String employee;

    private String outType;

    private String visitType;

    private String contactType;

    private String agentName;

    private String agentLinker;

    private String visitReason;

    private String visitResult;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getOutType() {
        return outType;
    }

    public void setOutType(String outType) {
        this.outType = outType;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentLinker() {
        return agentLinker;
    }

    public void setAgentLinker(String agentLinker) {
        this.agentLinker = agentLinker;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public String getVisitResult() {
        return visitResult;
    }

    public void setVisitResult(String visitResult) {
        this.visitResult = visitResult;
    }
}
