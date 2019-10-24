package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class JournalPatch {
    @Expose
    private String content;
    @Expose
    private Date createTs;

    private Integer journalId;
    private String journalPatchId;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public Integer getJournalId() {
        return journalId;
    }

    public void setJournalId(Integer journalId) {
        this.journalId = journalId;
    }

    public String getJournalPatchId() {
        return journalPatchId;
    }

    public void setJournalPatchId(String journalPatchId) {
        this.journalPatchId = journalPatchId;
    }

}
