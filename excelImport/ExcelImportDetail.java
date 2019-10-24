/**
 * @author ZXL
 * @date 2019/6/21 17:52
 */
package com.xuebei.crm.excelImport;

import com.google.gson.annotations.Expose;

public class ExcelImportDetail {
    @Expose
    private Integer logId;
    @Expose
    private Long batchLogId;
    @Expose
    private Integer result;
    @Expose
    private  String showInfo;
    @Expose
    private String createId;
    @Expose
    private String executeCode;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Long getBatchLogId() {
        return batchLogId;
    }

    public void setBatchLogId(Long batchLogId) {
        this.batchLogId = batchLogId;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(String showInfo) {
        this.showInfo = showInfo;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getExecuteCode() {
        return executeCode;
    }

    public void setExecuteCode(String executeCode) {
        this.executeCode = executeCode;
    }
}
