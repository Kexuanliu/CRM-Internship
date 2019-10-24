/**
 * @author ZXL
 * @date 2019/6/27 16:00
 */
package com.xuebei.crm.excelImport;

import java.util.Date;

public class OpporttunityExcelLoadModel {
    //创建日期
    private Date createTime;
    //员工姓名
    private String employeeName;
    //代理商名称
    private String agentName;
    //联系人
    private String linkName;
    //学校名称
    private String schoolName;
    //二级学院
    private String schoolSubpart;
    //决策人
    private String decisionMaker;
    //负责人
    private String charger;
    //商机名称
    private String oppName;
    //详细产品
    private String oppDetail;
    //是否包含学呗
    private String containXuebei;
    //金额
    private String money;
    //资金来源
    private String moneyForm;
    //预估挂标日期
    private Date preDate;
    //销售阶段
    private String saleStage;
    //跟进记录
    private String logDetail;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolSubpart() {
        return schoolSubpart;
    }

    public void setSchoolSubpart(String schoolSubpart) {
        this.schoolSubpart = schoolSubpart;
    }

    public String getDecisionMaker() {
        return decisionMaker;
    }

    public void setDecisionMaker(String decisionMaker) {
        this.decisionMaker = decisionMaker;
    }

    public String getCharger() {
        return charger;
    }

    public void setCharger(String charger) {
        this.charger = charger;
    }

    public String getOppName() {
        return oppName;
    }

    public void setOppName(String oppName) {
        this.oppName = oppName;
    }

    public String getOppDetail() {
        return oppDetail;
    }

    public void setOppDetail(String oppDetail) {
        this.oppDetail = oppDetail;
    }

    public String getContainXuebei() {
        return containXuebei;
    }

    public void setContainXuebei(String containXuebei) {
        this.containXuebei = containXuebei;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoneyForm() {
        return moneyForm;
    }

    public void setMoneyForm(String moneyForm) {
        this.moneyForm = moneyForm;
    }

    public Date getPreDate() {
        return preDate;
    }

    public void setPreDate(Date preDate) {
        this.preDate = preDate;
    }

    public String getSaleStage() {
        return saleStage;
    }

    public void setSaleStage(String saleStage) {
        this.saleStage = saleStage;
    }

    public String getLogDetail() {
        return logDetail;
    }

    public void setLogDetail(String logDetail) {
        this.logDetail = logDetail;
    }
}
