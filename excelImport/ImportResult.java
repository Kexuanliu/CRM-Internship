/**
 * @author ZXL
 * @date 2019/6/25 14:35
 */
package com.xuebei.crm.excelImport;

import com.google.gson.annotations.Expose;

import java.util.List;

public class ImportResult {

    @Expose
    private int failCount;

    @Expose
    private int successCount;

    @Expose
    private List<ErrorMsg> errorMsgsList;

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public List<ErrorMsg> getErrorMsgsList() {
        return errorMsgsList;
    }

    public void setErrorMsgsList(List<ErrorMsg> errorMsgsList) {
        this.errorMsgsList = errorMsgsList;
    }
}