package com.xuebei.crm.opportunity;

import com.google.gson.annotations.Expose;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/23.
 */
public class Opportunity {
    private static Map<String, String> salesStatusMap;
    @Expose
    private Integer opportunityId;
    @Expose
    private String customerId;
    @Expose
    private String customerName;
    @Expose
    private String opportunityName;
    @Expose
    private String salesStatus;
    @Expose
    private double amount;
    @Expose
    private String failReason;
    @Expose
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkDate;
    @Expose
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date clinchDate;
    @Expose
    private double totalCoast;
    @Expose
    private String content;
    @Expose
    private String contactId;
    @Expose
    private String userId;
    @Expose
    private List<String> subUserId;
    @Expose
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date create_ts;
    @Expose
    private String updater_id;
    @Expose
    private Date update_ts;
    @Expose
    private String checkDateString;
    @Expose
    private String clinchDateString;
    @Expose
    private String agent;
    @Expose
    private String totalName;
    @Expose
    private String lastStatus;
    @Expose
    private String agentId;
    @Expose
    private String decisionMakerId;
    @Expose
    private String agentLinkId;
    @Expose
    private String chargeId;

    @Expose
    private String showInfo;
    @Expose
    private String moneyFrom;

    @Expose
    private Integer containXuebei;

    private Integer isExcelImport;

    public Integer getIsExcelImport() {
        return isExcelImport;
    }

    public void setIsExcelImport(Integer isExcelImport) {
        this.isExcelImport = isExcelImport;
    }

    public String getMoneyFrom() {
        return moneyFrom;
    }

    public void setMoneyFrom(String moneyFrom) {
        this.moneyFrom = moneyFrom;
    }

    public Integer getContainXuebei() {
        return containXuebei;
    }

    public void setContainXuebei(Integer containXuebei) {
        this.containXuebei = containXuebei;
    }

    public String getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(String showInfo) {
        this.showInfo = showInfo;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getDecisionMakerId() {
        return decisionMakerId;
    }

    public void setDecisionMakerId(String decisionMakerId) {
        this.decisionMakerId = decisionMakerId;
    }

    public String getAgentLinkId() {
        return agentLinkId;
    }

    public void setAgentLinkId(String agentLinkId) {
        this.agentLinkId = agentLinkId;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    static {
        Map<String, String> tmpMap = new HashMap<>(12);
        tmpMap.put("A", "获取客户信息");
        tmpMap.put("B1", "方案撰写");
        tmpMap.put("B2", "方案申报及落实资金");
        tmpMap.put("B3", "项目论证");
        tmpMap.put("B4", "写招标参数");
        tmpMap.put("C", "挂网招标");
        tmpMap.put("D", "中标签合同");
        tmpMap.put("F", "输单");
        tmpMap.put("0", "未开始");
        tmpMap.put("1", "进行中");
        tmpMap.put("2", "交付及回款");
        salesStatusMap = Collections.unmodifiableMap(tmpMap);
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    public String getTotalName() {
        return totalName;
    }

    public void setTotalName(String totalName) {
        if (salesStatusMap.containsKey(salesStatus)) {
            salesStatus = salesStatusMap.get(salesStatus);
        }
        if (customerId != null) {
            this.totalName = this.opportunityName + "   用户:" + customerName + "-" + customerId + "-" + failReason + "-" + userId + "   金额:" + amount + "   阶段:" + salesStatus;
        } else {
            this.totalName = this.opportunityName + "   用户:" + customerName + "-" + failReason + "-" + userId + "   金额:" + amount + "   阶段:" + salesStatus;
        }
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getUpdater_id() {
        return updater_id;
    }

    public void setUpdater_id(String updater_id) {
        this.updater_id = updater_id;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<String> getSubUserId() {
        return subUserId;
    }

    public void setSubUserId(List<String> subUserId) {
        this.subUserId = subUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(Integer opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public String getSalesStatus() {
        return salesStatus;
    }

    public void setSalesStatus(String salesStatus) {
        this.salesStatus = salesStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getClinchDate() {
        return clinchDate;
    }

    public void setClinchDate(Date clinchDate) {
        this.clinchDate = clinchDate;
    }

    public double getTotalCoast() {
        return totalCoast;
    }

    public void setTotalCoast(double totalCoast) {
        this.totalCoast = totalCoast;
    }

    public Date getCreate_ts() {
        return create_ts;
    }

    public void setCreate_ts(Date create_ts) {
        this.create_ts = create_ts;
    }

    public Date getUpdate_ts() {
        return update_ts;
    }

    public void setUpdate_ts(Date update_ts) {
        this.update_ts = update_ts;
    }

    public String getCheckDateString() {
        SimpleDateFormat builder = new SimpleDateFormat("yyyy-MM-dd");
        return builder.format(this.checkDate);
    }

    public void setCheckDateString(String checkDateString) {
        this.checkDateString = checkDateString;
    }

    public String getClinchDateString() {
        SimpleDateFormat builder = new SimpleDateFormat("yyyy-MM-dd");
        return builder.format(this.clinchDate);
    }

    public void setClinchDateString(String clinchDateString) {
        this.clinchDateString = clinchDateString;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}
