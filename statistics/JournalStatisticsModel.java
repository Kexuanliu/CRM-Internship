/**
 * @author ZXL
 * @date 2019/7/25 16:03
 */
package com.xuebei.crm.statistics;

import com.google.gson.annotations.Expose;

public class JournalStatisticsModel {
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
}
