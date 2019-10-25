package com.xuebei.crm.statistics;

import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.utils.ColumnExtractor;
import com.xuebei.crm.utils.ResponseExcelWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping("/index")
    public String index(){
        return "statistics";
    }

    @RequestMapping("/info")
    public String info(){
        return "statisticsInfo";
    }

    @RequestMapping("/export")
    public String export(){
        return "statisticsExport";
    }


    /**
     * 获取总体统计概览
     */
    @RequestMapping("/getAllStatistics")
    public GsonView getAllStatistics(StatisticsSearchParam param) {
        StatisticsTotalViewModel viewModel = statisticsService.getStatisticsTotalViewModel(param);
        GsonView gsonView = GsonView.createSuccessView();
        gsonView.addStaticAttribute("totalView", viewModel);
        return gsonView;
    }

    /**
    *   获取详情
    */
    @RequestMapping("/getDetails")
    public GsonView getDetails(StatisticsDetailsParam param) {
        GsonView gsonView = GsonView.createSuccessView();
        switch (param.getStatisticsTypeEnum()){
            //todo 待填充
            case newACustomer: case ACover:
                setGsonView(param, gsonView, 1);
                break;
            case newAgent: case coreAgent:
                setGsonView(param, gsonView, 2);
                break;
            case visitCustomer: case companyVisit:
            case deepVisit: case normalVisit:
            case visistCustomer: case visistDept:
            case visitPresident: case visitDean:
                setGsonView(param, gsonView, 3);
                break;
            case newOpp: case newAOpp:
            case newBOpp: case newCOpp:
            case newDOpp: case DMoney:
                setGsonView(param, gsonView, 4);
                break;
        }
        return gsonView;
    }

    private void setGsonView(StatisticsDetailsParam param, GsonView gsonView, int type) {
        if (type == 1) {
            List<ContactStatisticsViewModel> res = statisticsService.getContactsDetail(param);
            gsonView.addStaticAttribute("res", res);
        } else if (type == 2) {
            List<AgentStatisticsViewModel> res = statisticsService.getAgentDetails(param);
            gsonView.addStaticAttribute("res", res);
        } else if (type == 3) {
            List<VisitStatisticsViewModel> res = statisticsService.getVisitDetails(param);
            gsonView.addStaticAttribute("res", res);
        } else {
            List<OpportunityViewModel> res = statisticsService.getOppoDetails(param);
            gsonView.addStaticAttribute("res", res);
        }
    }

    /**
     * 商机导出Excel
     */
    public void exportOppDetails(StatisticsDetailsParam param, HttpServletResponse response) throws IOException {
        ResponseExcelWriter<OpportunityViewModel> writer = new ResponseExcelWriter<>(buildOppColumnExtractors());
        List<OpportunityViewModel> res = statisticsService.getOppoDetails(param);
        writer.write(response, "商机详情Excel", res);
    }

    /**
     * 构建商机Excel导出
     */
    private List<ColumnExtractor<OpportunityViewModel>> buildOppColumnExtractors() {
        List<ColumnExtractor<OpportunityViewModel>> extractors = new ArrayList<>();
        extractors.add(new ColumnExtractor<>("创建时间", OpportunityViewModel::getCreateTime));
        extractors.add(new ColumnExtractor<>("员工", OpportunityViewModel::getEmployee));
        extractors.add(new ColumnExtractor<>("代理商名称", OpportunityViewModel::getAgent));
        extractors.add(new ColumnExtractor<>("院校", OpportunityViewModel::getCollege));
        extractors.add(new ColumnExtractor<>("二级学院", OpportunityViewModel::getSecondCollege));
        extractors.add(new ColumnExtractor<>("是否圈地", OpportunityViewModel::getWhetherEnclosure));
        extractors.add(new ColumnExtractor<>("报备项目", OpportunityViewModel::getProject));
        extractors.add(new ColumnExtractor<>("计划成交金（元）", OpportunityViewModel::getMoney));
        extractors.add(new ColumnExtractor<>("计划招标时间", OpportunityViewModel::getInviteTime));
        extractors.add(new ColumnExtractor<>("项目级别", OpportunityViewModel::getProjectLevel));
        extractors.add(new ColumnExtractor<>("联系人/代理商销售", OpportunityViewModel::getContact));
        extractors.add(new ColumnExtractor<>("项目决策人", OpportunityViewModel::getDecisionMaker));
        extractors.add(new ColumnExtractor<>("跟进1", OpportunityViewModel::getFollow1));
        extractors.add(new ColumnExtractor<>("跟进2", OpportunityViewModel::getFollow2));
        extractors.add(new ColumnExtractor<>("跟进2", OpportunityViewModel::getFollow3));

        return extractors;
    }

    /**
     * 代理商导出Excel
     */
    public void exportAgentDetails(StatisticsSearchParam param, HttpServletResponse response) throws IOException {
        ResponseExcelWriter<AgentStatisticsViewModel> writer = new ResponseExcelWriter<>(buildAgentColumnExtractors());
        List<AgentStatisticsViewModel> res = statisticsService.getAgentDetails(param);
        writer.write(response, "代理商详情Excel", res);
    }

    /**
     * 构建代理商Excel导出
     */
    private List<ColumnExtractor<AgentStatisticsViewModel>> buildAgentColumnExtractors() {
        List<ColumnExtractor<AgentStatisticsViewModel>> extractors = new ArrayList<>();
        extractors.add(new ColumnExtractor<>("创建时间", AgentStatisticsViewModel::getCreateTime));
        extractors.add(new ColumnExtractor<>("员工", AgentStatisticsViewModel::getEmployee));
        extractors.add(new ColumnExtractor<>("客户来源", AgentStatisticsViewModel::getCustomerFrom));
        extractors.add(new ColumnExtractor<>("代理商名称", AgentStatisticsViewModel::getAgentName));
        extractors.add(new ColumnExtractor<>("客户等级", AgentStatisticsViewModel::getCustomerLevel));
        extractors.add(new ColumnExtractor<>("主要销售成员", AgentStatisticsViewModel::getMainSaleMember));
        extractors.add(new ColumnExtractor<>("背景", AgentStatisticsViewModel::getBackground));
        extractors.add(new ColumnExtractor<>("本月联系", AgentStatisticsViewModel::getThisLinkTimes));
        extractors.add(new ColumnExtractor<>("上月联系", AgentStatisticsViewModel::getLastLinkTimes));
        extractors.add(new ColumnExtractor<>("上上月联系", AgentStatisticsViewModel::getLastLastLinkTimes));

        return extractors;
    }

    /**
     * 客户导出Excel
     */
    public void exportCustomerDetails(StatisticsDetailsParam param, HttpServletResponse response) throws IOException {
        ResponseExcelWriter<ContactStatisticsViewModel> writer = new ResponseExcelWriter<>(buildCustomerColumnExtractors());
        List<ContactStatisticsViewModel> res = statisticsService.getContactsDetail(param);
        writer.write(response, "客户详情Excel", res);
    }

    /**
     * 构建客户Excel导出
     */
    private List<ColumnExtractor<ContactStatisticsViewModel>> buildCustomerColumnExtractors() {
        List<ColumnExtractor<ContactStatisticsViewModel>> extractors = new ArrayList<>();
        extractors.add(new ColumnExtractor<>("创建时间", ContactStatisticsViewModel::getCreateTime));
        extractors.add(new ColumnExtractor<>("员工", ContactStatisticsViewModel::getEmployeeName));
        extractors.add(new ColumnExtractor<>("院校", ContactStatisticsViewModel::getSchool));
        extractors.add(new ColumnExtractor<>("二级学院", ContactStatisticsViewModel::getSubDept));
        extractors.add(new ColumnExtractor<>("联系人", ContactStatisticsViewModel::getLinker));
        extractors.add(new ColumnExtractor<>("职位", ContactStatisticsViewModel::getPosition));
        extractors.add(new ColumnExtractor<>("性别", ContactStatisticsViewModel::getGender));
        extractors.add(new ColumnExtractor<>("电话", ContactStatisticsViewModel::getMobile));
        extractors.add(new ColumnExtractor<>("座机", ContactStatisticsViewModel::getPhone));
        extractors.add(new ColumnExtractor<>("微信", ContactStatisticsViewModel::getWeiChat));
        extractors.add(new ColumnExtractor<>("QQ", ContactStatisticsViewModel::getQQ));
        extractors.add(new ColumnExtractor<>("邮箱", ContactStatisticsViewModel::getMail));

        return extractors;
    }

    /**
     * 拜访导出Excel
     */
    public void exportVisitDetails(StatisticsSearchParam param, HttpServletResponse response) throws IOException {
        ResponseExcelWriter<VisitStatisticsViewModel> writer = new ResponseExcelWriter<>(buildVisitColumnExtractors());
        List<VisitStatisticsViewModel> res = statisticsService.getVisitDetails(param);
        writer.write(response, "拜访详情Excel", res);
    }

    /**
     * 构建拜访Excel导出
     */
    private List<ColumnExtractor<VisitStatisticsViewModel>> buildVisitColumnExtractors() {
        List<ColumnExtractor<VisitStatisticsViewModel>> extractors = new ArrayList<>();
        extractors.add(new ColumnExtractor<>("拜访接待日期", VisitStatisticsViewModel::getReceiveDate));
        extractors.add(new ColumnExtractor<>("员工", VisitStatisticsViewModel::getEmployee));
        extractors.add(new ColumnExtractor<>("拜访类型", VisitStatisticsViewModel::getVisitType));
        extractors.add(new ColumnExtractor<>("拜访深浅", VisitStatisticsViewModel::getVisitDepth));
        extractors.add(new ColumnExtractor<>("外出", VisitStatisticsViewModel::getOut));
        extractors.add(new ColumnExtractor<>("院校", VisitStatisticsViewModel::getCollege));
        extractors.add(new ColumnExtractor<>("二级学院", VisitStatisticsViewModel::getSubDept));
        extractors.add(new ColumnExtractor<>("联系人", VisitStatisticsViewModel::getContact));
        extractors.add(new ColumnExtractor<>("联系方式", VisitStatisticsViewModel::getMobile));
        extractors.add(new ColumnExtractor<>("拜访事由", VisitStatisticsViewModel::getVisitReason));
        extractors.add(new ColumnExtractor<>("拜访过程及结果", VisitStatisticsViewModel::getVisitProcessAndResult));

        return extractors;
    }
}
