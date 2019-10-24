/**
 * @author TRY
 * @date 2019/3/15 14:27
 */
package com.xuebei.crm.customer.agent;

import com.google.gson.annotations.Expose;

import java.util.List;

public class AgentLinkViewModel {

    @Expose
    private CrmAgent crmAgent;

    @Expose
    private List<CrmAgentLink> crmAgentLinkList;

    public CrmAgent getCrmAgent() {
        return crmAgent;
    }

    public void setCrmAgent(CrmAgent crmAgent) {
        this.crmAgent = crmAgent;
    }

    public List<CrmAgentLink> getCrmAgentLinkList() {
        return crmAgentLinkList;
    }

    public void setCrmAgentLinkList(List<CrmAgentLink> crmAgentLinkList) {
        this.crmAgentLinkList = crmAgentLinkList;
    }
}
