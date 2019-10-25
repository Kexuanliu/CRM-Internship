/**
 * @author ZXL
 * @date 2019/7/19 16:20
 */
package com.xuebei.crm.statistics;

import com.google.gson.annotations.Expose;

public class StatisticsTotalViewModel {

    //客户
    /**
    *   A级人员
    */
    @Expose
    private Integer aLevelCustomerCount;

    /**
    *   A级人员覆盖率
    */
    @Expose
    private String aLevelCustomerRate;

    //代理商

    /**
     *  新增代理商
     */
    @Expose
    private Integer newAgentCount;

    /**
     *  核心代理商
     */
    @Expose
    private Integer coreAgentCount;

    /**
     *  院校拜访次数
     */
    @Expose
    private Integer visitCustomerTimesCount;

    /**
     *  院校拜访个数统计
     */
    @Expose
    private Integer visitCustomerNumCount;

    /**
     *  陪同拜访次数
     */
    @Expose
    private Integer accompanyVisitTimesCount;

    /**
     *  陪同拜访个数
     */
    @Expose
    private Integer accompanyVisitNumCount;

    /**
     *  深度拜访次数
     */
    @Expose
    private Integer deepVisitTimesCount;

    /**
     *  浅度拜访次数
     */
    @Expose
    private Integer normalVisitTimesCount;

    /**
     *  一级学院拜访个数
     */
    @Expose
    private Integer customerVisitNumCount ;

    /**
     *  二级学院拜访个数
     */
    @Expose
    private Integer deptVisitNumCount;

    /**
     *  拜访院长次数
     */
    @Expose
    private Integer visitPresidentTimesCount;

    /**
     *  拜访主任次数
     */
    @Expose
    private Integer visitDeanTimesCount;

    /**
     * 新增商机
     */
    @Expose
    private Integer newOppCount;

    /**
     *  新增A级商机
     */
    @Expose
    private Integer alevelOppCount;

    /**
     *  新增B级商机
     */
    @Expose
    private Integer blevelOppCount;

    /**
     *  新增C级商机
     */
    @Expose
    private Integer clevelOppCount;

    /**
     *  新增D级商机
     */
    @Expose
    private Integer dlevelOppCount;

    /**
     *  D级计划成交金额
     */
    @Expose
    private Integer dlevelOppMoneySum;


    public Integer getaLevelCustomerCount() {
        return aLevelCustomerCount;
    }

    public void setaLevelCustomerCount(Integer aLevelCustomerCount) {
        this.aLevelCustomerCount = aLevelCustomerCount;
    }

    public String getaLevelCustomerRate() {
        return aLevelCustomerRate;
    }

    public void setaLevelCustomerRate(String aLevelCustomerRate) {
        this.aLevelCustomerRate = aLevelCustomerRate;
    }

    public Integer getNewAgentCount() {
        return newAgentCount;
    }

    public void setNewAgentCount(Integer newAgentCount) {
        this.newAgentCount = newAgentCount;
    }

    public Integer getCoreAgentCount() {
        return coreAgentCount;
    }

    public void setCoreAgentCount(Integer coreAgentCount) {
        this.coreAgentCount = coreAgentCount;
    }

    public Integer getVisitCustomerTimesCount() {
        return visitCustomerTimesCount;
    }

    public void setVisitCustomerTimesCount(Integer visitCustomerTimesCount) {
        this.visitCustomerTimesCount = visitCustomerTimesCount;
    }

    public Integer getVisitCustomerNumCount() {
        return visitCustomerNumCount;
    }

    public void setVisitCustomerNumCount(Integer visitCustomerNumCount) {
        this.visitCustomerNumCount = visitCustomerNumCount;
    }

    public Integer getAccompanyVisitTimesCount() {
        return accompanyVisitTimesCount;
    }

    public void setAccompanyVisitTimesCount(Integer accompanyVisitTimesCount) {
        this.accompanyVisitTimesCount = accompanyVisitTimesCount;
    }

    public Integer getAccompanyVisitNumCount() {
        return accompanyVisitNumCount;
    }

    public void setAccompanyVisitNumCount(Integer accompanyVisitNumCount) {
        this.accompanyVisitNumCount = accompanyVisitNumCount;
    }

    public Integer getDeepVisitTimesCount() {
        return deepVisitTimesCount;
    }

    public void setDeepVisitTimesCount(Integer deepVisitTimesCount) {
        this.deepVisitTimesCount = deepVisitTimesCount;
    }

    public Integer getNormalVisitTimesCount() {
        return normalVisitTimesCount;
    }

    public void setNormalVisitTimesCount(Integer normalVisitTimesCount) {
        this.normalVisitTimesCount = normalVisitTimesCount;
    }

    public Integer getCustomerVisitNumCount() {
        return customerVisitNumCount;
    }

    public void setCustomerVisitNumCount(Integer customerVisitNumCount) {
        this.customerVisitNumCount = customerVisitNumCount;
    }

    public Integer getDeptVisitNumCount() {
        return deptVisitNumCount;
    }

    public void setDeptVisitNumCount(Integer deptVisitNumCount) {
        this.deptVisitNumCount = deptVisitNumCount;
    }

    public Integer getVisitPresidentTimesCount() {
        return visitPresidentTimesCount;
    }

    public void setVisitPresidentTimesCount(Integer visitPresidentTimesCount) {
        this.visitPresidentTimesCount = visitPresidentTimesCount;
    }

    public Integer getVisitDeanTimesCount() {
        return visitDeanTimesCount;
    }

    public void setVisitDeanTimesCount(Integer visitDeanTimesCount) {
        this.visitDeanTimesCount = visitDeanTimesCount;
    }

    public Integer getNewOppCount() {
        return newOppCount;
    }

    public void setNewOppCount(Integer newOppCount) {
        this.newOppCount = newOppCount;
    }

    public Integer getAlevelOppCount() {
        return alevelOppCount;
    }

    public void setAlevelOppCount(Integer alevelOppCount) {
        this.alevelOppCount = alevelOppCount;
    }

    public Integer getBlevelOppCount() {
        return blevelOppCount;
    }

    public void setBlevelOppCount(Integer blevelOppCount) {
        this.blevelOppCount = blevelOppCount;
    }

    public Integer getClevelOppCount() {
        return clevelOppCount;
    }

    public void setClevelOppCount(Integer clevelOppCount) {
        this.clevelOppCount = clevelOppCount;
    }

    public Integer getDlevelOppCount() {
        return dlevelOppCount;
    }

    public void setDlevelOppCount(Integer dlevelOppCount) {
        this.dlevelOppCount = dlevelOppCount;
    }

    public Integer getDlevelOppMoneySum() {
        return dlevelOppMoneySum;
    }

    public void setDlevelOppMoneySum(Integer dlevelOppMoneySum) {
        this.dlevelOppMoneySum = dlevelOppMoneySum;
    }
}
