/**
 * @author ZXL
 * @date 2019/7/18 17:00
 */
package com.xuebei.crm.statistics;

import com.google.gson.annotations.Expose;
import java.util.*;

public class AgentStatisticsViewModel {

    // 新增时间
    @Expose
    private String createTime;

    // 员工
    @Expose
    private String employee;

    // 客户来源
    @Expose
    private String customerFrom;

    // 代理商名称
    @Expose
    private String agentName;

    // 客户等级
    @Expose
    private String customerLevel;

    // 主要销售成员
    @Expose
    private String mainSaleMember;

    // 背景
    @Expose
    private String background;

    public String getCreateTime() {
        return createTime.substring(10);
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) { this.employee = employee; }

    public String getCustomerFrom() { return customerFrom; }

    public void setCustomerFrom(String customerFrom) { this.customerFrom = customerFrom; }

    public String getAgentName() { return agentName; }

    public void setAgentName(String agentName) { this.agentName = agentName; }

    public String getCustomerLevel() { return customerLevel; }

    public void setCustomerLevel(String customerLevel) { this.customerLevel = customerLevel; }

    public String getMainSaleMember() { return mainSaleMember; }

    public void setMainSaleMember(String mainSaleMember) { this.mainSaleMember = mainSaleMember; }

    public String getBackground() { return background; }

    public void setBackground(String background) { this.background = background; }

    // 本月联系
    @Expose
    private String thisLinkTimes;

    // 上月联系
    @Expose
    private String lastLinkTimes;

    // 上上月联系
    @Expose
    private String lastLastLinkTimes;

    public String getThisLinkTimes() { return thisLinkTimes; }

    public void setThisLinkTimes(String thisLinkTimes) { this.thisLinkTimes = thisLinkTimes; }

    public String getLastLinkTimes() { return lastLinkTimes; }

    public void setLastLinkTimes(String lastLinkTimes) { this.lastLinkTimes = lastLinkTimes; }

    public String getLastLastLinkTimes() { return lastLastLinkTimes; }

    public void setLastLastLinkTimes(String lastLastLinkTimes) { this.lastLastLinkTimes = lastLastLinkTimes; }

    // 代理商ID
    @Expose
    private String agentId;

    public String getAgentId() { return agentId; }

    public void setAgentId(String agentId) { this.agentId = agentId; }

}
