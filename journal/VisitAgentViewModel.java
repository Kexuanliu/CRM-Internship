/**
 * @author TRY
 * @date 2019/3/19 18:02
 */
package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class VisitAgentViewModel {

    @Expose
    private Date createTs;

    @Expose
    private String createName;

    @Expose
    private String visitType;

    @Expose
    private String visitResult;

    @Expose
    private String showInfo;

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getVisitResult() {
        return visitResult;
    }

    public void setVisitResult(String visitResult) {
        this.visitResult = visitResult;
    }

    public String getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(String showInfo) {
        this.showInfo = showInfo;
    }
}
