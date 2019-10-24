/**
 * @author TRY
 * @date 2019/3/20 13:47
 */
package com.xuebei.crm.customer.agent;

import com.google.gson.annotations.Expose;

public class AgentLinkSimpleModel {

    @Expose
    private String companyName;

    @Expose
    private String linkName;

    @Expose
    private String position;

    @Expose
    private String agentId;

    @Expose
    private String agentLinkId;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentLinkId() {
        return agentLinkId;
    }

    public void setAgentLinkId(String agentLinkId) {
        this.agentLinkId = agentLinkId;
    }
}
