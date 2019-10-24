/**
 * @author ZXL
 * @date 2019/6/6 16:43
 */
package com.xuebei.crm.dataRepair;

public interface DataRepairService {

    /**
     * 此方法用于修复companyUser的relsFullPath的数据
     */
    void repairCompanyUserLeadPath();

    /**
    *  此方法用于修复之前职位错乱的问题
    */
    void repairPosition();

    /**
    *   此方法用于修复联系人创建时间问题
    */
    void repairContacts();

    /**
     * 修复之前部门缺少的数据
     */
    void repairDepart();

    void repairSaleOppAgent();
}
