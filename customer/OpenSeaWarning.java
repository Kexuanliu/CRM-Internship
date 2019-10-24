package com.xuebei.crm.customer;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2018/7/19.
 */
public class OpenSeaWarning {
    @Expose
    private String leftTime;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Expose
    private String  deptName;
    @Expose
    private String deptId;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Expose
    private String createdTime;
    @Expose
    private Integer followTimes;
    @Expose
    private String lastTimeFollow;
    @Expose
    private String[] warnedReasons;

    @Expose
    private String leftDays;
    @Expose
    private String leftHours;

    public Boolean getDelayApplied() {
        return isDelayApplied;
    }

    public void setDelayApplied(Boolean delayApplied) {
        isDelayApplied = delayApplied;
    }

    @Expose
    private Boolean isDelayApplied;

    public String getLeftDays() {
        return leftDays;
    }

    public void setLeftDays(String leftDays) {
        this.leftDays = leftDays;
    }

    public String getLeftHours() {
        return leftHours;
    }

    public void setLeftHours(String leftHours) {
        this.leftHours = leftHours;
    }

    public String getLastTimeFollow() {
        return lastTimeFollow;
    }

    public void setLastTimeFollow(String lastTimeFollow) {
        this.lastTimeFollow = lastTimeFollow;
    }

    public String getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(String leftTime) {
        this.leftTime = leftTime;
    }

    public Integer getFollowTimes() {
        return followTimes;
    }

    public void setFollowTimes(Integer followTimes) {
        this.followTimes = followTimes;
    }

    public String[] getWarnedReasons() {
        return warnedReasons;
    }

    public void setWarnedReasons(String[] warnedReasons) {
        this.warnedReasons = warnedReasons;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
