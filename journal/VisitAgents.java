/**
 * @author TRY
 * @date 2019/3/20 16:27
 */
package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class VisitAgents {

    @Expose
    private Integer visitAgentsId;
    @Expose
    private Integer visitLogId;
    @Expose
    private String agentLinkId;
    @Expose
    private String agentId;
    @Expose
    private Date createTs;
    @Expose
    private String createId;
    @Expose
    private String createName;
    @Expose
    private String showInfo;

    public Integer getVisitAgentsId() {
        return visitAgentsId;
    }

    public void setVisitAgentsId(Integer visitAgentsId) {
        this.visitAgentsId = visitAgentsId;
    }

    public Integer getVisitLogId() {
        return visitLogId;
    }

    public void setVisitLogId(Integer visitLogId) {
        this.visitLogId = visitLogId;
    }

    public String getAgentLinkId() {
        return agentLinkId;
    }

    public void setAgentLinkId(String agentLinkId) {
        this.agentLinkId = agentLinkId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(String showInfo) {
        this.showInfo = showInfo;
    }
}
