/**
 * @author ZXL
 * @date 2019/6/21 18:20
 */
package com.xuebei.crm.excelImport;

import com.xuebei.crm.company.CompanyMapper;
import com.xuebei.crm.customer.*;
import com.xuebei.crm.customer.agent.*;
import com.xuebei.crm.journal.*;
import com.xuebei.crm.opportunity.Opportunity;
import com.xuebei.crm.opportunity.OpportunityService;
import com.xuebei.crm.sample.SampleService;
import com.xuebei.crm.user.GenderEnum;
import com.xuebei.crm.utils.CrmDateUtils;
import com.xuebei.crm.utils.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class ExcelImportServiceImpl implements ExcelImportService {

    @Autowired
    private ExcelImportMapper excelImportMapper;

    @Autowired
    private CheckRegionMapper checkRegionMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OpportunityService opportunityService;

    @Autowired
    private JournalService journalService;

    @Autowired
    private CustomerMapper customerMapper;

    private static Map<String, Character> regionNameToNum = new HashMap<String, Character>();

    static {
        regionNameToNum.put("华北", '1');
        regionNameToNum.put("东北", '2');
        regionNameToNum.put("华东", '3');
        regionNameToNum.put("华中", '4');
        regionNameToNum.put("华南", '5');
        regionNameToNum.put("西南", '6');
        regionNameToNum.put("西北", '7');
    }

    private static final String regContactType="正书记、副书记、正校长、副校长、正院长、副院长、正主任、副主任、正处长、副处长、老师";

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Override
    public void insertExcelBatchLog(ExcelImportBatchLog excelImportBatchLog) {
        excelImportMapper.insertExcelBatchLog(excelImportBatchLog);
    }

    @Override
    public void insertExcelImportDetail(ExcelImportDetail excelImportDetail) {
        excelImportMapper.insertExcelImportDetail(excelImportDetail);
    }

    @Override
    public List<ExcelImportBatchLog> getExcelImportBatchLogList(String userId, Integer type) {
        return excelImportMapper.getExcelImportBatchLogList(userId, type);
    }



    /**
     * 导入批次
     *
     * @param userId:         用户ID
     * @param importType:导入类型 1代理商
     * @return
     */
    private Long addExcelImportLog(String userId, Integer importType) {
        ExcelImportBatchLog excelImportBatchLog = new ExcelImportBatchLog();
        excelImportBatchLog.setBatchLogId(System.currentTimeMillis());
        excelImportBatchLog.setCreateId(userId);
        excelImportBatchLog.setImportType(importType);
        excelImportMapper.insertExcelBatchLog(excelImportBatchLog);
        return excelImportBatchLog.getBatchLogId();
    }

    /**
     * 导入详情
     *
     * @param batchId:         批次号
     * @param userId:ID
     * @param executeCode:执行结果
     * @param showInfo:展示结果
     * @param result:是否成功      1成功 0 失败
     * @return
     */
    private void addExceImportDetail(Long batchId, String userId, String executeCode, String showInfo, Integer result) {
        ExcelImportDetail excelImportDetail = new ExcelImportDetail();
        excelImportDetail.setBatchLogId(batchId);
        excelImportDetail.setCreateId(userId);
        excelImportDetail.setExecuteCode(executeCode);
        excelImportDetail.setResult(result);
        excelImportDetail.setShowInfo(showInfo);
        excelImportMapper.insertExcelImportDetail(excelImportDetail);
    }

    //userId是从request直接获取的
    private String getUserNameByUserId(String userId) {
        String companyUser = companyMapper.queryCrmUserId(userId);
        String userName = sampleService.getUserNameById(companyUser);
        return userName;
    }

    private void addErrorMsg(ErrorMsgEnum errorMsgEnum, String showMsg, Integer row, String showInfo, List<ErrorMsg> errorMsgList) {
        ErrorMsg errorMsg = new ErrorMsg();
        errorMsg.setMsg(showMsg);
        errorMsg.setErrorCode(errorMsgEnum.toString());
        errorMsg.setRows(row + 1);
        errorMsg.setShowInfo(showInfo);
        errorMsgList.add(errorMsg);
    }

    /**
     * 代理商导入
     */
    @Override
    public ImportResult importAgentExcel(MultipartFile file, String fileName, String userId) {
        ImportResult importResult = new ImportResult();
        Long batchLogId = addExcelImportLog(userId, ImportTypeEnum.AGENT.getValue());
        try (InputStream inputStream = file.getInputStream();
             Workbook wb = WorkbookFactory.create(inputStream)) {
            Sheet sheet = wb.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            List<ErrorMsg> errorMsgList = new ArrayList<>();
            Integer successCount = 0;
            Integer faildCount = 0;
            String userName = getUserNameByUserId(userId);
            for (int i = 1; i < rows; i++) {
                boolean canInsert = true;
                List<String> errorFieldList = new ArrayList<>();
                Row row = sheet.getRow(i);
                CrmAgent crmAgent = loadAgentByExcelRow(row, errorFieldList, i);
                if (crmAgent != null) {
                    CrmAgentLink crmAgentLink = loadAgentLinkByExcelRow(row);
                    crmAgentLink.setCreateTs(crmAgent.getCreateTs());
                    if (StringUtils.isBlank(crmAgent.getCreateName()) || !crmAgent.getCreateName().equals(userName)) {
                        addErrorMsg(ErrorMsgEnum.USERERROR, ErrorMsgEnum.USERERROR.getName(), i, crmAgent.getCompanyName(), errorMsgList);
                        canInsert = false;
                    }
                    boolean isExist = customerService.checkCrmAgentExistByName(crmAgent.getCompanyName());
                    if (isExist) {
                        addErrorMsg(ErrorMsgEnum.SAMEAGENT, ErrorMsgEnum.SAMEAGENT.getName(), i, crmAgent.getCompanyName(), errorMsgList);
                        canInsert = false;
                    }
                    checkAgentField(crmAgent, errorFieldList);
                    checkAgentLinkField(crmAgentLink, errorFieldList);
                    if (errorFieldList.size() > 0) {
                        addErrorMsg(ErrorMsgEnum.FIELDERROR, StringUtils.join(errorFieldList, ","), i, crmAgent.getCompanyName(), errorMsgList);
                        canInsert = false;
                    }
                    if (canInsert) {
                        crmAgent.setAgentId(UUIDGenerator.genUUID());
                        crmAgent.setCreateId(userId);
                        crmAgent.setUpdaterId(userId);
                        customerService.newAgent(crmAgent);
                        crmAgentLink.setLinkUserId(UUIDGenerator.genUUID());
                        crmAgentLink.setAgentId(crmAgent.getAgentId());
                        crmAgentLink.setCreateTs(crmAgent.getCreateTs());
                        crmAgentLink.setCreateId(userId);
                        crmAgentLink.setUpdaterId(userId);
                        customerService.newAgentLink(crmAgentLink);
                        addExceImportDetail(batchLogId, userId, "SUCCESS", crmAgent.getCompanyName(), 1);
                        successCount++;
                    } else {
                        //取最新的一个错误展示
                        addExceImportDetail(batchLogId, userId, errorMsgList.get(errorMsgList.size() - 1).getErrorCode(), crmAgent.getCompanyName(), 0);
                        faildCount++;
                    }
                }

            }
            importResult.setFailCount(faildCount);
            importResult.setSuccessCount(successCount);
            importResult.setErrorMsgsList(errorMsgList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return importResult;
    }


    private CrmAgent loadAgentByExcelRow(Row row, List<String> errorFieldList, int index) {
        CrmAgent crmAgent = new CrmAgent();
        if (row.getCell(0) == null) {
            return null;
        }
        Date createTime = null;
        try {
            createTime = row.getCell(0).getDateCellValue();//创建日期
        } catch (Exception e) {
            errorFieldList.add("创建日期");
            LOGGER.error(e.getMessage(), e);
        }
        String employeeName = getCellStr(row, 1);//员工姓名
        String region = getCellStr(row, 2);//销售区域
        String province = getCellStr(row, 3);//省
        String city = getCellStr(row, 4);//市
        String agentName = getCellStr(row, 5);//代理商名称
        String agentBg = getCellStr(row, 6);//公司背景
        String agentFrom = getCellStr(row, 7);//客户来源
        String agentProp = getCellStr(row, 8);//合作意向
        String agentLevel = getCellStr(row, 9);//客户等级
        String agentCopType = getCellStr(row, 10);//合作模式
        if (createTime != null) {
            crmAgent.setCreateTs(CrmDateUtils.format(createTime, "yyyy-MM-dd"));
        }
        crmAgent.setCreateName(employeeName);
        crmAgent.setCompanyName(agentName);
        crmAgent.setCustomerFrom(CustomerFromEnum.getCustomerFromEnum(agentFrom));
        crmAgent.setCooperation(CooperationEnum.getCooperationEnum(agentProp));
        crmAgent.setCooperationType(CooperationTypeEnum.getCooperationTypeEnum(agentCopType));
        crmAgent.setCustomerLevel(CustomerLevelEnum.getCustomerLevelEnum(agentLevel));
        crmAgent.setProfile(agentBg);
        crmAgent.setRegion(region);
        crmAgent.setProvince(province);
        crmAgent.setCity(city);
        crmAgent.setIsExcelImport(1);
        return crmAgent;
    }

    private void loadOppByExcelModel(OpporttunityExcelLoadModel loadModel, Opportunity opportunity) {
        opportunity.setOpportunityName(loadModel.getOppName());
        opportunity.setContent(loadModel.getOppDetail());
        if (StringUtils.isNotEmpty(loadModel.getMoney())) {
            opportunity.setAmount(Double.valueOf(loadModel.getMoney()));
        }
        opportunity.setCheckDate(loadModel.getPreDate());
        opportunity.setSalesStatus(translateSaleStage(loadModel.getSaleStage()));
        opportunity.setContainXuebei(translateYesOrNo(loadModel.getContainXuebei()));
        opportunity.setMoneyFrom(translateMoneyFrom(loadModel.getMoneyForm()));
        opportunity.setCreate_ts(loadModel.getCreateTime());
    }

    private Integer translateYesOrNo(String input) {
        switch (input) {
            case "是":
                return 1;
            case "否":
                return 2;
            default:
                return 0;
        }
    }

    private String translateMoneyFrom(String input) {
        switch (input) {
            case "专项资金":
                return "specificFund";
            case "省财政":
                return "provincialFinance";
            case "校内":
                return "school";
            default:
                return "";
        }
    }

    private CrmAgentLink loadAgentLinkByExcelRow(Row row) {
        String agentLinkName = getCellStr(row, 11);//主要销售成员
        String agentLinkPosition = getCellStr(row, 12);//职位
        String agentLinkSex = getCellStr(row, 13);//性别
        String agentLinkPhone = getCellStr(row, 14);//手机号
        String agentLinkBg = getCellStr(row, 15);//背景
        CrmAgentLink crmAgentLink = new CrmAgentLink();
        crmAgentLink.setLinkName(agentLinkName);
        crmAgentLink.setLinkPosition(agentLinkPosition);
        crmAgentLink.setLinkGeneral(tranlateSex(agentLinkSex));
        crmAgentLink.setLinkBg(agentLinkBg);
        crmAgentLink.setLinkMobile(agentLinkPhone);
        return crmAgentLink;
    }


    private boolean checkAgentField(CrmAgent crmAgent, List<String> errorFieldList) {
        boolean res = true;
        if (StringUtils.isBlank(crmAgent.getCreateTs())) {
            errorFieldList.add("增加年月日");
            res = false;
        }
        if (StringUtils.isBlank(crmAgent.getProfile())) {
            errorFieldList.add("公司背景");
            res = false;
        }

        boolean regionRes = checkRegion(crmAgent.getRegion(), crmAgent.getProvince(), crmAgent.getCity(), errorFieldList);
        if (regionRes == false) {
            res = false;
        }

        if (StringUtils.isBlank(crmAgent.getCompanyName())) {
            errorFieldList.add("代理商名称");
            res = false;
        }

        if (crmAgent.getCustomerFrom() == null) {
            errorFieldList.add("客户来源");
            res = false;
        }
        if (crmAgent.getCooperation() == null) {
            errorFieldList.add("合作意向");
            res = false;
        }
        if (crmAgent.getCooperationType() == null) {
            errorFieldList.add("合作模式");
            res = false;
        }
        if (crmAgent.getCustomerLevel() == null) {
            errorFieldList.add("客户等级");
            res = false;
        }
        return res;
    }

    private boolean checkRegion(String regionName, String provName, String cityName, List<String> errorFieldList) {
        boolean res = true;
        String pid = checkRegionMapper.getPidByPName(provName);  // given province id
        String pidOfCity = checkRegionMapper.getPidByCName(cityName);  // given province id of given city name
        boolean whetherNull = false;
        if (regionName == null || !regionNameToNum.containsKey(regionName)) {
            errorFieldList.add("销售区域");
            res = false;
            whetherNull = true;
        }
        if (pid == null) {
            errorFieldList.add("省");
            res = false;
            whetherNull = true;
        }
        if (pidOfCity == null) {
            errorFieldList.add("市");
            res = false;
            whetherNull = true;
        }
        if (!whetherNull && !pid.equals(pidOfCity)) {
            errorFieldList.add("省与市不匹配");
            res = false;
        }
        if (!whetherNull && pid.charAt(0) != regionNameToNum.get(regionName)) {
            errorFieldList.add("区域与省不匹配");
            res = false;
        }
        return res;
    }

    private boolean checkAgentLinkField(CrmAgentLink crmAgentLink, List<String> errorFieldList) {
        boolean res = true;
        if (StringUtils.isBlank(crmAgentLink.getLinkName())) {
            errorFieldList.add("主要销售成员");
            res = false;
        }
        if (StringUtils.isBlank(crmAgentLink.getLinkPosition())) {
            errorFieldList.add("职位");
            res = false;
        }
        if (StringUtils.isBlank(crmAgentLink.getLinkGeneral())) {
            errorFieldList.add("性别");
            res = false;
        }
        if (StringUtils.isBlank(crmAgentLink.getLinkMobile())) {
            errorFieldList.add("手机号");
            res = false;
        }
        if (StringUtils.isBlank(crmAgentLink.getLinkBg())) {
            errorFieldList.add("销售成员背景");
            res = false;
        }
        return res;
    }

    private String tranlateSex(String input) {
        if (StringUtils.isBlank(input)) {
            return null;
        }
        if (input.equals("女")) {
            return "FEMALE";
        } else if(input.equals("男")){
            return "MALE";
        }else{
            return "";
        }
    }

    private String getCellStr(Row row, int num) {
        Cell cell = row.getCell(num);
        if (cell == null) {
            return "";
        }
        cell.setCellType(CellType.STRING);
        String res = cell.getStringCellValue();
        if (StringUtils.isNotBlank(res)) {
            return res.trim();
        } else {
            return res;
        }
    }

    /**
     * 商机导入
     */
    @Override
    public ImportResult importOppExcel(MultipartFile file, String userId) {
        ImportResult importResult = new ImportResult();
        Long batchLogId = addExcelImportLog(userId, ImportTypeEnum.OPP.getValue());
        try (InputStream inputStream = file.getInputStream();
             Workbook wb = WorkbookFactory.create(inputStream)) {
            Sheet sheet = wb.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            List<ErrorMsg> errorMsgList = new ArrayList<>();
            Integer successCount = 0;
            Integer faildCount = 0;
            List<VisitRecord> visitRecordList = new ArrayList<>();
            String userName = getUserNameByUserId(userId);
            for (int i = 1; i < rows; i++) {
                boolean canInsert = true;
                List<String> errorFieldList = new ArrayList<>();
                List<String> notExistList = new ArrayList<>();
                Row row = sheet.getRow(i);
                Opportunity opportunity = new Opportunity();
                OpporttunityExcelLoadModel loadModel = loadAndCheckOppByExcelRow(row, errorFieldList, i);
                if (loadModel != null) {
                    //校验用户是否一致
                    if (!userName.equals(loadModel.getEmployeeName())) {
                        canInsert = false;
                        addErrorMsg(ErrorMsgEnum.USERERROR, ErrorMsgEnum.USERERROR.getName(), i, loadModel.getOppName(), errorMsgList);
                    }
                    //校验商机是否重名
                    Opportunity opportunityCheck = opportunityService.getOppByName(loadModel.getOppName());
                    if (opportunityCheck != null) {
                        canInsert = false;
                        addErrorMsg(ErrorMsgEnum.SAMEOPP, ErrorMsgEnum.SAMEOPP.getName(), i, loadModel.getOppName(), errorMsgList);
                    }
                    //校验内容是否存在(院校代理商)
                    VisitRecord visitRecord = new VisitRecord();
                    boolean checkExist = checkExist(loadModel, notExistList, opportunity, visitRecord);
                    if (!checkExist) {
                        addErrorMsg(ErrorMsgEnum.NOTEXIST, StringUtils.join(notExistList
                                , ","), i, loadModel.getOppName(), errorMsgList);
                        canInsert = false;
                    }
                    //字段校验
                    loadOppByExcelModel(loadModel, opportunity);
                    checkOppField(loadModel, errorFieldList, opportunity);
                    if (errorFieldList.size() > 0) {
                        addErrorMsg(ErrorMsgEnum.FIELDERROR, StringUtils.join(errorFieldList, ","), i, loadModel.getOppName(), errorMsgList);
                        canInsert = false;
                    }
                    if (canInsert) {
                        opportunity.setUserId(userId);
                        opportunity.setIsExcelImport(1);
                        opportunityService.addSale(opportunity);
                        Opportunity opportunity1 = opportunityService.getOppByName(opportunity.getOpportunityName());
                        visitRecord.setOpportunity(opportunity1);
                        visitRecord.setOpportunityId(opportunity1.getOpportunityId());
                        visitRecordList.add(visitRecord);
                        addExceImportDetail(batchLogId, userId, "SUCCESS", loadModel.getOppName(), 1);
                        successCount++;
                    } else {
                        //取最新的一个错误展示
                        addExceImportDetail(batchLogId, userId, errorMsgList.get(errorMsgList.size() - 1).getErrorCode(), loadModel.getOppName(), 0);
                        faildCount++;
                    }
                }
            }
            insertJournal(userId, userName, visitRecordList);
            importResult.setFailCount(faildCount);
            importResult.setSuccessCount(successCount);
            importResult.setErrorMsgsList(errorMsgList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return importResult;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        if (str.indexOf(".") > 0) {//判断是否有小数点
            if (str.indexOf(".") == str.lastIndexOf(".") && str.split("\\.").length == 2) { //判断是否只有一个小数点
                return pattern.matcher(str.replace(".", "")).matches();
            } else {
                return false;
            }
        } else {
            return pattern.matcher(str).matches();
        }
    }

    /**
     * 不但要装载  还要校验数据有效性
     */
    private OpporttunityExcelLoadModel loadAndCheckOppByExcelRow(Row row, List<String> errorFieldList, Integer index) {
        OpporttunityExcelLoadModel loadModel = new OpporttunityExcelLoadModel();
        if (row.getCell(0) == null) {
            return null;
        }
        try {
            loadModel.setCreateTime(row.getCell(0).getDateCellValue());//创建日期
        } catch (Exception e) {
            errorFieldList.add("创建日期");
            LOGGER.error(e.getMessage(), e);
        }
        loadModel.setEmployeeName(getCellStr(row, 1));//员工姓名
        loadModel.setAgentName(getCellStr(row, 2));//代理商名称
        loadModel.setLinkName(getCellStr(row, 3));//联系人
        loadModel.setSchoolName(getCellStr(row, 4));//学校名称
        loadModel.setSchoolSubpart(getCellStr(row, 5));//二级学院
        loadModel.setDecisionMaker(getCellStr(row, 6));//决策人
        loadModel.setCharger(getCellStr(row, 7));//负责人
        loadModel.setOppName(getCellStr(row, 8));//商机名称
        loadModel.setOppDetail(getCellStr(row, 9));//详细产品
        loadModel.setContainXuebei(getCellStr(row, 10));//是否包含学呗
        String money = getCellStr(row, 11);
        if (isNumeric(money)) {
            loadModel.setMoney(money);//金额
        } else {
            errorFieldList.add("金额");
        }
        loadModel.setMoneyForm(getCellStr(row, 12));//资金来源
        try {
            loadModel.setPreDate(row.getCell(13).getDateCellValue());//预估挂标日期
        } catch (Exception e) {
            errorFieldList.add("预估挂标日期");
            LOGGER.error(e.getMessage(), e);
        }
        loadModel.setSaleStage(getCellStr(row, 14)); //销售阶段
        loadModel.setLogDetail(getCellStr(row, 15)); //跟进记录
        return loadModel;
    }

    private String translateSaleStage(String input) {
        if (StringUtils.isNotBlank(input)) {
            if (input.indexOf("A") > -1) {
                return "A";
            } else if (input.indexOf("B1") > -1) {
                return "B1";
            } else if (input.indexOf("B2") > -1) {
                return "B2";
            } else if (input.indexOf("B3") > -1) {
                return "B3";
            } else if (input.indexOf("B4") > -1) {
                return "B4";
            } else if (input.indexOf("C") > -1) {
                return "C";
            } else if (input.indexOf("D") > -1) {
                return "D";
            } else if (input.indexOf("F") > -1) {
                return "F";
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    private boolean checkOppField(OpporttunityExcelLoadModel loadModel, List<String> errorFieldList, Opportunity opportunity) {
        boolean res = true;
        if (opportunity.getContainXuebei() == 0) {
            errorFieldList.add("是否包含学呗");
            res = false;
        }
        if (StringUtils.isBlank(opportunity.getSalesStatus())) {
            errorFieldList.add("预估挂标日期");
            res = false;
        }
        if (StringUtils.isBlank(opportunity.getMoneyFrom())) {
            errorFieldList.add("资金来源");
            res = false;
        }
        if (loadModel.getCreateTime() == null) {
            errorFieldList.add("创建日期");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getEmployeeName())) {
            errorFieldList.add("员工姓名");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getAgentName())) {
            errorFieldList.add("代理商名称");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getLinkName())) {
            errorFieldList.add("联系人");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getSchoolName())) {
            errorFieldList.add("学校名称");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getSchoolSubpart())) {
            errorFieldList.add("二级学院");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getDecisionMaker())) {
            errorFieldList.add("决策人");
            res = false;
        }

        if (StringUtils.isBlank(loadModel.getCharger())) {
            errorFieldList.add("负责人");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getOppName())) {
            errorFieldList.add("商机名称");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getOppDetail())) {
            errorFieldList.add("详细产品");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getContainXuebei())) {
            errorFieldList.add("是否包含学呗");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getMoney())) {
            errorFieldList.add("金额");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getMoneyForm())) {
            errorFieldList.add("资金来源");
            res = false;
        }
        if (loadModel.getPreDate() == null) {
            errorFieldList.add("预估挂标日期");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getSaleStage())) {
            errorFieldList.add("销售阶段");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getLogDetail())) {
            errorFieldList.add("跟进记录");
            res = false;
        }
        return res;
    }

    private boolean checkExist(OpporttunityExcelLoadModel loadModel, List<String> notExistList, Opportunity opportunity, VisitRecord visitRecord) {
        boolean res = true;
        if (StringUtils.isNotBlank(loadModel.getAgentName())) {
            CrmAgent crmAgent = customerService.getAgentByAgentName(loadModel.getAgentName());
            if (crmAgent != null) {
                CrmAgentLink crmAgentLink = customerService.getAgentByAgentIdAndName(crmAgent.getAgentId(), loadModel.getLinkName());
                if (crmAgentLink == null) {
                    notExistList.add(loadModel.getLinkName() + "(联系人)");
                    res = false;
                } else {
                    opportunity.setAgentId(crmAgent.getAgentId());
                    opportunity.setAgentLinkId(crmAgentLink.getLinkUserId());
                    List<CrmAgentLink> crmAgentLinkList = new ArrayList<>();
                    crmAgentLinkList.add(crmAgentLink);
                    visitRecord.setChosenAgents(crmAgentLinkList);
                }
            } else {
                notExistList.add(loadModel.getAgentName() + "(代理商)");
                res = false;
            }
        }
        if (StringUtils.isNotBlank(loadModel.getSchoolName())) {
            Customer customer = customerService.getCrmCustomerByName(loadModel.getSchoolName());
            if (customer != null) {
                Department department = customerService.getCrmDepartmentByName(customer.getCustomerId(), loadModel.getSchoolSubpart());
                if (department != null) {
                    Contacts decisionContacts = customerService.getContactsByName(department.getDeptId(), loadModel.getDecisionMaker());
                    Contacts chargerContacts = customerService.getContactsByName(department.getDeptId(), loadModel.getCharger());
                    if (chargerContacts == null) {
                        notExistList.add(loadModel.getCharger() + "(负责人)");
                        res = false;
                    }
                    if (decisionContacts == null) {
                        notExistList.add(loadModel.getDecisionMaker() + "(决策人)");
                        res = false;
                    }
                    if (res == true) {
                        opportunity.setChargeId(chargerContacts.getContactsId());
                        opportunity.setDecisionMakerId(decisionContacts.getContactsId());
                        opportunity.setCustomerId(customer.getCustomerId());
                        opportunity.setCustomerName(customer.getCustomerName());
                        List<Contacts> contactsList = new ArrayList<>();
                        contactsList.add(decisionContacts);
                        visitRecord.setChosenContacts(contactsList);
                        visitRecord.setContactType(2);
                        visitRecord.setVisitType("NORMAL_VISIT");
                        visitRecord.setVisitResult(loadModel.getLogDetail());
                    }
                } else {
                    notExistList.add(loadModel.getSchoolSubpart() + "(二级学院)");
                    res = false;
                }
            } else {
                notExistList.add(loadModel.getSchoolName() + "(院校)");
                res = false;
            }
        }
        return res;
    }

    private void insertJournal(String userId, String userName, List<VisitRecord> visitRecordList
    ) {
        Journal journal = getTodayJournal(userId, userName);
        journal.setVisitRecords(visitRecordList);
        try {
            journalService.createJournal(journal);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
    private Journal getTodayJournal(String userId, String userName) {
        Journal journal = new Journal();
        journal.setType(JournalTypeEnum.DAILY);
        journal.setUserId(userId);
        journal.setUserName(userName);
        journal.setOther("");
        journal.setSummary("");
        journal.setPlan("");
        journal.setHasSubmitted(true);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.YEAR, -1);
        journal.setCreateTs(calendar.getTime());
        journal.setModifyTs(calendar.getTime());
            return journal;
    }


    private void insertJournal(String userId, String userName, List<VisitRecord> visitRecordList, Date createTime
    ) {
        Journal journal = getJournal(userId, userName, createTime);
        journal.setVisitRecords(visitRecordList);
        try {
            journalService.createJournal(journal);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private Journal getJournal(String userId, String userName, Date createTime) {
        Journal journal = new Journal();
        journal.setType(JournalTypeEnum.DAILY);
        journal.setUserId(userId);
        journal.setUserName(userName);
        journal.setOther("");
        journal.setSummary("");
        journal.setPlan("");
        journal.setHasSubmitted(true);
        journal.setCreateTs(createTime);
        journal.setModifyTs(createTime);
        return journal;
    }

    /**
     * 导入学校
     */
    @Override
    public ImportResult importSchoolExcel(MultipartFile file, String excelName, String userId) {
        ImportResult importResult = new ImportResult();
        Long batchLogId = addExcelImportLog(userId, ImportTypeEnum.SCHOOL.getValue());
        try (InputStream inputStream = file.getInputStream();
             Workbook wb = WorkbookFactory.create(inputStream)) {
            Sheet sheet = wb.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            List<ErrorMsg> errorMsgList = new ArrayList<>();
            Integer successCount = 0;
            Integer faildCount = 0;
            String userName = getUserNameByUserId(userId);
            for (int i = 1; i < rows; i++) {
                boolean canInsert = true;
                List<String> errorFieldList = new ArrayList<>();
                Row row = sheet.getRow(i);
                CustomerExcelLoadModel loadModel = loadCustomerByExcelRow(row, errorFieldList, i);
                if (loadModel == null) {
                    continue;
                }
                Customer customerTmp = new Customer();
                loadCustomerByExcelModel(customerTmp, loadModel);
                Department departmentTmp = new Department();
                loadCustomerDepartByExcelModel(departmentTmp, loadModel);
                Contacts contactsTmp = new Contacts();
                loadContactsByExcelModel(contactsTmp, loadModel);
                checkCustomerField(loadModel, errorFieldList, customerTmp, contactsTmp);
                if (errorFieldList.size() > 0) {
                    addErrorMsg(ErrorMsgEnum.FIELDERROR, StringUtils.join(errorFieldList, ","), i, loadModel.getSchoolName(), errorMsgList);
                    canInsert = false;
                }
                if (!userName.equals(loadModel.getEmployeeName())) {
                    addErrorMsg(ErrorMsgEnum.USERERROR, ErrorMsgEnum.USERERROR.getName(), i, loadModel.getSchoolName(), errorMsgList);
                    canInsert = false;
                }
                boolean excuteRes = true;
                if (canInsert) {
                    //先检测院校是否存在
                    Customer customer = customerService.getCrmCustomerByName(loadModel.getSchoolName());
                    if (customer == null) {//如果存在, 直接使用,如果不存在, 则需要插入后返回结果
                        CustomerViewModel customerViewModel = getCustomer(customerTmp, userId);
                        customerService.newSchool(customerViewModel);
                        customer = customerTmp;
                        customer.setCustomerId(customerViewModel.getCustomerId());
                    }
                    Department department = customerService.getCrmDepartmentByName(customer.getCustomerId(), loadModel.getSubSchoolName());
                    if (department == null) {//如果存在, 直接使用,如果不存在, 则需要插入后返回结果
                        departmentTmp.setDeptId(UUIDGenerator.genUUID());
                        departmentTmp.setCustomer(customer);
                        departmentTmp.setCustomerId(customer.getCustomerId());
                        departmentTmp.setCreateId(userId);
                        departmentTmp.setCreateTs(loadModel.getCreateTime());
                        departmentTmp.setCreateName(userName);
                        departmentTmp.setIsExcelImport(1);
                        departmentTmp.setEnclosureStatus(EnclosureStatusEnum.NORMAL);
                        EnclosureApply enclosureApply = new EnclosureApply();
                        String reasons = "机构编辑申请";
                        enclosureApply.setDeptId(departmentTmp.getDeptId());
                        enclosureApply.setUserId(userId);
                        enclosureApply.setReasons(reasons);
                        customerMapper.insertDepartment(departmentTmp);
                        if (loadModel.getGetOwner().equals("是")) {
                            customerMapper.insertEnclosureApply(enclosureApply);
                        } else {
                            customerMapper.insertEnclosureApplyOver(enclosureApply);
                        }
                        department = departmentTmp;
                    }
                    Contacts contacts = customerService.getContactsByName(department.getDeptId(), loadModel.getSubSchoolLinker());
                    if (contacts != null) {//如果已存在, 返回一个已存在的错误
                        String logName = loadModel.getSchoolName() + "-" + loadModel.getSubSchoolName() + "-" + loadModel.getSubSchoolLinker();
                        addErrorMsg(ErrorMsgEnum.SAMECONTACT, ErrorMsgEnum.SAMECONTACT.getName(), i, logName, errorMsgList);
                        excuteRes = false;
                    } else { //添加联系人
                        ContactsType contactsType = customerMapper.queryContactsTypeByName(loadModel.getPosition());
                        contactsTmp.setContactsId(UUIDGenerator.genUUID());
                        contactsTmp.setDepartment(department);
                        contactsTmp.setContactsType(contactsType);
                        contactsTmp.setIsExcelImport(1);
                        contacts.setCreateTs(loadModel.getCreateTime());
                        contacts.setCreateId(userId);
                        contacts.setCreateName(userName);
                        customerMapper.insertContacts(contactsTmp);
                    }
                } else {
                    excuteRes = false;
                }
                String logName = loadModel.getSchoolName() + "-" + loadModel.getSubSchoolName() + "-" + loadModel.getSubSchoolLinker();
                if (excuteRes) {
                    addExceImportDetail(batchLogId, userId, "SUCCESS", logName, 1);
                    successCount++;
                } else {
                    addExceImportDetail(batchLogId, userId, errorMsgList.get(errorMsgList.size() - 1).getErrorCode(), logName, 0);
                    faildCount++;
                }
            }
            importResult.setFailCount(faildCount);
            importResult.setSuccessCount(successCount);
            importResult.setErrorMsgsList(errorMsgList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return importResult;
    }

    /**
     * 装载数据
     */
    private CustomerExcelLoadModel loadCustomerByExcelRow(Row row, List<String> errorFieldList, Integer index) {
        CustomerExcelLoadModel loadModel = new CustomerExcelLoadModel();
        if (row.getCell(0) == null) {
            return null;
        }
        try {
            loadModel.setCreateTime(row.getCell(0).getDateCellValue());//创建日期
        } catch (Exception e) {
            errorFieldList.add("创建日期");
            LOGGER.error(e.getMessage(), e);
        }
        loadModel.setEmployeeName(getCellStr(row, 1));//员工姓名
        loadModel.setRegion(getCellStr(row, 2));//销售区域
        loadModel.setProvince(getCellStr(row, 3));//省
        loadModel.setCity(getCellStr(row, 4));//市
        loadModel.setSchoolType(getCellStr(row, 5));//院校类型
        loadModel.setSchoolName(getCellStr(row, 6));//院校全称
        loadModel.setSchoolFrom(getCellStr(row, 7));//客户来源
        loadModel.setSubSchoolName(getCellStr(row, 8));//二级学院
        loadModel.setGetOwner(getCellStr(row, 9));//是否圈地;
        loadModel.setSubSchoolLinker(getCellStr(row, 10));//联系人
        loadModel.setSex(getCellStr(row, 11));//性别
        loadModel.setPosition(getCellStr(row, 12));//职位
        loadModel.setMobile(getCellStr(row, 13));//手机号
        return loadModel;
    }

    private boolean checkCustomerField(CustomerExcelLoadModel loadModel, List<String> errorFieldList, Customer customer, Contacts contacts) {
        boolean res = true;
        if (loadModel.getCreateTime() == null) {
            errorFieldList.add("创建日期");
            res = false;
        }
        if (customer.getCustomerType() == null) {
            errorFieldList.add("院校类型");
            res = false;
        }
        if (customer.getCustomerFrom() == null) {
            errorFieldList.add("院校来源");
            res = false;
        }
        if (contacts.getGender() == null) {
            errorFieldList.add("性别");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getEmployeeName())) {
            errorFieldList.add("员工姓名");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getSchoolType())) {
            errorFieldList.add("院校类型");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getSchoolName())) {
            errorFieldList.add("院校全称");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getSchoolFrom())) {
            errorFieldList.add("客户来源");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getSubSchoolName())) {
            errorFieldList.add("二级学院");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getSubSchoolLinker())) {
            errorFieldList.add("联系人");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getSex())) {
            errorFieldList.add("性别");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getPosition())) {
            errorFieldList.add("职位");
            res = false;
        }else{
            if(regContactType.indexOf(loadModel.getPosition())<0){
                errorFieldList.add("职位");
                res = false;
            }
        }
        if (StringUtils.isBlank(loadModel.getMobile())) {
            errorFieldList.add("手机号");
            res = false;
        }
        boolean regionRes = checkRegion(loadModel.getRegion(), loadModel.getProvince(), loadModel.getCity(), errorFieldList);
        if (regionRes == false) {
            res = false;
        }
        return res;
    }

    private void loadCustomerByExcelModel(Customer customer, CustomerExcelLoadModel loadModel) {
        customer.setCustomerName(loadModel.getSchoolName());
        customer.setCustomerType(CustomerTypeEnum.getCustomerTypeEnum(loadModel.getSchoolType()));
        customer.setCustomerFrom(CustomerFromEnum.getCustomerFromEnum(loadModel.getSchoolFrom()));
        customer.setCreateTs(CrmDateUtils.format(loadModel.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        customer.setRegion(loadModel.getRegion());
        customer.setProvince(loadModel.getProvince());
        customer.setCity(loadModel.getCity());
    }

    private void loadCustomerDepartByExcelModel(Department department, CustomerExcelLoadModel loadModel) {
        department.setDeptName(loadModel.getSubSchoolName());
        department.setCreateTs(loadModel.getCreateTime());
    }

    private void loadContactsByExcelModel(Contacts contacts, CustomerExcelLoadModel loadModel) {
        contacts.setRealName(loadModel.getSubSchoolLinker());
        contacts.setTel(loadModel.getMobile());
        contacts.setGender(translateGenderEnum(loadModel.getSex()));
        contacts.setContactsType(getContactsTypeByName(loadModel.getPosition()));
    }

    private ContactsType getContactsTypeByName(String name) {
        return customerMapper.queryContactsTypeByName(name);
    }

    private GenderEnum translateGenderEnum(String input) {
        switch (input) {
            case "男":
                return GenderEnum.MALE;
            case "女":
                return GenderEnum.FEMALE;
            default:
                return null;
        }
    }

    private CustomerViewModel getCustomer(Customer customer, String userId) {
        CustomerViewModel customerViewModel = new CustomerViewModel();
        customerViewModel.setCustomerId(UUIDGenerator.genUUID());
        customerViewModel.setCreatorId(userId);
        customerViewModel.setCreateTs(customer.getCreateTs());
        customerViewModel.setUpdaterId(customerViewModel.getCreatorId());
        customerViewModel.setSchoolType(customer.getCustomerType().toString());
        customerViewModel.setCustomerFrom(customer.getCustomerFrom().toString());
        customerViewModel.setName(customer.getCustomerName());
        customerViewModel.setCreatorId(userId);
        customerViewModel.setIsExcelImport(1);
        customerViewModel.setRegion(customer.getRegion());
        customerViewModel.setProvince(customer.getProvince());
        customerViewModel.setCity(customer.getCity());
        return customerViewModel;
    }


    /**
     * 导入日志
     */
    @Override
    public ImportResult importJournalExcel(MultipartFile file, String excelName, String userId) {
        ImportResult importResult = new ImportResult();
        Long batchLogId = addExcelImportLog(userId, ImportTypeEnum.JOURNAL.getValue());
        try (InputStream inputStream = file.getInputStream();
             Workbook wb = WorkbookFactory.create(inputStream)) {
            Sheet sheet = wb.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            List<ErrorMsg> errorMsgList = new ArrayList<>();
            Integer successCount = 0;
            Integer faildCount = 0;
            String userName = getUserNameByUserId(userId);
            for (int i = 1; i < rows; i++) {
                boolean canInsert = true;
                List<String> errorFieldList = new ArrayList<>();
                List<String> notExistList = new ArrayList<>();
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                JournalExcelLoadModel loadModel = loadJournalByExcelRow(row, errorFieldList, i);
                if (loadModel == null) {
                    continue;
                }
                String errorStr = loadModel.getAgentName() + "-" + loadModel.getAgentLinker();
                //校验用户是否一致
                if (!userName.equals(loadModel.getEmployee())) {
                    canInsert = false;
                    addErrorMsg(ErrorMsgEnum.USERERROR, ErrorMsgEnum.USERERROR.getName(), i, errorStr, errorMsgList);
                }
                //检查代理商以及代理商联系人是否存在
                VisitRecord visitRecord = new VisitRecord();
                boolean checkExist = checkAgentExist(loadModel, notExistList, visitRecord);
                if (!checkExist) {
                    addErrorMsg(ErrorMsgEnum.NOTEXIST, StringUtils.join(notExistList
                            , ","), i, errorStr, errorMsgList);
                    canInsert = false;
                }
                //字段检查
                boolean checkField = checkJournalField(loadModel, errorFieldList, visitRecord);
                if (!checkField) {
                    addErrorMsg(ErrorMsgEnum.FIELDERROR, StringUtils.join(errorFieldList, ","), i, errorStr, errorMsgList);
                    canInsert = false;
                }
                if (canInsert) {
                    //插入日志
                    List<VisitRecord> visitRecordList = new ArrayList<>();
                    visitRecordList.add(visitRecord);
                    insertJournal(userId, userName, visitRecordList,loadModel.getCreateTime());
                    addExceImportDetail(batchLogId, userId, "SUCCESS", buildLogStr(loadModel), 1);
                    successCount++;
                } else {
                    addExceImportDetail(batchLogId, userId, errorMsgList.get(errorMsgList.size() - 1).getErrorCode(), errorStr, 0);
                    faildCount++;
                }
            }
            importResult.setFailCount(faildCount);
            importResult.setSuccessCount(successCount);
            importResult.setErrorMsgsList(errorMsgList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return importResult;
    }

    private String buildLogStr(JournalExcelLoadModel loadModel) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(loadModel.getAgentName());
        stringBuilder.append("-");
        stringBuilder.append(loadModel.getAgentLinker());
        stringBuilder.append(",");
        stringBuilder.append("拜访事由: ");
        stringBuilder.append(loadModel.getVisitReason());
        stringBuilder.append(",");
        stringBuilder.append("拜访过程及结果: ");
        stringBuilder.append(loadModel.getVisitResult());
        return stringBuilder.toString();
    }

    private boolean checkAgentExist(JournalExcelLoadModel loadModel, List<String> notExistList, VisitRecord visitRecord) {
        boolean res = true;
        if (StringUtils.isNotBlank(loadModel.getAgentName())) {
            CrmAgent crmAgent = customerService.getAgentByAgentName(loadModel.getAgentName());
            if (crmAgent != null) {
                CrmAgentLink crmAgentLink = customerService.getAgentByAgentIdAndName(crmAgent.getAgentId(), loadModel.getAgentLinker());
                if (crmAgentLink == null) {
                    notExistList.add(loadModel.getAgentName() + "(联系人)");
                    res = false;
                } else {
                    List<CrmAgentLink> crmAgentLinkList = new ArrayList<>();
                    crmAgentLinkList.add(crmAgentLink);
                    visitRecord.setChosenAgents(crmAgentLinkList);
                    visitRecord.setOutType(translateOutType(loadModel.getOutType()));
                    visitRecord.setContactType(translateContactType(loadModel.getContactType()));
                    visitRecord.setVisitResult(loadModel.getVisitResult());
                    visitRecord.setOutReason(loadModel.getVisitReason());
                    visitRecord.setVisitType(getVisitType(VisitTypeEnum.getVisitTypeEnumEnum(loadModel.getVisitType())));
                    visitRecord.setIsExcelImport(1);
                }
            } else {
                notExistList.add(loadModel.getAgentName() + "(代理商)");
                res = false;
            }
        }
        return res;
    }

    private String getVisitType(VisitTypeEnum visitTypeEnum) {
        if (visitTypeEnum == null) {
            return null;
        } else {
            return visitTypeEnum.toString();
        }
    }

    /**
     * 装载数据
     */
    private JournalExcelLoadModel loadJournalByExcelRow(Row row, List<String> errorFieldList, Integer index) {
        JournalExcelLoadModel loadModel = new JournalExcelLoadModel();
        if (row.getCell(0) == null) {
            return null;
        }
        try {
            loadModel.setCreateTime(row.getCell(0).getDateCellValue());//创建日期
        } catch (Exception e) {
            errorFieldList.add("创建日期");
            LOGGER.error(e.getMessage(), e);
        }
        loadModel.setEmployee(getCellStr(row, 1));//员工姓名
        loadModel.setOutType(getCellStr(row, 2));//外出类型
        loadModel.setVisitType(getCellStr(row, 3));//拜访类型
        loadModel.setContactType(getCellStr(row, 4));//沟通时长
        loadModel.setAgentName(getCellStr(row, 5));//代理商全称
        loadModel.setAgentLinker(getCellStr(row, 6));//代理商联系人
        loadModel.setVisitReason(getCellStr(row, 7));//拜访事由
        loadModel.setVisitResult(getCellStr(row, 8));//拜访过程以及结果
        return loadModel;
    }

    private Integer translateOutType(String outType) {
        if ("市内".equals(outType)) {
            return 1;
        } else if ("市外".equals(outType)) {
            return 2;
        } else {
            return null;
        }
    }

    private Integer translateContactType(String input) {
        if ("大于10分钟".equals(input)) {
            return 2;
        } else if ("小于等于10分钟".equals(input)) {
            return 2;
        } else {
            return 0;
        }
    }

    private boolean checkJournalField(JournalExcelLoadModel loadModel, List<String> errorFieldList, VisitRecord visitRecord) {
        boolean res = true;
        boolean needCheckOutType = needCheckOutType(loadModel.getVisitType());
        if (visitRecord.getContactType() == null) {
            errorFieldList.add("沟通时长");
            res = false;
        }
        if (loadModel.getCreateTime() == null) {
            errorFieldList.add("拜访/接待日期");
        }
        if (StringUtils.isBlank(loadModel.getEmployee())) {
            errorFieldList.add("员工姓名");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getVisitType())) {
            errorFieldList.add("拜访类型");
            res = false;
        } else if (StringUtils.isBlank(visitRecord.getVisitType())) {
            errorFieldList.add("拜访类型");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getContactType())) {
            errorFieldList.add("沟通时长");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getAgentName())) {
            errorFieldList.add("代理商全称");
            res = false;
        }
        if (StringUtils.isBlank(loadModel.getAgentLinker())) {
            errorFieldList.add("代理商联系人");
            res = false;
        }
        if (needCheckOutType) {
            if (StringUtils.isBlank(loadModel.getOutType())) {
                errorFieldList.add("外出类型");
                res = false;
            }
            if (StringUtils.isBlank(loadModel.getVisitReason())) {
                errorFieldList.add("拜访事由");
                res = false;
            }
        }
        if (StringUtils.isBlank(loadModel.getVisitResult())) {
            errorFieldList.add("拜访过程以及结果");
            res = false;
        }
        return res;
    }

    //检查是否需要
    private boolean needCheckOutType(String input) {
        if ("电话联系".equals(input) || "其他".equals(input)) {
            return false;
        }
        return true;
    }

}
