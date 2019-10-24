/**
 * @author ZXL
 * @date 2019/6/21 17:46
 */
package com.xuebei.crm.excelImport;

import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.List;

public class ExcelImportBatchLog {

    @Expose
    private Long batchLogId;
    @Expose
    private String createId;
    @Expose
    private Date createTime;
    @Expose
    private Integer importType;
    @Expose
    List<ExcelImportDetail> excelImportDetailList;

    public List<ExcelImportDetail> getExcelImportDetailList() {
        return excelImportDetailList;
    }

    public void setExcelImportDetailList(List<ExcelImportDetail> excelImportDetailList) {
        this.excelImportDetailList = excelImportDetailList;
    }

    public Long getBatchLogId() {
        return batchLogId;
    }

    public void setBatchLogId(Long batchLogId) {
        this.batchLogId = batchLogId;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getImportType() {
        return importType;
    }

    public void setImportType(Integer importType) {
        this.importType = importType;
    }
}
