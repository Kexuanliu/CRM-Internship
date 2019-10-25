/**
 * @author ZXL
 * @date 2019/7/17 10:56
 */
package com.xuebei.crm.statistics;

import com.xuebei.crm.utils.CrmDateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.support.incrementer.SybaseAnywhereMaxValueIncrementer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatisticsSearchParam {

    /**
     * 用户列表
     */
    private List<String> userIds;

    /**
     * 时间段枚举
     */
    private TimeSpanEnum timeSpanEnum;

    /**
     * 起始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 是否显示其他条件
     **/
    private boolean showOtherCondition;

    public boolean isShowOtherCondition() {
        return showOtherCondition;
    }

    public void setShowOtherCondition(boolean showOtherCondition) {
        this.showOtherCondition = showOtherCondition;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public TimeSpanEnum getTimeSpanEnum() {
        return timeSpanEnum;
    }

    public void setTimeSpanEnum(TimeSpanEnum timeSpanEnum) {
        this.timeSpanEnum = timeSpanEnum;
    }

    public Date getBeginTime() {
        if (StringUtils.isNotEmpty(beginTime)) {
            return CrmDateUtils.parse(CrmDateUtils.LOG_HMSS_FORMAT, beginTime);
        } else {
            return getBeginDateByTimeEnnum();
        }
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        if (StringUtils.isNotEmpty(endTime)) {
            return CrmDateUtils.parse(CrmDateUtils.LOG_HMSS_FORMAT, endTime);
        } else {
            return getEndDateByTimeEnnum();
        }
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    private Date getBeginDateByTimeEnnum() {
        Calendar calendar = Calendar.getInstance();
        switch (this.timeSpanEnum) {
            case today:
                calendar.setTime(new Date());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                break;
            case yesterday:
                calendar.setTime(new Date());
                calendar.add(calendar.DATE, -1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                break;
            case thisMonth:
                calendar.setTime(new Date());
                int startDate = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY),startDate,00,00,00);
                break;
            case lastMonth:
                calendar.setTime(new Date());
                calendar.add(Calendar.MONTH,-1);
                int lastMonthStartDate = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY),lastMonthStartDate,00,00,00);
                break;
            case thisQuarter:
                calendar.setTime(new Date());
                setCalendarForQuarter(calendar, true);
                calendar.set(Calendar.DATE, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                break;
            case lastQuarter:
                calendar.setTime(new Date());
                setCalendarForQuarter(calendar, false);
                calendar.set(Calendar.DATE, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                break;
            case thisYear:
                calendar.setTime(new Date());
                setThisYearStart(calendar);
                break;
            case lastYear:
                calendar.setTime(new Date());
                setThisYearStart(calendar);
                calendar.add(Calendar.YEAR, -1);
                break;
            case toNow:
                calendar.set(1990, 01, 01, 0, 0, 0);
                break;
        }
        return calendar.getTime();
    }


    private Date getEndDateByTimeEnnum() {
        Calendar calendar = Calendar.getInstance();
        switch (this.timeSpanEnum) {
            case today:
                calendar.setTime(new Date());
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                break;
            case yesterday:
                calendar.setTime(new Date());
                calendar.add(calendar.DATE, -1);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                break;
            case thisMonth:
                calendar.setTime(new Date());
                int lastDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY),lastDate,23,59,59);
                break;
            case lastMonth:
                calendar.setTime(new Date());
                calendar.add(Calendar.MONTH,-1);
                int lastMonthLastDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY),lastMonthLastDate,23,59,59);
                break;
            case thisQuarter:
                calendar.setTime(new Date());
                setCalendarForQuarter(calendar, true);
                calendar.add(Calendar.MONTH, 2);
                int quarterLastDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY),quarterLastDate,23,59,59);
                break;
            case lastQuarter:
                calendar.setTime(new Date());
                setCalendarForQuarter(calendar, false);
                calendar.add(Calendar.MONTH, 2);
                int lastQuarterLastDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY),lastQuarterLastDate,23,59,59);
                break;
            case thisYear:
                calendar.setTime(new Date());
                setThisYearEnd(calendar);
                break;
            case lastYear:
                calendar.setTime(new Date());
                setThisYearEnd(calendar);
                calendar.add(Calendar.YEAR, -1);
                break;
            case toNow:
                calendar.setTime(new Date());
                break;
        }
        return calendar.getTime();
    }

    private void setThisYearStart(Calendar calendar) {
        calendar.set(Calendar.MONTH,calendar.getActualMinimum(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

    private void setThisYearEnd(Calendar calendar) {
        calendar.set(Calendar.MONTH,calendar.getActualMaximum(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
    }

    private void setCalendarForQuarter(Calendar calendar, boolean whetherThis) {
        int curMonth = calendar.get(Calendar.MONTH) + 1;
        if (curMonth <= 3) {
            if (whetherThis) {
                calendar.set(Calendar.MONTH, 0);
            } else {
                calendar.set(Calendar.MONTH, 9);
            }
        } else if (curMonth >= 4 && curMonth <= 6) {
            if (whetherThis) {
                calendar.set(Calendar.MONTH, 3);
            } else {
                calendar.set(Calendar.MONTH, 0);
            }
        } else if (curMonth >= 7 && curMonth <= 9) {
            if (whetherThis) {
                calendar.set(Calendar.MONTH, 6);
            } else {
                calendar.set(Calendar.MONTH, 3);
            }
        } else {
            if (whetherThis) {
                calendar.set(Calendar.MONTH, 9);
            } else {
                calendar.set(Calendar.MONTH, 6);
            }
        }
    }
}
