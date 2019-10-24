package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;
import com.xuebei.crm.user.User;

import java.util.Date;

/**
 * @author Rong Weicheng
 */
public class JournalComment {
    @Expose
    private Integer commentId;
    @Expose
    private String comment;
    @Expose
    private Integer journalId;
    @Expose
    private User creator;
    @Expose
    private Date createTs;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getJournalId() {
        return journalId;
    }

    public void setJournalId(Integer journalId) {
        this.journalId = journalId;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }
}
