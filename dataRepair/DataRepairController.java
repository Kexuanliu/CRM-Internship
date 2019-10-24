/**
 * @author ZXL
 * @date 2019/6/6 16:36
 */
package com.xuebei.crm.dataRepair;

import com.xuebei.crm.dto.GsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dataRepair")
public class DataRepairController {


    @Autowired
    DataRepairService dataRepairService;

    /**
     * 此方法用于修复companyUser的relsFullPath的数据
     */
    @RequestMapping("/repairCompanyUserLeadPath")
    public GsonView repairCompanyUserLeadPath() {
        GsonView gsonView = GsonView.createSuccessView();
        dataRepairService.repairCompanyUserLeadPath();
        return gsonView;
    }

    /**
     * 此方法用于修复职位
     */
    @RequestMapping("/repairPosition")
    public GsonView repairPosition(){
        GsonView gsonView = GsonView.createSuccessView();
        dataRepairService.repairPosition();
        return gsonView;
    }

    /**
     * 此方法用于修复联系人的创建人以及创建时间
     * 使用部门创建时间
     * 如果不存在使用日志最早时间
     * 如果不存在使用默认时间
     * 默认时间2019/1/1
     * 同时更新部门创建时间以及联系人时间
     */
    @RequestMapping("/repairContacts")
    public GsonView repairContacts() {
        GsonView gsonView = GsonView.createSuccessView();
        dataRepairService.repairContacts();
        return gsonView;
    }

    @RequestMapping("/repairSaleOppAgent")
    public GsonView repairSaleOppAgent(){
        GsonView gsonView = GsonView.createSuccessView();
        dataRepairService.repairSaleOppAgent();
        return gsonView;
    }
}
