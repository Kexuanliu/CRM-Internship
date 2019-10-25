/**
 * @author ZXL
 * @date 2019/7/17 10:38
 */
package com.xuebei.crm.statistics;

import com.xuebei.crm.customer.Contacts;
import com.xuebei.crm.customer.Customer;
import com.xuebei.crm.customer.CustomerMapper;
import com.xuebei.crm.customer.Department;
import com.xuebei.crm.customer.agent.AgentLinkViewModel;
import com.xuebei.crm.customer.agent.CrmAgent;
import com.xuebei.crm.customer.agent.CrmAgentLink;
import com.xuebei.crm.department.DeptMapper;
import com.xuebei.crm.journal.Journal;
import com.xuebei.crm.journal.JournalService;
import com.xuebei.crm.journal.VisitRecord;
import com.xuebei.crm.journal.VisitTypeEnum;
import com.xuebei.crm.opportunity.Opportunity;
import com.xuebei.crm.opportunity.OpportunityMapper;
import com.xuebei.crm.utils.CrmDoubleFormateUtils;
import com.xuebei.crm.user.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.text.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;


    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private OpportunityMapper opportunityMapper;

    @Autowired
    JournalService journalService;

    /**
     * 获取总体统计数据
     */
    @Override
    public StatisticsTotalViewModel getStatisticsTotalViewModel(StatisticsSearchParam param) {
        StatisticsTotalViewModel viewModel = new StatisticsTotalViewModel();
        int aLevelCount = getALevelContacts(param);
        int allLevelCount = getAllContacats(param);
        viewModel.setaLevelCustomerCount(aLevelCount);
        viewModel.setaLevelCustomerRate(CrmDoubleFormateUtils.doubleFormat(Double.valueOf(aLevelCount) / Double.valueOf(allLevelCount), "0.00"));
        viewModel.setNewAgentCount(getAgentCount(param));
        viewModel.setCoreAgentCount(getCoreAgent(param));

        JournalStatisticsModel journalStatisticsModel = getJournalStatistic(param);
        viewModel.setVisitCustomerTimesCount(journalStatisticsModel.getVisitCustomerTimesCount());
        viewModel.setVisitCustomerNumCount(journalStatisticsModel.getVisitCustomerNumCount());
        viewModel.setAccompanyVisitTimesCount(journalStatisticsModel.getAccompanyVisitTimesCount());
        viewModel.setAccompanyVisitNumCount(journalStatisticsModel.getAccompanyVisitNumCount());
        viewModel.setDeepVisitTimesCount(journalStatisticsModel.getDeepVisitTimesCount());
        viewModel.setNormalVisitTimesCount(journalStatisticsModel.getNormalVisitTimesCount());
        viewModel.setCustomerVisitNumCount(journalStatisticsModel.getCustomerVisitNumCount());
        viewModel.setDeptVisitNumCount(journalStatisticsModel.getDeptVisitNumCount());
        viewModel.setVisitPresidentTimesCount(journalStatisticsModel.getVisitPresidentTimesCount());
        viewModel.setVisitDeanTimesCount(journalStatisticsModel.getVisitDeanTimesCount());

        List<KeyValue> kvList = getOppoStat(param);
        int bLevelOppSum = 0;
        int alloppSum = 0;
        for (KeyValue item : kvList) {
            alloppSum = alloppSum + item.getValue();
            if (item.getKey().equals("A")) {
                viewModel.setAlevelOppCount(item.getValue());
            } else if (item.getKey().equals("B1")) {
                bLevelOppSum = bLevelOppSum + item.getValue();
            } else if (item.getKey().equals("B2")) {
                bLevelOppSum = bLevelOppSum + item.getValue();
            } else if (item.getKey().equals("B3")) {
                bLevelOppSum = bLevelOppSum + item.getValue();
            } else if (item.getKey().equals("B4")) {
                bLevelOppSum = bLevelOppSum + item.getValue();
            } else if (item.getKey().equals("C")) {
                viewModel.setClevelOppCount(item.getValue());
            } else if (item.getKey().equals("D")) {
                viewModel.setDlevelOppCount(item.getValue());
            }
        }
        viewModel.setNewOppCount(alloppSum);
        viewModel.setBlevelOppCount(bLevelOppSum);
        viewModel.setClevelOppCount(bLevelOppSum);
        viewModel.setDlevelOppMoneySum(getDMoney(param));
        return viewModel;
    }

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DeptMapper deptMapper;

    /**
     * A级客户
     */
    @Override
    public int getALevelContacts(StatisticsSearchParam param) {
        return statisticsMapper.getContactsCount(param);
    }

    /**
     * 所有客户
     */
    @Override
    public int getAllContacats(StatisticsSearchParam param) {
        return statisticsMapper.getAllContacatsCount(param);
    }

    @Override
    public List<ContactStatisticsViewModel> getContactsDetail(StatisticsDetailsParam param) {
        List<ContactStatisticsViewModel> res = statisticsMapper.getContactStatisticsViewModelList(param);
        return res;
    }

    @Override
    public List<VisitStatisticsViewModel> getVisitDetails(StatisticsSearchParam param) {
        //todo: 获取拜访数据
        return null;
    }


    @Override
    public List<AgentStatisticsViewModel> getAgentDetails(StatisticsSearchParam param) {
        List<AgentStatisticsViewModel> res = new ArrayList<>();
        List<CrmAgent> crmAgentList = customerMapper.getCrmAgentByParam(param);
        Set<String> ids = new HashSet<>();
        for(CrmAgent item: crmAgentList){
            AgentStatisticsViewModel tmp = new AgentStatisticsViewModel();
            tmp.setCreateTime(item.getCreateTs());
            tmp.setEmployee(item.getCreateName());
            tmp.setCustomerFrom(item.getCustomerFrom().getName());
            tmp.setAgentName(item.getWebsite());
            tmp.setCustomerLevel(item.getCustomerLevel().getName());
            ids.add(item.getAgentId());
            tmp.setBackground(item.getProfile());
            tmp.setAgentId(item.getAgentId());
            res.add(tmp);
        }
        //mainSalesMember
        List<CrmAgentLink> crmAgentLinkList = customerMapper.getNameAndPosition(ids);
        Map<String, String> agentIdToNameAndPosition = new HashMap<>();
        for (CrmAgentLink cal: crmAgentLinkList) {
            String agentId = cal.getAgentId();
            String name = cal.getLinkName();
            String position = cal.getLinkPosition();
            String combine = name + "-" + position;
            agentIdToNameAndPosition.put(agentId, combine);  // <agentId, name-position>
        }
        for (AgentStatisticsViewModel asvm: res) {
            String agentId = asvm.getAgentId();
            String nameAndPosition = agentIdToNameAndPosition.get(agentId);
            asvm.setMainSaleMember((nameAndPosition));
        }
        return res;
    }

    @Override
    public List<OpportunityViewModel> getOppoDetails(StatisticsDetailsParam param) {
        List<Opportunity> oppoList = opportunityMapper.getOppoByParam(param);
        List<OpportunityViewModel> res = new ArrayList<>();
        Set<String> userIds2 = new HashSet<>(); // 用ID找真正姓名
        Set<String> collegeIds = new HashSet<>(); // 找院校
        Set<String> decisionMakerIds = new HashSet<>(); // 找二级院校
        Set<String> agentIds = new HashSet<>(); // 找联系人, id
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  // 日期转换成STRING
        for (Opportunity oppo: oppoList) {
            OpportunityViewModel tmp = new OpportunityViewModel();
            tmp.setCreateTime(formatter.format(oppo.getCreate_ts()));
            if (oppo.getUserId() != null && !oppo.getUserId().isEmpty()) {
                userIds2.add(oppo.getUserId());
            }
            tmp.setUserId(oppo.getUserId());
            tmp.setAgent(oppo.getAgent());
            tmp.setAgentId(oppo.getAgentId());
            if (oppo.getCustomerId() != null && !oppo.getCustomerId().isEmpty()) {
                collegeIds.add(oppo.getCustomerId());
            }
            tmp.setCollegeId(oppo.getCustomerId());
            if (oppo.getDecisionMakerId() != null && !oppo.getDecisionMakerId().isEmpty()) {
                decisionMakerIds.add(oppo.getDecisionMakerId());
            }
            tmp.setProject(oppo.getOpportunityName());
            tmp.setProjectId(oppo.getOpportunityId().toString());
            tmp.setMoney(oppo.getAmount()); // double
            if (oppo.getCheckDate() != null) {
                tmp.setInviteTime(formatter.format(oppo.getCheckDate()));
            }
            tmp.setProjectLevel(oppo.getSalesStatus());
            if (oppo.getAgentId() != null && !oppo.getAgentId().isEmpty()) {
                agentIds.add(oppo.getAgentId());
            }
            tmp.setDecisionMakerId(oppo.getDecisionMakerId());
            res.add(tmp);
        }

        // employee
        setEmployees(userIds2, res);

        // college
        setCollege(collegeIds, res);

        // secondCollege and decisionMaker
        Set<String> deptIds = new HashSet<>();
        setSecondCollegeAndDecisionMaker(decisionMakerIds, res, deptIds);

        // whetherEnclosure
        setEnclosure(deptIds, res);

        // contact, contactId
        setContact(agentIds, res);

        return res;
    }

    /**
     * 设置"员工"
     * @param userIds 员工ID
     * @param res
     */
    private void setEmployees(Set<String> userIds, List<OpportunityViewModel> res) {
        List<User> userNames = new ArrayList<>();
        if (!userIds.isEmpty()) {
            userNames = userMapper.getRealNameById(userIds);
        }
        Map<String, String> idToName = new HashMap<>();
        for (User user: userNames) {
            String userId = user.getUserId();
            String userName = user.getRealName();
            idToName.put(userId, userName);
        }
        for (OpportunityViewModel ovm: res) {
            String realName = idToName.get(ovm.getUserId());
            ovm.setEmployee(realName);
        }
    }

    /**
     * 设置"院校"
     * @param collegeIds 院校ID
     * @param res
     */
    private void setCollege(Set<String> collegeIds, List<OpportunityViewModel> res) {
        List<Customer> customers = new ArrayList<>();
        if (!collegeIds.isEmpty()) {
            customers = customerMapper.getCollegeNameById(collegeIds);
        }
        Map<String, String> customerIdToName = new HashMap<>();
        for (Customer customer: customers) {
            String customerId = customer.getCustomerId();
            String customerName = customer.getCustomerName();
            customerIdToName.put(customerId, customerName);
        }
        for (OpportunityViewModel ovm: res) {
            String customerId = ovm.getCollegeId();
            String customerName = customerIdToName.get(customerId);
            ovm.setCollege(customerName);
        }
    }

    /**
     * 设置"二级学院"和"决策人"
     * @param decisionMakerIds 决策人ID
     * @param res
     * @param deptIds 二级学院ID
     */
    private void setSecondCollegeAndDecisionMaker(Set<String> decisionMakerIds, List<OpportunityViewModel> res, Set<String> deptIds) {
        List<Department> departmentList = new ArrayList<>();
        if (!decisionMakerIds.isEmpty()) {
            departmentList = deptMapper.getDeptNameById(decisionMakerIds);
        }
        Map<String, String> decisionMakerIdToName = new HashMap<>();
        Map<String, String> decisionMakerIdToDeptId = new HashMap<>();
        Map<String, String> decisionMakerIdToRealName = new HashMap<>();
        for (Department dep: departmentList) {
            String deptId = dep.getDeptId();
            String deptName = dep.getDeptName();
            String decisionMakerId = dep.getDecisionMakerId();
            String decisionMaker = dep.getDecisionMaker();
            decisionMakerIdToName.put(decisionMakerId, deptName);
            decisionMakerIdToDeptId.put(decisionMakerId, deptId);
            decisionMakerIdToRealName.put(decisionMakerId, decisionMaker);
            deptIds.add(deptId);
        }
        for (OpportunityViewModel ovm: res) {
            String decisionMakerId = ovm.getDecisionMakerId();
            String deptName = decisionMakerIdToName.get(decisionMakerId);
            ovm.setSecondCollege(deptName);
            ovm.setSecondCollegeId(decisionMakerIdToDeptId.get(decisionMakerId));
            ovm.setDecisionMaker(decisionMakerIdToRealName.get(decisionMakerId));
        }
    }

    /**
     * 设置"是否圈地"
     * @param deptIds 二级学院ID
     * @param res
     */
    private void setEnclosure(Set<String> deptIds, List<OpportunityViewModel> res) {
        List<Enclosure> enclosureList = new ArrayList<>();
        if (!deptIds.isEmpty()) {
            enclosureList = statisticsMapper.getEnclosureStatus(deptIds);
        }
        Map<String, String> deptIdToStatus = new HashMap<>();
        for (Enclosure en: enclosureList) {
            String deptId = en.getDeptId();
            String enclosure = en.getWhetherEnclosure();
            if ("PERMITTED".equals(enclosure)) {
                enclosure = "是";
            } else {
                enclosure = "否";
            }
            deptIdToStatus.put(deptId, enclosure);
        }
        for (OpportunityViewModel ovm: res) {
            String deptId = ovm.getSecondCollegeId();
            ovm.setWhetherEnclosure(deptIdToStatus.get(deptId));
        }
    }

    /**
     * 设置"联系人"
     * @param agentIds 代理商ID
     * @param res
     */
    private void setContact(Set<String> agentIds, List<OpportunityViewModel> res) {
        List<CrmAgentLink> crmAgentLinkList = new ArrayList<>();
        if (!agentIds.isEmpty()) {
            crmAgentLinkList = customerMapper.getNameAndPosition(agentIds);
        }
        Map<String, String> agentIdToNameAndPosition = new HashMap<>();
        Map<String, String> agentIdToUserId = new HashMap<>();
        for (CrmAgentLink cal: crmAgentLinkList) {
            String agentId = cal.getAgentId();
            String name = cal.getLinkName();
            String position = cal.getLinkPosition();
            String combine = name + "-" + position;
            String userId = cal.getLinkUserId();
            agentIdToNameAndPosition.put(agentId, combine);
            agentIdToUserId.put(agentId, userId);
        }
        for (OpportunityViewModel ovm: res) {
            String agentId = ovm.getAgentId();
            ovm.setContact(agentIdToNameAndPosition.get(agentId));
            ovm.setContactId(agentIdToUserId.get(agentId));
        }
    }


    @Override
    public JournalStatisticsModel getJournalStatistic(StatisticsSearchParam param) {
        //获取所有的拜访记录, 筛选出拜访的客户, 陪同拜访次数以及个数, 深度拜访次数和个数拜访学院
        //需要的统计项是: 拜访客户的个数和次数, 陪同拜访的个数和次数
        //深度拜访的次数, 浅度拜访的次数
        //拜访一级学院的个数
        //拜访二级学院的个数
        //拜访院长的次数
        //拜访主任的次数
        JournalStatisticsModel res = new JournalStatisticsModel();
        //统计院校
        Set<String> schoolSet = new HashSet<>();
        Set<String> contactsSet = new HashSet<>();
        Set<String> deptSet = new HashSet<>();
        Set<String> accpomyDeptSet = new HashSet<>();
        Integer visitCustomerTimesCount = 0;
        Integer visitCustomerNumCount = 0;
        Integer accompanyVisitTimesCount = 0;
        Integer accompanyVisitNumCount = 0;
        Integer deepVisitTimesCount = 0;
        Integer normalVisitTimesCount = 0;
        Integer customerVisitNumCount = 0;
        Integer deptVisitNumCount = 0;
        Integer visitPresidentTimesCount = 0;
        Integer visitDeanTimesCount = 0;
        List<Journal> journalList = journalService.searchJournalForStatistics(param);
        for (Journal item : journalList) {
            //调查拜访记录
            if (CollectionUtils.isNotEmpty(item.getVisitRecords())) {
                for (VisitRecord record : item.getVisitRecords()) {
                    if (CollectionUtils.isNotEmpty(record.getChosenContacts())) {
                        for (Contacts contact : record.getChosenContacts()) {
                            contactsSet.add(contact.getTotalName());//联系人
                            if (contact.getContactsDept() != null) {
                                visitCustomerTimesCount = visitCustomerTimesCount + 1;
                                schoolSet.add(contact.getContactsDept().getCustomerName());
                                deptSet.add(contact.getContactsDept().getDeptName());
                                if (VisitTypeEnum.ACCOMPANY_VISIT.toString().equals(record.getVisitType())) {
                                    accpomyDeptSet.add(contact.getContactsDept().getDeptName());
                                }
                                if ("院长".equals(contact.getContactsDept().getContactsType())) {
                                    visitPresidentTimesCount = visitPresidentTimesCount + 1;
                                }
                                if ("主任".equals(contact.getContactsDept().getContactsType())) {
                                    visitDeanTimesCount = visitDeanTimesCount + 1;
                                }
                            }
                        }
                    }
                    if (record.getContactType()!=null && record.getContactType() == 2) {
                        deepVisitTimesCount = deptVisitNumCount + 1;
                    } else {
                        normalVisitTimesCount = normalVisitTimesCount + 1;
                    }
                    if (VisitTypeEnum.ACCOMPANY_VISIT.toString().equals(record.getVisitType())) {
                        accompanyVisitTimesCount = accompanyVisitTimesCount + 1;
                    }
                }
            }
        }
        deptVisitNumCount = deptSet.size();
        customerVisitNumCount = schoolSet.size();
        accompanyVisitNumCount = accpomyDeptSet.size();
        visitCustomerNumCount = contactsSet.size();
        res.setVisitCustomerTimesCount(visitCustomerTimesCount);
        res.setVisitCustomerNumCount(visitCustomerNumCount);
        res.setAccompanyVisitTimesCount(accompanyVisitTimesCount);
        res.setAccompanyVisitNumCount(accompanyVisitNumCount);
        res.setDeepVisitTimesCount(deepVisitTimesCount);
        res.setNormalVisitTimesCount(normalVisitTimesCount);
        res.setDeptVisitNumCount(deptVisitNumCount);
        res.setVisitPresidentTimesCount(visitPresidentTimesCount);
        res.setVisitDeanTimesCount(visitDeanTimesCount);
        res.setCustomerVisitNumCount(customerVisitNumCount);
        return res;
    }

    /**
     * 新增代理商
     */
    @Override
    public int getAgentCount(StatisticsSearchParam param) {
        return statisticsMapper.getAgentCount(param);
    }

    /**
     * 核心代理商
     */
    @Override
    public int getCoreAgent(StatisticsSearchParam param) {
        return statisticsMapper.getCoreAgent(param);
    }

    /**
     * 商机统计
     */
    @Override
    public List<KeyValue> getOppoStat(StatisticsSearchParam param) {
        return statisticsMapper.getOppoStat(param);
    }

    /**
     * D级别计划成交金额
     */
    @Override
    public Integer getDMoney(StatisticsSearchParam param) {
        return statisticsMapper.getDMoney(param);
    }


}
