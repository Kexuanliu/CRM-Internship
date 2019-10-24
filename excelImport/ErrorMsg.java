/**
 * @author ZXL
 * @date 2019/6/25 14:35
 */
package com.xuebei.crm.excelImport;

import com.google.gson.annotations.Expose;

public class ErrorMsg {
    @Expose
    private Integer rows;
    @Expose
    private String msg;
    @Expose
    private String errorCode;
    @Expose
    private String showInfo;

    public String getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(String showInfo) {
        this.showInfo = showInfo;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
