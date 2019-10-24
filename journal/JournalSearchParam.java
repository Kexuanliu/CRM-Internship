package com.xuebei.crm.journal;

import com.xuebei.crm.utils.CrmDateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Rong Weicheng on 2018/7/14.
 */
public class JournalSearchParam {
    private String userId;
    private JournalTypeEnum journalType;
    private String senderIds;
    private String[] sdId;

    private String customer;
    private String project;

    private String contactsId;
    /**
     * 日志筛选的开始时间.具体到毫秒
     */
    private Date startTime;
    /**
     * 日志筛选的结束时间.具体到毫秒
     */
    private Date endTime;

    /**
     * 日志筛选的开始日期.具体到日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 日志筛选的结束日期.具体到日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    private Integer isRead;

    private Integer isMine;

    private String childId;

    private Boolean isMy;

    private Integer pageNo;
    private Integer pageSize;

    private Integer offSet;

    public Integer getOffSet() {
        if (pageNo != null && pageSize != null) {
            return (pageNo - 1) * pageSize;
        } else {
            return 0;
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public JournalTypeEnum getJournalType() {
        return journalType;
    }

    public void setJournalType(JournalTypeEnum journalType) {
        this.journalType = journalType;
    }

    public String getSenderIds() {
        return senderIds;
    }

    public void setSenderIds(String senderIds) {
        this.senderIds = senderIds;
    }

    public String[] getSdId() {
        return sdId;
    }

    public void setSdId(String[] sdId) {
        this.sdId = sdId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getIsMine() {
        return isMine;
    }

    public void setIsMine(Integer isMine) {
        this.isMine = isMine;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public Boolean getMy() {
        return isMy;
    }

    public void setMy(Boolean my) {
        isMy = my;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        if(pageSize!=null){
            return pageSize;
        }else{
            return 100;
        }
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setStartDate(Date startDate) {
        if (startDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            CrmDateUtils.setAtHalfPassEight(calendar);
            this.startTime = calendar.getTime();
        }
    }

    public void setEndDate(Date endDate) {
        if (endDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDate);
            CrmDateUtils.setAtHalfPassEight(calendar);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            this.endTime = calendar.getTime();
        }
    }
}
