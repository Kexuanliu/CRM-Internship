package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;
import com.xuebei.crm.user.User;

import java.util.Date;

/**
 * @author  Xinyu Liu
 * 拜访评论实体类
 */
public class VisitLogComment {

    @Expose
    private Integer commentId;
    @Expose
    private String content;
    @Expose
    private Integer visitLogId;
    @Expose
    private Date createTs;
    @Expose
    private User creator;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getVisitLogId() {
        return visitLogId;
    }

    public void setVisitLogId(Integer visitLogId) {
        this.visitLogId = visitLogId;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
