package com.xuebei.crm.customer;

import com.xuebei.crm.company.CompanyMapper;
import com.xuebei.crm.company.CompanyService;
import com.xuebei.crm.customer.agent.CrmAgent;
import com.xuebei.crm.customer.agent.CrmAgentLink;
import com.xuebei.crm.department.DeptService;
import com.xuebei.crm.department.WarningBeforeCreateEnum;
import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.excelImport.ExcelImportBatchLog;
import com.xuebei.crm.excelImport.ExcelImportService;
import com.xuebei.crm.excelImport.ImportResult;
import com.xuebei.crm.journal.*;
import com.xuebei.crm.member.Member;
import com.xuebei.crm.member.MemberService;
import com.xuebei.crm.opportunity.Opportunity;
import com.xuebei.crm.opportunity.OpportunityService;
import com.xuebei.crm.project.ProjectMapper;
import com.xuebei.crm.sample.SampleService;
import com.xuebei.crm.utils.CrmDateUtils;
import com.xuebei.crm.utils.UUIDGenerator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private DeptService deptService;

    @Autowired
    private JournalService journalService;

    @Autowired
    private OpportunityService opportunityService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    CompanyService companyService;

    @Autowired
    private ExcelImportService excelImportService;

    private static final String AUTHENTICATION_ERROR_MSG = "用户没有改操作权限";
    private static final String DEPT_NAME_BLANK_ERROR_MSG = "部门名称不能为空";

    @RequestMapping("searchCustInfo")
    public String searchInfo() {
        return "searchCustomerInfo";
    }

    private String acquireUserId(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("userId");
    }

    @RequestMapping("add")
    public String addCustomer(String customerId
            , ModelMap modelMap) {
        if (!StringUtils.isBlank(customerId)) {
            modelMap.addAttribute("customerId", customerId);
        }
        return "addCustomer";
    }

    @RequestMapping("addAgent")
    public String addAgent(String agentId,
                           ModelMap modelMap, HttpServletRequest request) {
        modelMap.addAttribute("agentId", agentId);
        if (StringUtils.isBlank(agentId)) {
            modelMap.addAttribute("userType", "ADMIN");
        } else {
            String userType = getUserType(request);
            modelMap.addAttribute("userType", userType);
        }
        return "addAgent";
    }

    /**
     * 获取用户类型
     *
     * @param request:请求
     * @return
     * @throws
     * @author zxl
     * @date 10:50 2019/3/21
     * @since
     */
    private String getUserType(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        String userType = companyMapper.queryUserType(userId);
        return userType;
    }

    @RequestMapping("delCustomer")
    public GsonView delCustomer(@RequestParam("customerId") String customerId,
                                HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", false);

        String userId = (String) request.getSession().getAttribute("userId");
        String userType = companyMapper.queryUserType(userId);
        if ("ADMIN".equals(userType)) {
            boolean res = customerService.delSchool(customerId);
            if (res) {
                gsonView.addStaticAttribute("successFlg", true);
            } else {
                gsonView.addStaticAttribute("errMsg", "删除出错");
            }
        } else {
            gsonView.addStaticAttribute("errMsg", "无权限");
        }
        return gsonView;
    }

    @RequestMapping("new")
    public GsonView newCustomer(CustomerViewModel customerViewModel,
                                HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        Customer customer = customerService.getCrmCustomerByName(customerViewModel.getName());
        if (customer != null) {
            gsonView.addStaticAttribute("exist", true);
            return gsonView;
        }
        customerViewModel.setCustomerId(UUIDGenerator.genUUID());
        customerViewModel.setCreatorId((String) request.getSession().getAttribute("userId"));
        customerViewModel.setCreateTs(CrmDateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        customerViewModel.setUpdaterId(customerViewModel.getCreatorId());
        customerViewModel.setIsExcelImport(0);
        if (StringUtils.isBlank(customerViewModel.getProfile())) {
            customerViewModel.setProfile(null);
        }
        if (StringUtils.isBlank(customerViewModel.getWebsite())) {
            customerViewModel.setWebsite(null);
        }
        customerService.newSchool(customerViewModel);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    /**
     * 新增代理商
     *
     * @return
     * @throws
     * @author zxl
     * @date
     * @since
     */
    @RequestMapping("newAgent")
    public GsonView newAgent(CrmAgent crmAgent,
                             HttpServletRequest request) {
        CrmAgent tmp=customerMapper.getAgentByAgentName(crmAgent.getCompanyName());
        if(tmp!=null){
            return GsonView.createErrorView("代理商名称在系统中已存在，如您未创建过此代理商，请联系管理员查看重名代理商");
        }
        crmAgent.setAgentId(UUIDGenerator.genUUID());
        String userId = (String) request.getSession().getAttribute("userId");
        String companyUser = companyMapper.queryCrmUserId(userId);
        String userName = sampleService.getUserNameById(companyUser);
        crmAgent.setCreateId(userId);
        crmAgent.setCreateName(userName);
        crmAgent.setUpdaterId(userId);
        crmAgent.setIsExcelImport(0);
        crmAgent.setCreateTs(CrmDateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        if (customerService.newAgent(crmAgent)) {
            GsonView gsonView = GsonView.createSuccessView();
            gsonView.addStaticAttribute("agentId", crmAgent.getAgentId());
            return gsonView;
        } else {
            return GsonView.createErrorView("数据库插入数据失败");
        }
    }


    /**
     * 新增代理商联系人
     *
     * @param crmAgentLink:
     * @return
     * @throws
     * @author zxl
     * @date 17:22 2019/3/13
     * @since
     */
    @RequestMapping("newAgentLink")
    public GsonView newAgentLink(CrmAgentLink crmAgentLink,
                                 HttpServletRequest request) {
        crmAgentLink.setLinkUserId(UUIDGenerator.genUUID());
        String userId = (String) request.getSession().getAttribute("userId");
        crmAgentLink.setCreateId(userId);
        crmAgentLink.setUpdaterId(userId);
        crmAgentLink.setCreateTs(CrmDateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        if (customerService.newAgentLink(crmAgentLink)) {
            return GsonView.createSuccessView();
        } else {
            return GsonView.createErrorView("数据库插入数据失败");
        }
    }

    /**
     * 删除代理商
     *
     * @return
     * @throws
     * @author zxl
     * @date
     * @since
     */
    @RequestMapping("delAgent")
    public GsonView delAgent(@RequestParam("agentId") String agentId) {
        if (customerService.delAgent(agentId)) {
            return GsonView.createSuccessView();
        } else {
            return GsonView.createErrorView("数据库删除数据失败");
        }
    }

    /**
     * 软删除代理商联系人
     *
     * @param linkUserId:id
     * @return
     * @throws
     * @author zxl
     * @date 18:02 2019/3/13
     * @since
     */
    @RequestMapping("delAgentLink")
    public GsonView delAgentLink(@RequestParam("linkUserId") String linkUserId) {
        if (customerService.delCrmAgentLink(linkUserId)) {
            return GsonView.createSuccessView();
        } else {
            return GsonView.createErrorView("数据库删除数据失败");
        }
    }


    /**
     * 获取学校信息
     *
     * @return
     * @throws
     * @author zxl
     * @date
     * @since
     */
    @RequestMapping("getSchool")
    public GsonView getSchool(@RequestParam("customerId") String customerId,
                              HttpServletRequest request) {
        GsonView gsonView = GsonView.createSuccessView();
        Customer customer = customerMapper.queryCustomer(customerId);
        if (customer != null) {
            gsonView.addStaticAttribute("obj", customer);
            return gsonView;
        } else {
            return GsonView.createErrorView("获取学校失败");
        }
    }

    /**
     * 编辑代理商
     *
     * @return
     * @throws
     * @author zxl
     * @date
     * @since
     */
    @RequestMapping("editAgent")
    public GsonView editAgent(CrmAgent crmAgent,
                              HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        crmAgent.setUpdaterId(userId);
        if (customerService.editAgent(crmAgent)) {
            return GsonView.createSuccessView();
        } else {
            return GsonView.createErrorView("数据库更新数据失败");
        }
    }

    /**
     * 编辑代理商联系人
     *
     * @return
     * @throws
     * @author zxl
     * @date
     * @since
     */
    @RequestMapping("editAgentLink")
    public GsonView editAgentLink(CrmAgentLink crmAgentLink,
                                  HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        crmAgentLink.setUpdaterId(userId);
        if (customerService.updateCrmAgentLink(crmAgentLink)) {
            return GsonView.createSuccessView();
        } else {
            return GsonView.createErrorView("数据库更新数据失败");
        }
    }

    /**
     * 获取代理商
     *
     * @return
     * @throws
     * @author zxl
     * @date
     * @since
     */
    @RequestMapping("getAgent")
    public GsonView getAgent(@RequestParam("agentId") String agentId,
                             HttpServletRequest request) {
        GsonView gsonView = GsonView.createSuccessView();
        CrmAgent crmAgent = customerService.queryCrmAgent(agentId);
        if (crmAgent != null) {
            List<CrmAgentLink> crmAgentLinkList = customerService.queryCrmAgentLinkList(agentId);
            gsonView.addStaticAttribute("obj", crmAgent);
            if (crmAgentLinkList != null && crmAgentLinkList.size() > 0) {
                gsonView.addStaticAttribute("linkObj", crmAgentLinkList.get(0));
            }
            return gsonView;
        } else {
            return GsonView.createErrorView("获取代理商失败");
        }
    }

    /**
     * 获取代理商联系人
     *
     * @return
     * @throws
     * @author zxl
     * @date
     * @since
     */
    @RequestMapping("getAgentLink")
    public GsonView getAgentLink(@RequestParam("linkUserId") String linkUserId,
                                 HttpServletRequest request) {
        GsonView gsonView = GsonView.createSuccessView();
        CrmAgentLink crmAgentLink = customerService.queryCrmAgentLink(linkUserId);
        if (crmAgentLink != null) {
            gsonView.addStaticAttribute("obj", crmAgentLink);
            return gsonView;
        } else {
            return GsonView.createErrorView("获取代理商联系人失败");
        }
    }


    /**
     * 通过代理商ID获取所有未删除的联系人
     *
     * @param agentId:代理商ID
     * @return
     * @throws
     * @author zxl
     * @date 17:49 2019/3/13
     * @since
     */
    @RequestMapping("getAgentLinkList")
    public GsonView getAgentLinkList(@RequestParam("agentId") String agentId) {
        List<CrmAgentLink> crmAgentLinkList = customerService.queryCrmAgentLinkList(agentId);
        if (crmAgentLinkList != null) {
            GsonView gsonView = GsonView.createSuccessView();
            gsonView.addStaticAttribute("crmAgentLinkList", crmAgentLinkList);
            return gsonView;
        } else {
            return GsonView.createErrorView("获取代理商联系人失败");
        }
    }

    /**
     * 编辑学校
     *
     * @return
     * @throws
     * @author zxl
     * @date 2019/03/11
     * @since
     */
    @RequestMapping("editSchool")
    public GsonView editSchool(CustomerViewModel customerViewModel,
                               HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        String userId = (String) request.getSession().getAttribute("userId");
//        String userType = companyMapper.queryUserType(userId);
//        if (!"ADMIN".equals(userType)) {
//            return GsonView.createErrorView("无权限");
//        }
        if (StringUtils.isBlank(customerViewModel.getProfile())) {
            customerViewModel.setProfile(null);
        }
        if (StringUtils.isBlank(customerViewModel.getWebsite())) {
            customerViewModel.setWebsite(null);
        }
        customerViewModel.setUpdaterId(userId);
        customerService.editSchool(customerViewModel);
        return GsonView.createSuccessView();
    }


    @RequestMapping("searchSchool")
    public GsonView searchSchool(@RequestParam("keyword") String keyword) {
        GsonView gsonView = new GsonView();
        List<String> schList = customerMapper.searchSchool(keyword);
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("schList", schList);
        return gsonView;
    }


    @RequestMapping("/addTopDepartment")
    public String addDepartmentPage(@RequestParam("customerId") String customerId,
                                    ModelMap modelMap) {
        modelMap.addAttribute("customerId", customerId);
        return "addTopDepartment";
    }

    @RequestMapping("/addSubDepartment")
    public String addSubDepartment(@RequestParam("deptId") String deptId,
                                   ModelMap modelMap) {

        Department department = customerMapper.queryDepartmentById(deptId);
        modelMap.addAttribute("customerId", department.getCustomer().getCustomerId());
        modelMap.addAttribute("parentDeptId", deptId);
        return "addSubDepartment";
    }


    /**
     * 添加 客户-顶级机构(二级学院) 信息
     * 1. 认证出错：提供的 客户ID 不属于 用户的公司，无权限为它添加机构
     * 2. 机构名检查：机构名不能为空，机构名不能与客户的顶级机构(二级学院)重复
     *
     * @param customerId 客户ID
     * @param deptName   新增的顶级机构(二级学院)名称
     * @param website    机构(学院)的网址
     * @param profile    机构(学院)的简介
     * @return 返回操作状态的JSON
     * successFlg(Bool): 操作成功与否
     * errMsg(String): 当successFlg==false时含有此字段，表示错误信息
     */
    @RequestMapping("/action/addTopDepartment")
    public GsonView addTopDepartment(@RequestParam("customerId") String customerId,
                                     @RequestParam("deptName") String deptName,
                                     @RequestParam(value = "website", required = false) String website,
                                     @RequestParam(value = "profile", required = false) String profile,
                                     HttpServletRequest request) {

        if (StringUtils.isBlank(deptName)) {
            return GsonView.createErrorView(DEPT_NAME_BLANK_ERROR_MSG);
        }
        String userId = (String) request.getSession().getAttribute("userId");
        if (customerMapper.isTopDepartNameExist(customerId, deptName)) {
            Department expiredDept = customerMapper.searchDeptByName(customerId, deptName);
            customerMapper.updateDept(website, profile, expiredDept.getDeptId());
            customerMapper.updateEnclosureApply(expiredDept.getDeptId(), userId);
        } else {
            Customer customer = new Customer();
            customer.setCustomerId(customerId);

            Department dept = new Department();
            dept.setDeptId(UUIDGenerator.genUUID());
            dept.setDeptName(deptName);
            dept.setWebsite(website);
            dept.setProfile(profile);
            dept.setEnclosureStatus(EnclosureStatusEnum.NORMAL);
            dept.setCustomer(customer);
            dept.setParent(new Department());
            dept.setCreateId(userId);
            dept.setCreateTs(new Date());
            String userName = companyMapper.getUserNameByCompanyUserId(userId);
            dept.setCreateName(userName);

            EnclosureApply enclosureApply = new EnclosureApply();
            String reasons = "机构编辑申请";
            enclosureApply.setDeptId(dept.getDeptId());
            enclosureApply.setUserId(userId);
            enclosureApply.setReasons(reasons);
            dept.setIsExcelImport(0);
            customerMapper.insertDepartment(dept);
            customerMapper.insertEnclosureApply(enclosureApply);
        }
        return GsonView.createSuccessView();
    }

    /**
     * 增加专业（二级机构）
     * 1. 用户是否有操作该一级机构的权限，即用户圈了该专业
     * 2. 机构名检查，机构名不为空，机构名不重复
     */
    @RequestMapping("/action/addSubDepartment")
    public GsonView addSubDepartment(@RequestParam("parentDeptId") String parentDeptId,
                                     @RequestParam("deptName") String deptName,
                                     HttpServletRequest request) {
        // TODO： 用户是否圈了该专业检查

        // 检查是否为空
        deptName = deptName.trim();
        if (StringUtils.isBlank(deptName)) {
            return GsonView.createErrorView(DEPT_NAME_BLANK_ERROR_MSG);
        }

        // 检查机构名称是否重复
        if (customerService.isSubDepartmentNameDuplicated(parentDeptId, deptName)) {
            return GsonView.createErrorView("专业(部门)名称重复，请重新输入");
        }
        String userId = (String) request.getSession().getAttribute("userId");

        customerService.addSubDepartment(parentDeptId, deptName,userId);


        return GsonView.createSuccessView();
    }

    /**
     * 为前段提供 实时检测 专业（二级机构）可以使用 的接口
     */
    @RequestMapping("/action/subDepartmentCheck")
    public GsonView subDepartmentCheck(@RequestParam("parentDeptId") String parentDeptId,
                                       @RequestParam("deptName") String deptName) {
        deptName = deptName.trim();
        if (StringUtils.isBlank(deptName)) {
            return GsonView.createErrorView(DEPT_NAME_BLANK_ERROR_MSG);
        }
        if (customerService.isSubDepartmentNameDuplicated(parentDeptId, deptName)) {
            return GsonView.createErrorView("此专业已存在，无法添加");
        }
        return GsonView.createSuccessView();
    }


    @RequestMapping("/action/departmentCheck")
    public GsonView departmentCheck(@RequestParam("deptName") String deptName,
                                    @RequestParam("customerId") String customerId,
                                    HttpServletRequest request) {
        deptName = deptName.trim();
        if (StringUtils.isBlank(deptName)) {
            return GsonView.createErrorView(DEPT_NAME_BLANK_ERROR_MSG);
        }
        String userId = (String) request.getSession().getAttribute("userId");
        WarningBeforeCreateEnum warning = deptService.warningBeforeCreate(deptName, customerId, userId);
        switch (warning) {
            case APPLY_BY_ME:
                return GsonView.createErrorView(WarningBeforeCreateEnum.APPLY_BY_ME.getName());
            case APPLY_BY_OTHERS:
                return GsonView.createErrorView(WarningBeforeCreateEnum.APPLY_BY_OTHERS.getName());
            default:
                break;
        }
        return GsonView.createSuccessView();
    }

    @RequestMapping("addContactsPage")
    public String addContactsPage(@RequestParam("deptId") String deptId,
                                  ModelMap modelMap,
                                  HttpServletRequest request) {
        Department department = customerMapper.queryDepartmentById(deptId);
        modelMap.addAttribute("customerId", department.getCustomer().getCustomerId());
        modelMap.addAttribute("deptId", deptId);

        // 部门ID空 或 用户不能修改该部门（因为客户不属于用户的公司）
        Department dept = customerMapper.queryDepartmentById(deptId);
//        if (dept == null || !customerService.isUserHasCustomer(acquireUserId(request), dept.getCustomer().getCustomerId())) {
//            return "error/404";
//        }

        // 判断部门是顶级部门
        if (dept.getParent() == null || dept.getParent().getDeptId() == null) {
            modelMap.addAttribute("isTopDept", true);
        } else {
            modelMap.addAttribute("isTopDept", false);
        }

        return "addContacts";
    }

    /**
     * 添加联系人信息
     * 1. 检查参数中的 deptId 对应的客户(公司学校)是否为用户所有 --权限检查
     * 2. 直属于顶级机构(二级学院)的联系人contactsTypeId应为空。不直属的不能为空，且要检查ID是否属于该客户
     * 3. 联系人姓名不能为空，手机号/座机号 至少填一个
     * 4. 手机号、邮箱号 格式检查
     *
     * @param deptId         添加联系人的部门 ID (把联系人加入到哪个部门中)
     * @param contactsTypeId 添加联系人的类型 (注意：顶级机构必为空，不顶级的必须不空)
     * @param contacts       添加联系人信息
     * @return 返回操作状态的JSON
     */
    @RequestMapping("/action/addContacts")
    public GsonView addContacts(@RequestParam("deptId") String deptId,
                                @RequestParam(value = "contactsTypeId", required = false) String contactsTypeId,
                                Contacts contacts, @RequestParam("title") String title,
                                HttpServletRequest request) {
        //final String TOP_DEPT_CONTACTS_TYPE_NOT_NULL_ERROR_MSG = "顶级机构中联系人不允许有职位";
        final String SUB_DEPT_CONTACTS_NULL_ERROR_MSG = "机构中联系人需要有职位信息";
        final String REAL_NAME_BLANK_ERROR_MSG = "联系人姓名不能为空";
        //final String TEL_AND_PHONE_BLANK_ERROR_MSG ="联系人手机号和座机号不能同时为空";
        final String TEL_NOT_ELEVEN_ERROR_MSG = "请填写正确的11位手机号码";
        // 权限检查
        Department dept = customerMapper.queryDepartmentById(deptId);
        String userId = acquireUserId(request);
        if (dept == null || !customerService.isUserHasCustomer(userId, dept.getCustomer().getCustomerId())) {
            return GsonView.createErrorView(AUTHENTICATION_ERROR_MSG);
        }

        // 联系人类型检查
//        if (dept.getParent() == null || dept.getParent().getDeptId() == null) {
////            if (contactsTypeId != null) {
////                return GsonView.createErrorView(TOP_DEPT_CONTACTS_TYPE_NOT_NULL_ERROR_MSG);
////            }
//        } else {
        if (contactsTypeId == null || contactsTypeId.equals("")) {
            return GsonView.createErrorView(SUB_DEPT_CONTACTS_NULL_ERROR_MSG);
        }
//        System.out.println(contactsTypeId);
//        ContactsType type = customerMapper.queryContactsTypeById(contactsTypeId);
//        if (type == null || !type.getCustomerId().equals(dept.getCustomer().getCustomerId())) {
//            return GsonView.createErrorView(AUTHENTICATION_ERROR_MSG + ", 机构类型错误");
//        }
//        }

        // 联系人信息
        if (contacts.getRealName() == null || StringUtils.isBlank(contacts.getRealName())) {
            return GsonView.createErrorView(REAL_NAME_BLANK_ERROR_MSG);
        }
        //联系人手机号和座机号不能同时为空 条件取消
//        if ((contacts.getTel() == null || StringUtils.isBlank(contacts.getTel())) &&
//                (contacts.getPhone() == null || StringUtils.isBlank(contacts.getPhone()))) {
//            return GsonView.createErrorView(TEL_AND_PHONE_BLANK_ERROR_MSG);
//        }
        if ((contacts.getTel() != null && !StringUtils.isBlank(contacts.getTel())) && contacts.getTel().length() != 11) {
            return GsonView.createErrorView(TEL_NOT_ELEVEN_ERROR_MSG);
        }
        // 添加联系人
        ContactsType contactsType = new ContactsType();
        contactsType.setContactsTypeId(contactsTypeId);

        contacts.setDepartment(dept);
        contacts.setContactsType(contactsType);
        if (title.equals("添加联系人")) {
            System.out.println("插入");
            contacts.setContactsId(UUIDGenerator.genUUID());
            contacts.setCreateTs(new Date());
            contacts.setCreateId(userId);
            contacts.setCreateName(companyService.getUserNameByCompanyUserId(userId));
            customerMapper.insertContacts(contacts);
        } else {
            System.out.println(contacts.getDepartment().getDeptId() + "," + contacts.getContactsId());
            customerMapper.updateContacts(contacts);

        }
        return GsonView.createSuccessView();
    }


    @RequestMapping("/organization")
    public String organization(@RequestParam("customerId") String customerId, ModelMap modelMap) {
        Customer customer = customerMapper.queryCustomer(customerId);
        modelMap.addAttribute("customerId", customer.getCustomerId());
        modelMap.addAttribute("customerName", customer.getCustomerName());
        return "customer/organization";
    }

    @RequestMapping("/organization/show")
    public GsonView queryDepartment(@RequestParam("customerId") String customerId,
                                    HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        List<Department> departmentList = deptService.departmentList(customerId, userId);
        Map<String, Department> departHash = new HashMap<>();
        List<Department> departmentListAll = deptService.getAllDepart(customerId);
        for (Department item : departmentList) {
            departHash.put(item.getDeptId(), item);
        }
        for (int i = 0; i < departmentListAll.size(); i++) {
            Department item=departmentListAll.get(i);
            Department tmp = departHash.get(item.getDeptId());
            if (tmp != null) {
                departmentListAll.set(i, tmp);
            }else{
                if(StringUtils.isNotBlank(item.getCreateName())){
                    item.setApplyName(item.getCreateName());
                }else{
                    item.setApplyName(deptService.getDepartApplyName(item.getDeptId()));
                }
            }
        }
        List searchList = new ArrayList();
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("customerList", departmentListAll);
        gsonView.addStaticAttribute("searchList", searchList);
        return gsonView;
    }

    @RequestMapping("/checkExistByName")
    public GsonView checkExistByName(@RequestParam("name")String name){
        GsonView gsonView=GsonView.createSuccessView();
        Customer customer = customerService.getCrmCustomerByName(name);
        if(customer==null){
            gsonView.addStaticAttribute("exist",false);
        }else{
            gsonView.addStaticAttribute("exist",true);
        }
        return gsonView;
    }

    @RequestMapping("/organization/apply")
    public GsonView applyDepartment(@RequestParam("applyReasons") String reasons,
                                    @RequestParam("applyDeptId") String applyDeptId,
                                    HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        final String EMPTY_REASONS_ERROR = "申请理由不能为空";
        reasons.trim();
        if (StringUtils.isBlank(reasons)) {
            gsonView.addStaticAttribute("successFlg", false);
            gsonView.addStaticAttribute("errMsg", EMPTY_REASONS_ERROR);
        } else {
            String userId = (String) request.getSession().getAttribute("userId");
            deptService.enclosureApply(applyDeptId, userId, reasons);
            gsonView.addStaticAttribute("successFlg", true);
        }
        return gsonView;
    }

    @RequestMapping("/organization/delayApply")
    public GsonView delayApply(@RequestParam("deptId") String deptId,
                               @RequestParam("delayApplyReasons") String reasons,
                               HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        reasons.trim();
        if (StringUtils.isBlank(reasons)) {
            return GsonView.createErrorView("申请理由不能为空");
        }
        String userId = (String) request.getSession().getAttribute("userId");
        deptService.enclosureDelayApply(deptId, userId, reasons);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("/customerList")
    public String customerList() {
        return "customerList";
    }

    /**
     * 获取代理商列表页面
     *
     * @return
     * @throws
     * @author zxl
     * @date
     * @since
     */
    @RequestMapping("/agentList")
    public String agentList() {
        return "agentList";
    }

    /**
     * 获取代理商列表
     *
     * @param searchWord: 关键词
     * @return * @return: 视图
     * @throws
     * @author zxl
     * @date 14:38 2019/3/13
     * @since
     */
    @RequestMapping("/queryAgentList")
    public GsonView queryAgentList(@RequestParam("searchWord") String searchWord,
                                   HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        List<CrmAgent> crmAgentList = null;
        String userId = (String) request.getSession().getAttribute("userId");
        if (!StringUtils.isBlank(searchWord)) {
            crmAgentList = customerService.queryCrmAgentByKeyWord(searchWord);

        } else {
            crmAgentList = customerService.queryCrmAgentByIds(userId,true);
        }
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("crmAgentList", crmAgentList);
        return gsonView;
    }

    @RequestMapping("/queryCustomer")
    public GsonView queryCustomerInfo(@RequestParam("searchWord") String keyword,
                                      HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        GsonView gsonView = new GsonView();
        //获取包含自身与下属成员的Id集合（字符串集合）
        Set<String> members = journalService.getAllSubordinatesUserId(userId);
        //先获取自身的顾客
        List<Customer> myCustomers = customerService.getMyCustomers(userId);
        if (!StringUtils.isBlank(keyword)) {
            List<Customer> customerList = customerService.queryCustomerInfo(keyword, userId);
            Set<String> customerSet = new HashSet<>();
            HashMap<String, Department> departmentHashMap = getLastDepartsListBySet(customerSet);
            for (Customer customer : customerList) {
                customerSet.add(customer.getCustomerId());
            }
            for (Customer customer : customerList) {
                customer.setCreateName(getCreateName(customer.getCustomerId(), departmentHashMap));
            }
            gsonView.addStaticAttribute("customerList", customerList);
        } else {
            List<Customer> commonCustomers = customerService.getCommonCustomers(userId);
            //再获取下属的顾客
            for (String member : members) {
                if (!member.equals(userId)) {
                    myCustomers.addAll(customerService.getMyCustomers(member));
                }
            }
            Set<String> customerSet = new HashSet<>();
            for (Customer customer : commonCustomers) {
                customerSet.add(customer.getCustomerId());
            }
            for (Customer customer : myCustomers) {
                customerSet.add(customer.getCustomerId());
            }
            HashMap<String, Department> departmentHashMap = getLastDepartsListBySet(customerSet);

            for (Customer customer : commonCustomers) {
                customer.setCreateName(getCreateName(customer.getCustomerId(), departmentHashMap));
            }
            for (Customer customer : myCustomers) {
                customer.setCreateName(getCreateName(customer.getCustomerId(), departmentHashMap));
            }
            gsonView.addStaticAttribute("commonCustomers", commonCustomers);
            gsonView.addStaticAttribute("myCustomers", myCustomers);
        }
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }


    private String getCreateName(String customerId, HashMap<String, Department> departmentHashMap) {
        Department department = departmentHashMap.get(customerId);
        if (department != null) {
            String createName = department.getCreateName();
            String createId = department.getCreateId();
            if (StringUtils.isBlank(createName) && !StringUtils.isBlank(createId)) {
                createName = companyService.getUserNameByCompanyUserId(createId);
            }
            return department.getDeptName() + "-" + createName;
        } else {
            return "";
        }
    }

    private HashMap<String, Department> getLastDepartsListBySet(Set<String> customerSet) {
        if (customerSet.size() > 0) {
            List<Department> departments = deptService.getLastDepartBySet(customerSet);
            HashMap<String, Department> hashMap = new HashMap<>(customerSet.size());
            for (Department department : departments) {
                hashMap.put(department.getCustomerId(), department);
            }
            return hashMap;
        } else {
            return new HashMap<>();
        }
    }

    @RequestMapping("/searchCompany")
    public GsonView searchCustomerInfo(@RequestParam("searchWord") String keyword,
                                       @RequestParam("customerId") String customerId,
                                       HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        String userId = (String) request.getSession().getAttribute("userId");
        List<Department> departmentList = deptService.departmentList(customerId, userId);
        deptService.filterDepartmentList(departmentList, keyword);
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("scustomerList", departmentList);
        return gsonView;
    }

    @RequestMapping("/getMyCustomers")
    public GsonView getMyCustomers(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        List<Customer> myCustomers = customerService.getMyCustomers(userId);//"57259d54f9994209a813e8ad2b297b3a"
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("myCustomers", myCustomers);
        return gsonView;
    }

    @RequestMapping("/lastTime")
    public GsonView lastTime(@RequestParam("customerId") String customerId) {
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("lastTime", customerService.lastFollowTs(customerId));
        return gsonView;
    }

    @RequestMapping("/editContactsPage")
    public String editContactsPage(@RequestParam("contactsId") String contactsId, @RequestParam("deptId") String deptId, @RequestParam("customerId") String customerId,
                                   ModelMap modelMap) {
        modelMap.addAttribute("contactsId", contactsId);
        modelMap.addAttribute("customerId", customerId);
        modelMap.addAttribute("deptId", deptId);
        return "addContacts";
    }

    @RequestMapping("/action/getContacts")
    public GsonView getContacts(@RequestParam("contactsId") String contactsId) {
        GsonView gsonView = new GsonView();
        try {
            Contacts contacts = customerMapper.queryContactsById(contactsId);
            gsonView.addStaticAttribute("successFlg", true);
            gsonView.addStaticAttribute("contacts", contacts);
        } catch (Exception e) {
            gsonView.addStaticAttribute("successFlg", false);
        }
        return gsonView;
    }

    @RequestMapping("/customerInfo")
    public String customerInfo(@RequestParam("customerId") String customerId,
                               ModelMap modelMap, HttpServletRequest request) {
        Customer customer = customerMapper.queryCustomer(customerId);
        modelMap.addAttribute("customerId", customerId);
        modelMap.addAttribute("customerName", customer.getCustomerName());
        modelMap.addAttribute("profile", customer.getProfile());
        modelMap.addAttribute("website", customer.getWebsite());
        modelMap.addAttribute("customerType", customer.getCustomerType().getName());
        modelMap.addAttribute("customerFrom",customer.getCustomerFrom().getName());
        modelMap.addAttribute("area", getStr(customer.getRegion()) + " " + getStr(customer.getProvince()) + " " + getStr(customer.getCity()));
        String userType = getUserType(request);
        modelMap.addAttribute("userType", userType);
        return "customerInfo";
    }

    /**
     * 代理商详情页面
     *
     * @param agentId: 代理商ID
     * @return
     * @throws
     * @author zxl
     * @date 16:42 2019/3/13
     * @since
     */
    @RequestMapping("/agentInfo")
    public String agentInfo(@RequestParam("agentId") String agentId,
                            ModelMap modelMap, HttpServletRequest request) {
        CrmAgent crmAgent = customerService.queryCrmAgent(agentId);
        modelMap.addAttribute("agentId", agentId);
        modelMap.addAttribute("companyName", crmAgent.getCompanyName());
        modelMap.addAttribute("profile", crmAgent.getProfile());
        modelMap.addAttribute("website", crmAgent.getWebsite());
        modelMap.addAttribute("customerFrom", crmAgent.getCustomerFrom().getName());
        modelMap.addAttribute("cooperation", crmAgent.getCooperation().getName());
        modelMap.addAttribute("cooperationType", crmAgent.getCooperationType().getName());
        modelMap.addAttribute("customerLevel", crmAgent.getCustomerLevel().getName());
        modelMap.addAttribute("area", getStr(crmAgent.getRegion()) + " " + getStr(crmAgent.getProvince()) + " " + getStr(crmAgent.getCity()));
        String userType = getUserType(request);
        modelMap.addAttribute("userType", userType);
        return "agentInfo";
    }

    private String getStr(String input){
        if(StringUtils.isBlank(input)){
            return "";
        }else{
            return input;
        }
    }


    /**
     * 联系人详情界面
     *
     * @param linkUserId:id
     * @return
     * @throws
     * @author zxl
     * @date 13:41 2019/3/14
     * @since
     */
    @RequestMapping("/agentLinkInfo")
    public String agentLinkInfo(@RequestParam("linkUserId") String linkUserId,
                                ModelMap modelMap, HttpServletRequest request) {
        CrmAgentLink crmAgentLink = customerService.queryCrmAgentLink(linkUserId);
        if (crmAgentLink != null) {
            modelMap.addAttribute("linkUserId", crmAgentLink.getLinkUserId());
            modelMap.addAttribute("agentId", crmAgentLink.getAgentId());
            modelMap.addAttribute("linkName", crmAgentLink.getLinkName());
            modelMap.addAttribute("linkPosition", crmAgentLink.getLinkPosition());
            if ("FEMALE".equals(crmAgentLink.getLinkGeneral())) {
                modelMap.addAttribute("linkGeneral", "女");
            } else {
                modelMap.addAttribute("linkGeneral", "男");
            }

            modelMap.addAttribute("linkMobile", crmAgentLink.getLinkMobile());
            modelMap.addAttribute("linkPhone", crmAgentLink.getLinkPhone());
            modelMap.addAttribute("linkWeixin", crmAgentLink.getLinkWeixin());
            modelMap.addAttribute("linkQQ", crmAgentLink.getLinkQQ());
            modelMap.addAttribute("linkMail", crmAgentLink.getLinkMail());
            modelMap.addAttribute("linkBg", crmAgentLink.getLinkBg());
            String userType = getUserType(request);
            modelMap.addAttribute("userType", userType);
        }
        return "agentLinkInfo";
    }

    /**
     * 添加联系人界面
     *
     * @param agentId:代理商ID
     * @return
     * @throws
     * @author zxl
     * @date 13:48 2019/3/14
     * @since
     */
    @RequestMapping("/addAgentLink")
    public String addAgentLink(String agentId,
                               String linkUserId,
                               ModelMap modelMap) {
        modelMap.addAttribute("agentId", agentId);
        modelMap.addAttribute("linkUserId", linkUserId);
        return "addAgentLink";
    }

    /**
     * 代理商子页面跟进记录
     *
     * @param
     * @return
     * @throws
     * @author zxl
     * @date 15:26 2019/3/14
     * @since
     */
    @RequestMapping("/agentRelJour")
    public String agentRelJour(@RequestParam("agentId") String agentId, String companyName, ModelMap modelMap) {
        modelMap.addAttribute("companyName", companyName);
        modelMap.addAttribute("agentId", agentId);
        return "agentRelJour";
    }

    /**
     * 代理商子页面跟进记录
     *
     * @param
     * @return
     * @throws
     * @author zxl
     * @date 15:26 2019/3/14
     * @since
     */
    @RequestMapping("/agentRelOpp")
    public String agentRelOpp(@RequestParam("agentId") String agentId, String companyName, ModelMap modelMap) {
        modelMap.addAttribute("companyName", companyName);
        modelMap.addAttribute("agentId", agentId);
        return "agentRelOpp";
    }


    @RequestMapping("queryOpportunityByAgentId")
    public GsonView queryOpportunityByAgentId(@RequestParam("agentId") String agentId
            , HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        List<Opportunity> opportunities = opportunityService.
                queryOpportunityByAgentId(userId, agentId);
        GsonView gsonView = GsonView.createSuccessView();
        gsonView.addStaticAttribute("opportunityList", opportunities);
        return gsonView;
    }


    @RequestMapping("queryJournalListByAgentId")
    public GsonView queryJournalListByAgentId(@RequestParam("agentId") String agentId
            , HttpServletRequest request) {
        GsonView gsonView = GsonView.createSuccessView();

        List<VisitAgentViewModel> res = journalService.getVisitAgentViewModelList(agentId);
        gsonView.addStaticAttribute("journalList", res);

        return gsonView;
    }


    @RequestMapping("/contactsInfo")
    public String contactsInfoPage(@RequestParam("contactsId") String contactsId, HttpServletRequest request,
                                   ModelMap modelMap) {
        Contacts contacts = customerMapper.queryContactsById(contactsId);
        JournalSearchParam param = new JournalSearchParam();
        param.setContactsId(contactsId);
        String userId = (String) request.getSession().getAttribute("userId");
        param.setUserId(userId);
        List<Journal> journals = journalService.searchJournalFaster(param);
        List<Opportunity> opportunities = projectMapper.queryOpportunitySimple(contactsId);
        for (Opportunity opportunity : opportunities) {
            opportunity.setTotalName("");
        }
        filterJournals(journals, contactsId);
        //List<FollowUpRecord> followUpRecords = customerMapper.queryFollowUpRecordsByContactsId(contactsId);
        modelMap.addAttribute("followUpRecords", journals);
        modelMap.addAttribute("contacts", contacts);
        modelMap.addAttribute("opportunities", opportunities);
        return "contactsInfo";
    }


    private void filterJournals(List<Journal> journals, String contactsId) {
        for (Journal journal : journals) {
            if(journal.getVisitRecords()!=null&&journal.getVisitRecords().size()>0){
                Iterator<VisitRecord> iterator = journal.getVisitRecords().iterator();
                while (iterator.hasNext()) {
                    VisitRecord visitRecord = iterator.next();
                    boolean contains = false;
                    if(CollectionUtils.isNotEmpty(visitRecord.getChosenContacts())){
                        for (Contacts contacts : visitRecord.getChosenContacts()) {
                            if (contactsId.equals(contacts.getContactsId())) {
                                contains = true;
                                break;
                            }
                        }
                        if (!contains) {
                            iterator.remove();
                        }
                    }
                }
            }
        }
    }

    @RequestMapping("/editCustomer")
    public String editCustomerPage(@RequestParam("customerId") String customerId,
                                   HttpServletRequest request,
                                   ModelMap modelMap) {
        String userId = (String) request.getSession().getAttribute("userId");

        List<Member> subMemberList = memberService.searchSubMemberList(userId);
        List<Department> deptList = deptService.myDepartmentList(customerId, userId,subMemberList);

        modelMap.addAttribute("customerId", customerId);
        modelMap.addAttribute("departments", deptList);

        return "editCustomer";
    }

    /**
     *  excel 导入代理商页面
     *
    */
    @RequestMapping("/agentInput")
    public String agentInput() {
        return "excelInput/agentInput";
    }

    /**
     * excel 导入代理商
     */
    @RequestMapping("/loadAgentExcel")
    public GsonView loadAgentExcel(@RequestParam MultipartFile file, @RequestParam String excelName, HttpServletRequest request) {
        String userId = acquireUserId(request);
        ImportResult res = excelImportService.importAgentExcel(file, excelName, userId);
        GsonView gsonView = GsonView.createSuccessView();
        gsonView.addStaticAttribute("res", res);
        gsonView.addStaticAttribute("createTime",CrmDateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        return gsonView;
    }

    @RequestMapping("/queryImportHistory")
    public GsonView queryImportHistory(HttpServletRequest request,@RequestParam("type") Integer type) {
        String userId = acquireUserId(request);
        GsonView gsonView = GsonView.createSuccessView();
        List<ExcelImportBatchLog> list = excelImportService.getExcelImportBatchLogList(userId,type);
        gsonView.addStaticAttribute("historyList", list);
        return gsonView;
    }

    /**
     * excel 导入院校
     */
    @RequestMapping("/loadSchoolExcel")
    public GsonView loadSchoolExcel(@RequestParam MultipartFile file, @RequestParam String excelName, HttpServletRequest request) {
        String userId = acquireUserId(request);
        ImportResult res = excelImportService.importSchoolExcel(file, excelName, userId);
        GsonView gsonView = GsonView.createSuccessView();
        gsonView.addStaticAttribute("res", res);
        gsonView.addStaticAttribute("createTime",CrmDateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        return gsonView;
    }
}
