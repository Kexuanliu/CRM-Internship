/**
 * @author TRY
 * @date 2019/3/12 16:53
 */
package com.xuebei.crm.customer.agent;

import com.google.gson.annotations.Expose;
import com.xuebei.crm.customer.CustomerFromEnum;

import java.util.Date;

public class CrmAgentLink {


    @Expose
    private String linkUserId;

    @Expose
    private String agentId;

    @Expose
    private String linkName;

    @Expose
    private String linkPosition;

    @Expose
    private String linkGeneral;

    @Expose
    private String linkMobile;

    @Expose
    private String linkPhone;

    @Expose
    private String linkWeixin;

    @Expose
    private String linkQQ;

    @Expose
    private String linkMail;

    @Expose
    private String linkBg;

    @Expose
    private String updaterId;

    @Expose
    private String createId;

    @Expose
    private String createTs;

    @Expose
    private String createName;

    public String getLinkUserId() {
        return linkUserId;
    }

    public void setLinkUserId(String linkUserId) {
        this.linkUserId = linkUserId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkPosition() {
        return linkPosition;
    }

    public void setLinkPosition(String linkPosition) {
        this.linkPosition = linkPosition;
    }

    public String getLinkGeneral() {
        return linkGeneral;
    }

    public void setLinkGeneral(String linkGeneral) {
        this.linkGeneral = linkGeneral;
    }

    public String getLinkMobile() {
        return linkMobile;
    }

    public void setLinkMobile(String linkMobile) {
        this.linkMobile = linkMobile;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getLinkWeixin() {
        return linkWeixin;
    }

    public void setLinkWeixin(String linkWeixin) {
        this.linkWeixin = linkWeixin;
    }

    public String getLinkQQ() {
        return linkQQ;
    }

    public void setLinkQQ(String linkQQ) {
        this.linkQQ = linkQQ;
    }

    public String getLinkMail() {
        return linkMail;
    }

    public void setLinkMail(String linkMail) {
        this.linkMail = linkMail;
    }

    public String getLinkBg() {
        return linkBg;
    }

    public void setLinkBg(String linkBg) {
        this.linkBg = linkBg;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
}
