package com.xuebei.crm.journal;

import com.xuebei.crm.company.CompanyMapper;
import com.xuebei.crm.customer.*;
import com.xuebei.crm.customer.agent.CrmAgent;
import com.xuebei.crm.customer.agent.CrmAgentLink;
import com.xuebei.crm.department.DeptMapper;
import com.xuebei.crm.department.DeptService;
import com.xuebei.crm.exception.AuthenticationException;
import com.xuebei.crm.exception.InformationNotCompleteException;
import com.xuebei.crm.member.Member;
import com.xuebei.crm.member.MemberService;
import com.xuebei.crm.opportunity.Opportunity;
import com.xuebei.crm.project.Project;
import com.xuebei.crm.project.ProjectMapper;
import com.xuebei.crm.statistics.StatisticsSearchParam;
import com.xuebei.crm.user.User;
import com.xuebei.crm.utils.CrmDateUtils;
import com.xuebei.crm.utils.Hoilday;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.xuebei.crm.utils.CrmMapBuildUtils.initEnumListMap;


@Service
public class JournalServiceImpl implements JournalService {

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private CustomerService customerService;

    private static final String USERNOTOWNER="用户不拥有此日志";

    /**
     * 创建一个新日志
     * 注意：调用该方法前，需要将 session 中 userId 装载入 journal 对象中(Not checked)
     *
     * @param journal
     */
    @Override
    public void createJournal(Journal journal) throws InformationNotCompleteException, AuthenticationException {
        checkBasicInfo(journal);
        checkVisitRecords(journal.getVisitRecords());

        List<Journal> drafts = journalMapper.findJournalDraft(journal.getUserId());
        if (drafts != null) {
            for (Journal draft : drafts){
                journalMapper.deleteJournal(draft.getUserId(), draft.getJournalId());
            }
        }
        journalMapper.createJournal(journal);
        insertVisitRecords(journal.getJournalId(), journal.getVisitRecords(), journal.getUserId(), journal.getUserName(), journal.getCreateTs());
    }

    @Override
    public void modifyJournal(Journal journal) throws InformationNotCompleteException, AuthenticationException {
        checkBasicInfo(journal);
        Journal oldJournal = journalMapper.queryJournalById(journal.getJournalId());
        if (oldJournal == null || !oldJournal.getUserId().equals(journal.getUserId())) {
            throw new AuthenticationException(USERNOTOWNER);
        } else if (oldJournal.getHasSubmitted()) {
            throw new AuthenticationException("日志已提交");
        }
        checkVisitRecords(journal.getVisitRecords());
        journalMapper.updateJournal(journal);
        deleteVisitRecords(journal.getJournalId());
        insertVisitRecords(journal.getJournalId(), journal.getVisitRecords(), journal.getUserId(), journal.getUserName(), journal.getCreateTs());
    }

    @Override
    public void deleteJournalById(String userId, Integer journalId) throws AuthenticationException {
        if (!journalMapper.userHasJournal(userId, journalId)) {
            throw new AuthenticationException(USERNOTOWNER);
        }

        deleteVisitRecords(journalId);
        journalMapper.deleteJournal(userId, journalId);
    }

    @Override
    public List<ManageJournal> getJournalState(String userId) {
        Date monthStart = new Date();
        monthStart.setHours(8);
        monthStart.setMinutes(30);
        monthStart.setSeconds(0);
        int date = monthStart.getDate();
        monthStart.setDate(date - 30);
        List<ManageJournal> manageJournals = journalMapper.getJournalState(userId, monthStart, null);
        Date tmp = new Date();
        tmp.setHours(9);
        ManageJournal tmpM = new ManageJournal();
        tmpM.setTagertDate(tmp);
        manageJournals.add(tmpM);
        manageJournals.sort((journal1, journal2) -> journal1.getTagertDate().after(journal2.getTagertDate()) ? 1 : -1);
        List<ManageJournal> manageJournals2 = new ArrayList<ManageJournal>();
        ManageJournal recordJournal = null;
        for (ManageJournal manageJournal : manageJournals) {
            if (manageJournal.getTagertDate().before(new Date(monthStart.getTime()))) {
                recordJournal = manageJournal;
            } else {
                if (recordJournal != null && recordJournal.getRepairDate() != null) {
                    recordJournal.setShowDate();
                    manageJournals2.add(recordJournal);
                }
                recordJournal = manageJournal;

                long subDay = (recordJournal.getTagertDate().getTime() - monthStart.getTime()) / 86400000;
                monthStart.setDate(monthStart.getDate() + 1);

                for (int i = 0; i < subDay; i++) {
                    Date tmpHoilday = new Date(monthStart.getTime() - 86400000);
                    try {
                        if (!Hoilday.isHoliday(tmpHoilday)) {
                            ManageJournal tmpManageJ = new ManageJournal();
                            tmpManageJ.setShowDate(new Date(monthStart.getTime() - 86400000));
                            manageJournals2.add(tmpManageJ);
                        }
                    } catch (Exception e) {

                    }
                    monthStart.setDate(monthStart.getDate() + 1);
                }
            }
        }
        return manageJournals2;
    }


    @Override
    public List<FollowJournal> getJournalFollow(String userId) {
        String companyId = companyMapper.queryCompanyIdByUserId(userId);
        Map<String, Float> money = companyMapper.queryJournalFine(companyId);
        Float delay = 0F;
        Float miss = 0F;
        if (money != null && !money.isEmpty()) {
            delay = money.get("delay");
            miss = money.get("miss");
        }
        List<String> userIDs = journalMapper.queryCompanyUserIds(companyId);
        List<FollowJournal> followJournals = new ArrayList<>();
        for (String user : userIDs) {
            Date monthStart = getMonthStartDate();
            List<ManageJournal> manageJournals = journalMapper.getJournalState(user, monthStart, null);
            Date tmp = new Date();
            //tmp.setDate(tmp.getDate()+1);
            tmp.setHours(9);

            ManageJournal tmpM = new ManageJournal();
            tmpM.setTagertDate(tmp);
            manageJournals.add(tmpM);
            manageJournals.sort((journal1, journal2) -> journal1.getTagertDate().after(journal2.getTagertDate()) ? 1 : -1);

            List<ManageJournal> manageJournals2 = new ArrayList<>();
            ManageJournal recordJournal = null;

            for (ManageJournal manageJournal : manageJournals) {
                if (manageJournal.getTagertDate().before(monthStart)) {
                    recordJournal = manageJournal;
                } else {
                    if (recordJournal != null && recordJournal.getRepairDate() != null) {
                        manageJournals2.add(recordJournal);

                    }
                    recordJournal = manageJournal;
                    long subDay2 = (recordJournal.getTagertDate().getTime() - monthStart.getTime()) / 86400000;
                    monthStart.setDate(monthStart.getDate() + 1);
                    for (int i = 0; i < subDay2; i++) {
                        Date tmpHoilday = new Date(monthStart.getTime() - 86400000);
                        try {
                            if (!Hoilday.isHoliday(tmpHoilday)) {
                                ManageJournal tmpManageJ = new ManageJournal();
                                tmpManageJ.setShowDate(new Date(monthStart.getTime() - 86400000));
                                manageJournals2.add(tmpManageJ);

                            }
                        } catch (Exception e) {
                            System.out.println("error4");
                            break;
                        }
                        monthStart.setDate(monthStart.getDate() + 1);
                    }

                }
            }
            int repairCount = 0;
            int loseCount = 0;
            for (ManageJournal manageJournal : manageJournals2) {
                if (manageJournal.getRepairDate() != null) {
                    repairCount++;
                } else if (manageJournal.getTagertDate() == null) {
                    loseCount++;
                }
            }
            followJournals.add(new FollowJournal(journalMapper.queryNameById(user), repairCount, loseCount, delay, miss));
        }
        return followJournals;
    }

    private Date getMonthStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public List<FollowJournal> getJournalFollow(String userId, int index, float delay, float miss) {
        List<String> userIDs = journalMapper.queryCompanyUserIds(userId);
        List<FollowJournal> followJournals = new ArrayList<>();
        for (String user : userIDs) {
            Date monthStart = getMonthStartDate();
            Date monthEnd = new Date(monthStart.getTime());
            monthStart.setMonth(monthStart.getMonth() - index);
            monthEnd.setMonth(monthStart.getMonth() + 1);
            List<ManageJournal> manageJournals = journalMapper.getJournalState(user, monthStart, monthEnd);
            monthEnd.setDate(1);
            monthEnd.setHours(9);

            ManageJournal tmpM = new ManageJournal();
            tmpM.setTagertDate(monthEnd);
            manageJournals.add(tmpM);
            manageJournals.sort((journal1, journal2) -> journal1.getTagertDate().after(journal2.getTagertDate()) ? 1 : -1);
            List<ManageJournal> manageJournals2 = new ArrayList<ManageJournal>();
            ManageJournal recordJournal = null;
            //monthStart.setDate(monthStart.getDate()+1);
            for (ManageJournal manageJournal : manageJournals) {
                if (manageJournal.getTagertDate().before(monthStart)) {
                    recordJournal = manageJournal;
                } else {
                    if (recordJournal != null && recordJournal.getRepairDate() != null) {
                        manageJournals2.add(recordJournal);
                    }
                    recordJournal = manageJournal;
                    long subDay2 = (recordJournal.getTagertDate().getTime() - monthStart.getTime()) / 86400000;
                    monthStart.setDate(monthStart.getDate() + 1);

                    for (int i = 0; i < subDay2; i++) {
                        Date tmpHoilday = new Date(monthStart.getTime() - 86400000);
                        try {
                            if (!Hoilday.isHoliday(tmpHoilday)) {
                                ManageJournal tmpManageJ = new ManageJournal();
                                tmpManageJ.setShowDate(new Date(monthStart.getTime() - 86400000));

                                manageJournals2.add(tmpManageJ);

                            }
                        } catch (Exception e) {
                            System.out.println("error2");
                            break;
                        }
                        monthStart.setDate(monthStart.getDate() + 1);
                    }

                }
            }
            int repairCount = 0;
            int loseCount = 0;
            for (ManageJournal manageJournal : manageJournals2) {
                if (manageJournal.getRepairDate() != null) {
                    repairCount++;
                } else if (manageJournal.getTagertDate() == null) {
                    loseCount++;
                }
            }
            followJournals.add(new FollowJournal(journalMapper.queryNameById(user), repairCount, loseCount, delay, miss));
        }
        return followJournals;
    }

    @Override
    public Journal queryJournalById(String userId, Integer journalId) throws AuthenticationException {
        if (!journalMapper.userHasJournal(userId, journalId))
            throw new AuthenticationException("用户不拥有此日志");

        Journal journal = journalMapper.queryJournalById(journalId);
        journal.setVisitRecords(journalMapper.queryVisitLogs(journalId));


        for (VisitRecord visitRecord : journal.getVisitRecords()) {
            visitRecord.setChosenContacts(journalMapper.queryVisitContacts(visitRecord.getVisitId()));
            List<VisitAgents> visitAgents = journalMapper.queryVisitAgents(visitRecord.getVisitId());
            List<String> linkUserIds = new ArrayList<>();
            for (VisitAgents visitAgents1 : visitAgents) {
                linkUserIds.add(visitAgents1.getAgentLinkId());
            }
            List<CrmAgentLink> crmAgentLinks = customerService.getCrmAgentLink(linkUserIds);
            visitRecord.setChosenAgents(crmAgentLinks);
        }

        return journal;
    }

    /**
     * 检查 journal 对象中 type summary plan hasSubmitted 成员是否为空，若为空则抛出异常
     *
     * @param journal 被检查的 journal 对象
     * @throws InformationNotCompleteException 信息不完整
     */
    private void checkBasicInfo(Journal journal) throws InformationNotCompleteException {
        if (journal.getType() == null)
            throw new InformationNotCompleteException("journal.type 不能为空");
        if (journal.getOther() == null)
            throw new InformationNotCompleteException("journal.other 不能为空");
        if (journal.getPlan() == null)
            throw new InformationNotCompleteException("journal.plan 不能为空");
        if (journal.getHasSubmitted() == null)
            throw new InformationNotCompleteException("journal.hasSubmitted 不能为空");
    }

    /**
     * 检查访问记录对象中的 信息完整度 和 联系人权限
     *
     * @param visitRecords 访问记录
     * @throws InformationNotCompleteException 信息不完整
     */
    private void checkVisitRecords(List<VisitRecord> visitRecords) throws InformationNotCompleteException {
        if (visitRecords == null)
            return;
        for (VisitRecord visitRecord : visitRecords) {
            if (visitRecord == null)
                throw new InformationNotCompleteException("visitRecord 不能为空");
            if (visitRecord.getVisitType() == null)
                throw new InformationNotCompleteException("visitType 不能为空");
            if (visitRecord.getVisitResult() == null)
                throw new InformationNotCompleteException("visitResult 不能为空");
        }
    }

    private void insertVisitRecords(Integer journalId, List<VisitRecord> visitRecords, String createId, String createName, Date createTs) {
        if (visitRecords == null)
            return;
        for (VisitRecord visitRecord : visitRecords) {
            visitRecord.setJournalId(journalId);
            journalMapper.insertVisitLog(visitRecord);
            if(CollectionUtils.isNotEmpty(visitRecord.getChosenContacts())){
                for (Contacts contacts : visitRecord.getChosenContacts()) {
                    journalMapper.insertVisitContacts(visitRecord.getVisitId(), contacts.getContactsId());
                }
            }
            if(CollectionUtils.isNotEmpty(visitRecord.getChosenAgents())){
                for (CrmAgentLink agent : visitRecord.getChosenAgents()) {
                    VisitAgents visitAgents = new VisitAgents();
                    CrmAgent crmAgent = customerService.queryCrmAgent(agent.getAgentId());
                    visitAgents.setVisitLogId(visitRecord.getVisitId());
                    visitAgents.setAgentId(agent.getAgentId());
                    visitAgents.setAgentLinkId(agent.getLinkUserId());
                    visitAgents.setCreateId(createId);
                    visitAgents.setCreateName(createName);
                    if (createTs == null) {
                        createTs = new Date();
                    }
                    visitAgents.setCreateTs(createTs);
                    visitAgents.setShowInfo(crmAgent.getCompanyName() + "-" + agent.getLinkName()
                            + "(" + agent.getLinkPosition() + ")");
                    journalMapper.addVisitAgents(visitAgents);
                }
            }
        }
    }

    private void deleteVisitRecords(Integer journalId) {
        List<VisitRecord> records = journalMapper.queryVisitLogs(journalId);
        for (VisitRecord record : records) {
            journalMapper.deleteVisitContacts(record.getVisitId());
            journalMapper.deleteVisitAgents(record.getVisitId());
        }
        journalMapper.deleteVisitLog(journalId);
    }

    public List<Journal> searchJournalInTheSameDay(String journalId) {
        Journal myJournal = journalMapper.searchJournal(journalId);
        JournalSearchParam param = new JournalSearchParam();
        param.setUserId(myJournal.getUserId());

        Date startTime = CrmDateUtils.getJournalDayFirstTime(myJournal.getCreateTs());
        Date endTime = CrmDateUtils.getJournalDayLastTime(myJournal.getCreateTs());
        param.setStartTime(startTime);
        param.setEndTime(endTime);

        List<Journal> journalList = journalMapper.searchMyJournal(param);
        //统计日志有多少人已读
        setJournalData(param.getUserId(), journalList);
        return journalList;
    }

    @Override
    public List<Journal> searchJournal(JournalSearchParam param) {
        List<Journal> rawJournalList = new ArrayList<>();

        if (param.getIsMine() == null || param.getIsMine() == 0) {
            // 非仅看自己时，查询下属的日志

            ///分解发送人字符串
            if (param.getSenderIds() != null && !param.getSenderIds().equals("")) {
                param.setSdId(param.getSenderIds().trim().split(","));
            }

            // 当未选择发送人，则找到所有下属作为筛选目标
            if (param.getSdId() == null || param.getSdId().length == 0) {
                Set<String> childs = getAllSubordinatesUserId(param.getUserId());
                //set转array
                String[] childsArray = new String[childs.size()];
                childs.toArray(childsArray);
                //sdId是一个String[]
                param.setSdId(childsArray);
            }
               //获取sdId中每个元素的journal
            rawJournalList.addAll(journalMapper.searchReceivedJournal(param));
        }
        if (param.getSenderIds() == null || param.getSenderIds().length() == 0) {
            //非按下属筛选时，查询我的日志
            rawJournalList.addAll(journalMapper.searchMyJournal(param));
        }

        List<Journal> resultJournalList = mergeSameDayJournal(rawJournalList);
        resultJournalList.sort((journal1, journal2) -> journal1.getCreateTs().before(journal2.getCreateTs()) ? 1 : -1);
        resultJournalList = getPagedList(resultJournalList, param.getPageNo(), param.getPageSize());
        setJournalData(param.getUserId(), resultJournalList);
        return resultJournalList;
    }


    @Override
    public List<Journal> searchJournalFaster(JournalSearchParam param) {
        List<Journal> rawJournalList = new ArrayList<>();
        if (param.getIsMine() == null || param.getIsMine() == 0) {
            // 非仅看自己时，查询下属的日志
            ///分解发送人字符串
            if (param.getSenderIds() != null && !param.getSenderIds().equals("")) {
                param.setSdId(param.getSenderIds().trim().split(","));
            }
            // 当未选择发送人，则找到所有下属作为筛选目标
            if (param.getSdId() == null || param.getSdId().length == 0) {
                Set<String> childs = getAllSubordinatesUserId(param.getUserId());
                childs.add(param.getUserId());
                //set转array
                String[] childsArray = new String[childs.size()];
                childs.toArray(childsArray);
                //sdId是一个String[]
                param.setSdId(childsArray);
            }
            //获取sdId中每个元素的journal

        } else {
            String[] arr = new String[]{param.getUserId()};
            param.setSdId(arr);
        }
        rawJournalList.addAll(journalMapper.searchReceivedJournal(param));
        List<Journal> resultJournalList = getDayFirst(rawJournalList);
        setJournalDataFaster(param.getUserId(), resultJournalList);
        return resultJournalList;
    }

    @Override
    public List<Journal> searchJournalForStatistics(StatisticsSearchParam param) {
        List<Journal> rawJournalList = new ArrayList<>();
        rawJournalList.addAll(journalMapper.searchJournalForStatistics(param));
        List<Journal> resultJournalList = getDayFirst(rawJournalList);
        setJournalDataForStatistics(resultJournalList);
        return resultJournalList;
    }

    private List<Journal> getDayFirst(List<Journal> input) {

        //首先-9小时
        changeTime(input, -9);
        List<Journal> res = new ArrayList<>();
        Set<String> timeSet = new HashSet<>();
        for (Journal item : input) {
            String setStr;
            if (item.getCreateTs() != null) {
                setStr = CrmDateUtils.format(item.getCreateTs(), "yyyyMMdd");
            } else {
                setStr = item.getCreateTime();
            }
            if (!timeSet.contains(setStr + item.getUserId() + item.getType())) {
                res.add(item);
                timeSet.add(setStr + item.getUserId() + item.getType());
            }
        }
        //对结果+9小时
        changeTime(res, 9);
        return res;
    }

    private void changeTime(List<Journal> input, Integer hour) {
        for (Journal item : input) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(item.getCreateTs());
            calendar.add(Calendar.HOUR, hour);
            item.setCreateTs(calendar.getTime());
        }
    }


    public static <T> List<T> getPagedList(List<T> list, Integer pageNo, Integer pageSize) {
        if (pageNo == null || pageSize == null) {
            return list;
        }

        int totalItems = list.size();
        int fromIndex = (pageNo - 1) * pageSize;
        if (fromIndex >= totalItems) {
            return new ArrayList<>();
        }

        int toIndex = fromIndex + pageSize;
        toIndex = toIndex > totalItems ? totalItems : toIndex;
        return list.subList(fromIndex, toIndex);
    }

    private List<Journal> mergeSameDayJournal(List<Journal> rawJournalList) {
        Map<String, Map<Date, List<Journal>>> userDayJournalsMap = new HashMap<>();
        for (Journal journal : rawJournalList) {
            userDayJournalsMap.computeIfAbsent(journal.getUserId(), userId -> new HashMap<>())
                    .computeIfAbsent(CrmDateUtils.getJournalDayFirstTime(journal.getCreateTs()), date -> new ArrayList<>())
                    .add(journal);
        }
        List<Journal> rltJournalList = new ArrayList<>();
        for (Map.Entry<String, Map<Date, List<Journal>>> userJournalEntry : userDayJournalsMap.entrySet()) {
            for (Map.Entry<Date, List<Journal>> dayJournalList : userJournalEntry.getValue().entrySet()) {
                rltJournalList.add(getLastTimeJournal(dayJournalList.getValue()));
            }
        }
        return rltJournalList;
    }

    private Journal getLastTimeJournal(List<Journal> journalList) {
        if (CollectionUtils.isEmpty(journalList)) {
            return null;
        }
        Journal maxDateJournal = journalList.get(0);
        for (Journal journal : journalList) {
            if (journal.getCreateTs().after(maxDateJournal.getCreateTs())) {
                maxDateJournal = journal;
            }
        }
        return maxDateJournal;
    }

    private void setJournalData(String userId, List<Journal> journalList) {
        Date todayFirstTime = CrmDateUtils.getTodayJournalFirstTime();
        for (Journal journal : journalList) {
            journal.setVisitRecords(journalMapper.queryVisitLogs(journal.getJournalId()));
            for (VisitRecord visitRecord : journal.getVisitRecords()) {
                visitRecord.setComments(journalMapper.getVisitLogComments(visitRecord.getVisitId()));
                visitRecord.setChosenContacts(journalMapper.queryVisitContacts(visitRecord.getVisitId()));
                List<VisitAgents> visitAgents = journalMapper.queryVisitAgents(visitRecord.getVisitId());
                for (VisitAgents visitAgent : visitAgents) {
                    Contacts contacts = new Contacts();
                    contacts.setTotalName(visitAgent.getShowInfo());
                    visitRecord.getChosenContacts().add(contacts);
                }
                Opportunity opportunity = projectMapper.queryOpportunity(visitRecord.getOpportunityId());
                if (opportunity != null) {
                    visitRecord.setOpportunityName(opportunity.getOpportunityName());
                    visitRecord.setOpportunity(opportunity);
                    visitRecord.getOpportunity().setTotalName("");
                }
            }
            // 查询日志补丁
            journal.setJournalPatches(journalMapper.queryJournalPatch(journal.getJournalId()));
            // 是否是自己
            journal.setMine(journal.getUserId().equals(userId));

            Date repairTs = journalMapper.queryRepairDate(journal.getJournalId());
            if (repairTs != null) {
                journal.setRepairTs(repairTs);
            }
            journal.setToday(journal.getCreateTs().after(todayFirstTime));
            journal.setComments(journalMapper.getComments(journal.getJournalId()));
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public List searchDatail(String journalId) {
        Journal myJournal = journalMapper.searchJournal(journalId);
        List<User> journalUnread = journalMapper.searchUnread(journalId);
        List<User> journalRead = journalMapper.searchRead(journalId);
        List result = new ArrayList();
        result.add(myJournal);
        result.add(journalUnread);
        result.add(journalRead);
        return result;
    }

    @Override
    public List<BigCustomer> getAllCustomers(String companyId, String userId) {
        List<BigCustomer> bigCustomers = new ArrayList<>();
        List<JournalCustomer> customerList = journalMapper.queryJournalCustomersByCompanyId(userId);
        for (JournalCustomer customer : customerList) {
            String customerId = customer.getCustomerId();
            List<Department> departmentList = journalMapper.searchDepts(customerId, userId);
            //设置圈地状态
            deptService.setEnclosureStatus(departmentList);
            //添加联系人和三级机构
            setSubDeptAndContacts(departmentList);
            //按圈地状态分类dept
            EnumMap<EnclosureStatusEnum, List<Department>> deptMap = getDeptMap(departmentList);
            deptService.setEnclosureStatus(departmentList);
            //下属人员
            List<Member> subMemberList = memberService.searchSubMemberList(userId);
            for (Member member : subMemberList) {
                //下属圈地
                List<Department> subMyDepts = deptMapper.searchMyDepts(customerId, member.getMemberId());
                deptService.setEnclosureStatus(subMyDepts);
                setSubDeptAndContacts(subMyDepts);
                for (Department department : subMyDepts) {
                    department.setApplyName(member.getMemberName());
                }

                //下属申请
                List<Department> subApplyingDepts = deptMapper.searchApplyingDepts(customerId, member.getMemberId());
                deptService.setEnclosureStatus(subApplyingDepts);
                setSubDeptAndContacts(subApplyingDepts);
                for (Department department : subApplyingDepts) {
                    department.setApplyName(member.getMemberName());
                }

                deptMap.get(EnclosureStatusEnum.MINE).addAll(subMyDepts);
            }

            List<Department> rltList = new ArrayList<>();
            rltList.addAll(deptMap.get(EnclosureStatusEnum.MINE));
            if (!rltList.isEmpty()) {
                bigCustomers.add(new BigCustomer(customer.getName(), rltList));
            }
        }
        return bigCustomers;
    }

    private EnumMap<EnclosureStatusEnum, List<Department>> getDeptMap(List<Department> departmentList) {
        EnumMap<EnclosureStatusEnum, List<Department>> deptMap = initEnumListMap(EnclosureStatusEnum.class);
        for (Department department : departmentList) {
            if (exitPerson(department)) {
                deptMap.get(department.getEnclosureStatus()).add(department);
            }
        }
        return deptMap;
    }

    private boolean exitPerson(Department d) {
        boolean tag = false;
        if (d.getContactsList() != null && !d.getContactsList().isEmpty()) {
            tag = true;
        }
        if (d.getDepartmentList() != null) {
            List<Department> rmdepartments = new ArrayList<>();
            for (Department department : d.getDepartmentList()) {
                if (department.getContactsList() != null && !department.getContactsList().isEmpty()) {
                    tag = true;
                } else {
                    rmdepartments.add(department);
                }
            }
            for (Department as : rmdepartments) {
                d.getDepartmentList().remove(as);
            }
        }
        return tag;
    }

    private void setSubDeptAndContacts(List<Department> departmentList) {
        //二级机构
        for (Department department : departmentList) {
            if (department.getEnclosureStatus() == EnclosureStatusEnum.MINE) {
                deptService.addSubDeptAndContacts(department,false);
            }
        }
    }

    @Override
    public List<JournalCustomer> getAllContacts(String companyId, String userId) {
        List<JournalCustomer> customerList = journalMapper.queryJournalCustomersByCompanyId(userId);
        List<Member> memberList = memberService.searchSubMemberList(userId);
        for (JournalCustomer customer : customerList) {
            try {
                String customerId = customer.getCustomerId();
                List<String> deptList = journalMapper.queryDeptId(userId, customerId);
                for (Member member : memberList) {
                    deptList.addAll(journalMapper.queryDeptId(member.getMemberId(), customerId));
                }
                List<Contacts> contactsList = new ArrayList<>();
                for (String deptId : deptList) {
                    contactsList.addAll(journalMapper.queryContactsByCustomerId(deptId));
                }
                customer.setContactsGroup(contactsList);
            } catch (NullPointerException e) {
                break;
            }
        }
        return customerList;
    }

    /**
     * 重写 by zxl
     * 获取所有下属的ID
     */
    public Set<String> getAllSubordinatesUserId(String userId) {
        String fullPath = journalMapper.queryUserFullPathByUserId(userId);
        if (StringUtils.isEmpty(fullPath)) {
            return new HashSet<>();
        }
        return journalMapper.querySuberUserIdByFullpath(fullPath);
    }

    public Set<Project> getAllSubordinatesProjects(Set<String> userGroup) {
        // 这样实现目的是，防止以后查询projects的接口发生改变，发生碰撞
        Set<Project> projectSet = new HashSet<>();
        for (String userId : userGroup) {
            List<Project> projects = projectMapper.queryProjectsByUserId(userId);
            projectSet.addAll(projects);
        }
        return projectSet;
    }

    public List<Opportunity> getAllSubordinatesOpportunity(Set<String> userGroup) {
        if (CollectionUtils.isEmpty(userGroup)) {
            return new ArrayList<>();
        }
        List<Opportunity> opportunities = projectMapper.queryOpportunitiesByUserIds(userGroup);
        return opportunities;
    }

    /**
     * 获取拜访记录
     *
     * @author zxl
     * @date 18:17 2019/3/19
     * @return
     * @throws
     * @since
    */
    @Override
    public List<VisitAgentViewModel> getVisitAgentViewModelList(String agentId) {
        return journalMapper.getVisitAgentViewModelList(agentId);
    }


    //通过代理商以及院校查找商机
    @Override
    public List<Opportunity> getSubordinatesOpportunityByAgentAndCustomerList(Set<String> userGroup, List<String> agents, List<String> customerList,List<String> userIds ) {
        if(CollectionUtils.isEmpty(agents)&&CollectionUtils.isEmpty(customerList)){
            return new ArrayList<>();
        }
        List<Opportunity> res = projectMapper.getSubordinatesOpportunityByAgentAndCustomerList(userGroup, agents, customerList, userIds);
        buildOppShowInfo(res);
        return res;
    }

    //填补空白
    private void buildOppShowInfo(List<Opportunity> res) {
        for (Opportunity item : res) {
            StringBuilder stringBuilder = new StringBuilder();
            if (StringUtils.isNotBlank(item.getCustomerName())) {
                stringBuilder.append((item.getCustomerName()));
                if (StringUtils.isNotBlank(item.getFailReason())) {
                    stringBuilder.append("-");
                    stringBuilder.append((item.getFailReason()));
                }
            }
            item.setShowInfo(stringBuilder.toString());
        }
    }


    private void setJournalDataFaster(String userId, List<Journal> journalList) {
        List<Integer> journalIds = new ArrayList<>();
        for (Journal journal : journalList) {
            journalIds.add(journal.getJournalId());
        }
        Date todayFirstTime = CrmDateUtils.getTodayJournalFirstTime();
        List<Integer> visitRecordIds = new ArrayList<>();
        List<Integer> oppIds = new ArrayList<>();
        Map<Integer, List<VisitRecord>> visitRecordMap = getVisitRecord(journalIds, visitRecordIds, oppIds, false);
        Map<Integer, Opportunity> opportunityMap = getOppMap(oppIds);
        Map<Integer, List<VisitAgents>> visitAgentsMap = getVisitAgentsMap(visitRecordIds);
        Map<Integer, List<VisitLogComment>> visitCommentMap = getVisitCommentMap(visitRecordIds);
        Map<Integer, List<Contacts>> visitContactMap = getVisitContactMap(visitRecordIds);
        Map<Integer, List<JournalPatch>> journalPatchMap = getJournalPatchByJournalIds(journalIds);
        Map<Integer, Date> repairDate = getRepairTsByJournalIds(journalIds);
        Map<Integer, List<JournalComment>> commentMap = getCommentsByJournalIds(journalIds);
        for (Journal journal : journalList) {
            List<VisitRecord> visitRecordList = visitRecordMap.get(journal.getJournalId());
            if (CollectionUtils.isNotEmpty(visitRecordList)) {
                for (VisitRecord visitRecord : visitRecordList) {
                    Integer visitRecordId = visitRecord.getVisitId();
                    if (visitRecordId != null) {
                        visitRecord.setComments(visitCommentMap.get(visitRecordId));
                        visitRecord.setChosenContacts(visitContactMap.get(visitRecordId));
                        List<VisitAgents> visitAgents = visitAgentsMap.get(visitRecordId);
                        if (CollectionUtils.isNotEmpty(visitAgents)) {
                            if (CollectionUtils.isEmpty(visitRecord.getChosenContacts())) {
                                visitRecord.setChosenContacts(new ArrayList<>());
                            }
                            for (VisitAgents visitAgent : visitAgents) {
                                Contacts contacts = new Contacts();
                                contacts.setTotalName(visitAgent.getShowInfo());
                                visitRecord.getChosenContacts().add(contacts);
                            }
                        }
                        Opportunity opportunity = opportunityMap.get(visitRecord.getOpportunityId());
                        if (opportunity != null) {
                            visitRecord.setOpportunityName(opportunity.getOpportunityName());
                            visitRecord.setOpportunity(opportunity);
                            visitRecord.getOpportunity().setTotalName("");
                        }
                    }
                }
            }
            // 是否是自己
            journal.setMine(journal.getUserId().equals(userId));
            journal.setJournalPatches(journalPatchMap.get(journal.getJournalId()));
            Date repairTs = repairDate.get(journal.getJournalId());
            if (repairTs != null) {
                journal.setRepairTs(repairTs);
            }
            journal.setToday(journal.getCreateTs().after(todayFirstTime));
            journal.setComments(commentMap.get(journal.getJournalId()));
            journal.setVisitRecords(visitRecordList);
        }
    }


    private void setJournalDataForStatistics(List<Journal> journalList) {
        List<Integer> journalIds = new ArrayList<>();
        for (Journal journal : journalList) {
            journalIds.add(journal.getJournalId());
        }
        List<Integer> visitRecordIds = new ArrayList<>();
        List<Integer> oppIds = new ArrayList<>();
        Map<Integer, List<VisitRecord>> visitRecordMap = getVisitRecord(journalIds, visitRecordIds, oppIds, true);
        Map<Integer, List<Contacts>> visitContactMap = getVisitContactMap(visitRecordIds);
        for (Journal journal : journalList) {
            List<VisitRecord> visitRecordList = visitRecordMap.get(journal.getJournalId());
            if (CollectionUtils.isNotEmpty(visitRecordList)) {
                for (VisitRecord visitRecord : visitRecordList) {
                    Integer visitRecordId = visitRecord.getVisitId();
                    if (visitRecordId != null) {
                        visitRecord.setChosenContacts(visitContactMap.get(visitRecordId));
                    }
                }
            }
            journal.setVisitRecords(visitRecordList);
        }
    }

    /**
     * 一次获取所有的评论 ,然后再进行装载
     */
    private Map<Integer, List<JournalComment>> getCommentsByJournalIds(List<Integer> journalIds) {
        Map<Integer, List<JournalComment>> commentMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(journalIds)) {
            List<JournalComment> journalComments = journalMapper.getCommentsByIds(journalIds);
            for (JournalComment journalComment : journalComments) {
                List<JournalComment> commentList = commentMap.get(journalComment.getJournalId());
                if (CollectionUtils.isEmpty(commentList)) {
                    commentList = new ArrayList<>();
                }
                commentList.add(journalComment);
                commentMap.put(journalComment.getJournalId(), commentList);
            }
        }
        return commentMap;
    }

    /**
     * 一次查询补充日期
     */
    private Map<Integer, Date> getRepairTsByJournalIds(List<Integer> journalIds) {
        Map<Integer, Date> repairDate = new HashMap<>();
        if (CollectionUtils.isNotEmpty(journalIds)) {
            List<RepairJournal> repairJournals = journalMapper.getRepairJournalList(journalIds);
            for (RepairJournal item : repairJournals) {
                repairDate.put(item.getJournalId(), item.getRepairTs());
            }
        }
        return repairDate;
    }

    /**
     * 一次查询日志补丁
     */
    private Map<Integer, List<JournalPatch>> getJournalPatchByJournalIds(List<Integer> journalIds) {
        Map<Integer, List<JournalPatch>> commentMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(journalIds)) {
            List<JournalPatch> journalPatches = journalMapper.getJournalPatchByIds(journalIds);
            for (JournalPatch item : journalPatches) {
                List<JournalPatch> journalPatchList = commentMap.get(item.getJournalId());
                if (CollectionUtils.isEmpty(journalPatchList)) {
                    journalPatchList = new ArrayList<>();
                }
                journalPatchList.add(item);
                commentMap.put(item.getJournalId(), journalPatchList);
            }
        }
        return commentMap;
    }

    /**
     * 一次获取所有访问记录
     */
    private Map<Integer, List<VisitRecord>> getVisitRecord(List<Integer> journalIds, List<Integer> visitRecordIds, List<Integer> oppIds, boolean isSample) {
        Map<Integer, List<VisitRecord>> visitRecordMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(journalIds)) {
            List<VisitRecord> visitRecordList ;
            if(isSample){
                visitRecordList = journalMapper.queryVisitLogsByIdsSimple(journalIds);
            }else{
                visitRecordList = journalMapper.queryVisitLogsByIds(journalIds);
            }
            for (VisitRecord item : visitRecordList) {
                visitRecordIds.add(item.getVisitId());
                List<VisitRecord> visitRecords = visitRecordMap.get(item.getJournalId());
                if (CollectionUtils.isEmpty(visitRecords)) {
                    visitRecords = new ArrayList<>();
                }
                visitRecords.add(item);
                visitRecordIds.add(item.getVisitId());
                oppIds.add(item.getOpportunityId());
                visitRecordMap.put(item.getJournalId(), visitRecords);
            }
        }
        return visitRecordMap;
    }

    private Map<Integer, Opportunity> getOppMap(List<Integer> oppIds) {
        Map<Integer, Opportunity> opportunityMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(oppIds)) {
            List<Opportunity> opportunityList = projectMapper.getOpportunityByIds(oppIds);
            for (Opportunity item : opportunityList) {
                opportunityMap.put(item.getOpportunityId(), item);
            }
        }
        return opportunityMap;
    }

    private Map<Integer, List<VisitAgents>> getVisitAgentsMap(List<Integer> visitIds) {
        Map<Integer, List<VisitAgents>> visitAgentsMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(visitIds)) {
            List<VisitAgents> oriList  = journalMapper.queryVisitAgentsByIds(visitIds);
            for (VisitAgents item : oriList) {
                List<VisitAgents> visitAgents = visitAgentsMap.get(item.getVisitLogId());
                if (CollectionUtils.isEmpty(visitAgents)) {
                    visitAgents = new ArrayList<>();
                }
                visitAgents.add(item);
                visitAgentsMap.put(item.getVisitLogId(), visitAgents);
            }
        }
        return visitAgentsMap;
    }

    private Map<Integer, List<VisitLogComment>> getVisitCommentMap(List<Integer> visitIds) {
        Map<Integer, List<VisitLogComment>> visitCommentMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(visitIds)) {
            List<VisitLogComment> oriList = journalMapper.getVisitLogCommentsByIds(visitIds);
            for (VisitLogComment item : oriList) {
                List<VisitLogComment> visitLogComments = visitCommentMap.get(item.getVisitLogId());
                if (CollectionUtils.isEmpty(visitLogComments)) {
                    visitLogComments = new ArrayList<>();
                }
                visitLogComments.add(item);
                visitCommentMap.put(item.getVisitLogId(), visitLogComments);
            }
        }
        return visitCommentMap;
    }

    private Map<Integer, List<Contacts>> getVisitContactMap(List<Integer> visitIds) {
        Map<Integer, List<Contacts>> visitContactMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(visitIds)) {
            List<Contacts> oriList = journalMapper.queryVisitContactsByIds(visitIds);
            for (Contacts item : oriList) {
                List<Contacts> contacts = visitContactMap.get(item.getVisitLogId());
                if (CollectionUtils.isEmpty(contacts)) {
                    contacts = new ArrayList<>();
                }
                contacts.add(item);
                visitContactMap.put(item.getVisitLogId(), contacts);
            }
        }
        return visitContactMap;
    }
}
