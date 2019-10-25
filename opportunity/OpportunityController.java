package com.xuebei.crm.opportunity;

import com.xuebei.crm.company.CompanyMapper;
import com.xuebei.crm.customer.Customer;
import com.xuebei.crm.customer.CustomerService;
import com.xuebei.crm.customer.Department;
import com.xuebei.crm.customer.agent.AgentLinkSimpleModel;
import com.xuebei.crm.customer.agent.AgentLinkViewModel;
import com.xuebei.crm.department.DeptService;
import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.excelImport.ExcelImportService;
import com.xuebei.crm.excelImport.ImportResult;
import com.xuebei.crm.journal.VisitRecord;
import com.xuebei.crm.member.Member;
import com.xuebei.crm.member.MemberService;
import com.xuebei.crm.project.ProjectContacts;
import com.xuebei.crm.project.ProjectService;
import com.xuebei.crm.sample.SampleService;
import com.xuebei.crm.utils.CrmDateUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rong Weicheng on 2018/7/9.
 */
@Controller
@RequestMapping("/opportunity")
public class OpportunityController {

    @Autowired
    private OpportunityService opportunityService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OpportunityMapper opportunityMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private DeptService deptService;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private ExcelImportService excelImportService;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        // 转换日期格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    @RequestMapping("")
    public String opportunity() {
        return "opportunity";
    }

    @RequestMapping("opportunityDl")
    public String opportunityDl(@Param("oppId")String oppId,ModelMap modelMap) {
        modelMap.addAttribute("oppId",oppId);
        return "opportunityDl";
    }

    @RequestMapping("detail")
    public String opportunityDetail() {
        return "opportunityDetail";
    }

    @RequestMapping("newSale")
    public String newSale() {
        return "newSale";
    }

    @RequestMapping("getCustomers")
    public GsonView getCustomers(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        GsonView gsonView = new GsonView();
        List<Customer> customerList = opportunityService.getSimpleMyCustomers(userId);//test userId "57259d54f9994209a813e8ad2b297b3a"
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("customerList", customerList);
        return gsonView;
    }


    @RequestMapping("/getDepartMentLinks")
    public GsonView getDepartMentLinks(@RequestParam("deptId") String deptId) {
        GsonView gsonView = GsonView.createSuccessView();
        Department department = deptService.getDepartmentLink(deptId);
        gsonView.addStaticAttribute("item", department);
        return gsonView;
    }

    @RequestMapping("getAgentLinks")
    public GsonView getAgentLinks(HttpServletRequest request) {
        GsonView gsonView = GsonView.createSuccessView();
        String userId = (String) request.getSession().getAttribute("userId");
        List<AgentLinkViewModel> resList = customerService.getAgentLinkViewModelList(userId, false);
        gsonView.addStaticAttribute("agentList", resList);
        return gsonView;
    }

    @RequestMapping("addSale")
    public GsonView addSale(@RequestBody Opportunity opportunity,
                            HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        opportunity.setUserId(userId);
        opportunity.setCreate_ts(new Date());
        opportunity.setIsExcelImport(0);
        opportunityService.addSale(opportunity);
        opportunityService.addOpportunityContact(opportunity.getOpportunityId(), opportunity.getDecisionMakerId());
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("queryOpportunity")
    public GsonView queryOpportunity(OpportunitySearchParam opportunitySearchParam,
                                     HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        opportunitySearchParam.setScene(userId);
        GsonView gsonView = new GsonView();
        boolean admin = false;
        String userType = companyMapper.queryUserType(userId);
        if ("ADMIN".equals(userType)) {
            admin = true;
        }
        List<Member> membersWhole = memberService.searchSubMemberList(userId);
        List<String> subUserIdWhole = new ArrayList<>();
        Iterator<Member> itWhole = membersWhole.iterator();
        while (itWhole.hasNext()) {
            subUserIdWhole.add(itWhole.next().getMemberId());
        }
        if(subUserIdWhole.isEmpty()){
            subUserIdWhole.add("000");
        }
        opportunitySearchParam.setCurUserId(userId);
        opportunitySearchParam.setAdmin(admin);
        opportunitySearchParam.setSubUserIdWhole(subUserIdWhole);

        if (opportunitySearchParam.getUserId() != null && !opportunitySearchParam.getUserId().equals("")) {
            if (opportunitySearchParam.getUserId().equals("mine")) {
                opportunitySearchParam.setUserId(userId);
            } else if (opportunitySearchParam.getUserId().equals("sub")) {
                opportunitySearchParam.setUserId("all");
                List<Member> members = memberService.searchSubMemberList(userId);
                List<String> subUserId = new ArrayList<>();
                Iterator<Member> it = members.iterator();
                if (!members.isEmpty()) {
                    while (it.hasNext()) {
                        subUserId.add(it.next().getMemberId());
                    }
                    opportunitySearchParam.setSubUserId(subUserId);
                } else {
                    subUserId.add("0");
                    opportunitySearchParam.setSubUserId(subUserId);
                }
            }
        }

        if (opportunitySearchParam.getSubUser() != null && !opportunitySearchParam.getSubUser().equals("")) {
            opportunitySearchParam.setChooseSubUser((opportunitySearchParam.getSubUser().split(",")));
        }
        List<String> createIds = sampleService.getUserIdsByUserName(opportunitySearchParam.getKeyWord());
        opportunitySearchParam.setCreateIds(createIds);

        List<Opportunity> opportunities = opportunityService.queryOpportunity(opportunitySearchParam);
        gsonView.addStaticAttribute("opportunityList", opportunities);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("subMemberList")
    public GsonView subMemberList(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        GsonView gsonView = new GsonView();
        List<Member> members = memberService.searchSubMemberList(userId);
        gsonView.addStaticAttribute("subMemberList", members);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("opportunityToDetail")
    public GsonView opportunityToDetail(@RequestParam("opportunityId")String opportunityId, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        GsonView gsonView = new GsonView();
        request.getSession().setAttribute("opportunityId",opportunityId);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("opportunityDetail")
    public GsonView opportunityDetal(@RequestParam("opportunityId")String opportunityId ,
                                     HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        Opportunity opportunity = opportunityService.opportunityDetail(opportunityId);
        if(opportunity.getCheckDate() != null){
            opportunity.setCheckDateString(opportunity.getCheckDateString());
        }
        if(opportunity.getClinchDate()!=null){
            opportunity.setClinchDateString(opportunity.getClinchDateString());
        }
        //这里是决策人信息
        ProjectContacts contact = projectService.getProjectDetail(opportunityId).getProjectContacts();
        ProjectContacts charge = projectService.queryProjectDetailByChargId(opportunity.getChargeId()).getProjectContacts();
        AgentLinkSimpleModel agentLinkSimpleModel = customerService.getAgentLinkSimpleModel(opportunity.getAgentLinkId());

        gsonView.addStaticAttribute("opportunity", opportunity);
        gsonView.addStaticAttribute("opportunityId", opportunityId);
        String creatorName = opportunityService.queryOpportunityCreator(opportunityId);
        gsonView.addStaticAttribute("creatorName", creatorName);

        if (contact == null) {
            contact = charge;
        }
        gsonView.addStaticAttribute("contact", contact);
        gsonView.addStaticAttribute("chage", charge);
        gsonView.addStaticAttribute("agent", agentLinkSimpleModel);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("modification")
    public GsonView modificationOpportunity(@RequestBody Opportunity opportunity,
                            HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        opportunity.setUserId(userId);
        opportunityService.modifyOpportunity(opportunity);
        opportunityService.addModificationRecord(opportunity.getOpportunityId(),userId,opportunity.getLastStatus(),opportunity.getSalesStatus());
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("modificationRecord")
    public GsonView modificationRecord(@RequestParam("opportunityId")int opportunityId,
                                            HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        List<OpportunityModify> record = opportunityService.queryModifyRecord(opportunityId);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("modificationRecord",record);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("opportunityRecord")
    public GsonView opportunityVisitRecord(@RequestParam("opportunityId")int opportunityId,
                                       HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        List<VisitRecord> visitRecords = opportunityService.queryVisitRecord(opportunityId);
        List<Support> applySupports = opportunityService.queryApplySupport(opportunityId);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("visitRecords",visitRecords);
        gsonView.addStaticAttribute("applySupports",applySupports);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }



    @RequestMapping("applySupport")
    public String applySupport(@RequestParam("salesOpportunityId") Integer salesOpportunityId,
                                ModelMap modelMap) {
        SupportTypeEnum[] supportTypes = SupportTypeEnum.values();
        modelMap.addAttribute("salesOpportunityId", salesOpportunityId);
        modelMap.addAttribute("supportTypes", supportTypes);

        return "applySupport";
    }

    @RequestMapping("/action/submitApplySupport")
    public GsonView submitApplySupport(@RequestParam("salesOpportunityId") Integer salesOpportunityId,
                                       @RequestParam(value = "supportType", required = false) SupportTypeEnum supportType,
                                       @RequestParam(value = "expireDate", required = false) Date expireDate,
                                       @RequestParam(value = "order", required = false) SupportOrderEnum order,
                                       @RequestParam("content") String content,
                                       HttpServletRequest request) {
        if (supportType == null) {
            return GsonView.createErrorView("未填写支持类型");
        }

        if (expireDate == null) {
            return GsonView.createErrorView("未填写截止日期");
        }

        if (order == null) {
            return GsonView.createErrorView("未填写优先级");
        }

        String userId = (String)request.getSession().getAttribute("userId");
        Support support = new Support(salesOpportunityId, supportType, expireDate, order, content, userId);

        opportunityMapper.insertSupport(support);
        return GsonView.createSuccessView();
    }

    @RequestMapping("failReason")
    public GsonView failReason(@RequestParam("opportunityId")int opportunityId,
                               @RequestParam("failReason")String failReason,
                               HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        opportunityService.insertFailReason(opportunityId,failReason);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("deleteOpportunity")
    public GsonView deleteOpportunity(@RequestParam("opportunityId")int opportunityId,
                               HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        opportunityService.deleteOpportunity(opportunityId);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("convertOpportunity")
    public GsonView convertOpportunity(@RequestParam("opportunityId")int opportunityId,
                                      HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        opportunityService.convertOpportunity(opportunityId);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("/loadOppExcel")
    public GsonView loadOppExcel(@RequestParam MultipartFile file, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        ImportResult res = excelImportService.importOppExcel(file, userId);
        GsonView gsonView = GsonView.createSuccessView();
        gsonView.addStaticAttribute("res", res);
        gsonView.addStaticAttribute("createTime", CrmDateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return gsonView;
    }
}
