/**
 * @author ZXL
 * @date 2019/7/17 10:43
 */
package com.xuebei.crm.statistics;

import com.google.gson.Gson;
import com.xuebei.crm.journal.Journal;
import com.xuebei.crm.journal.JournalService;
import com.xuebei.crm.sample.SampleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StatisticsTest {

    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    JournalService journalService;

    @Test
    public void testGetAgentCount(){
        StatisticsSearchParam param = new StatisticsSearchParam();
        param.setTimeSpanEnum(TimeSpanEnum.lastQuarter);
        List<String> userIds = new ArrayList<String>();
        userIds.add("6244a58a4d634392879860767b26d8bd");
        //userIds.add("6244a58a4d634392879860767b26d8bd");
        //userIds.add("e8c48ff8dfa64759b1bc7f83a6820d6b");
        //userIds.add("ae13e5b9c51143deba7e53288042b0ef");
        param.setUserIds(userIds);
        System.out.println(statisticsService.getAgentCount(param));
    }

    @Test
    public void testGetCoreAgent() {
        StatisticsSearchParam param = new StatisticsSearchParam();
        param.setTimeSpanEnum(TimeSpanEnum.toNow);
        List<String> userIds = new ArrayList<String>();
        userIds.add("6244a58a4d634392879860767b26d8bd");
        param.setUserIds(userIds);
        System.out.println(statisticsService.getCoreAgent(param));
    }

    @Test
    public void testGetOppoStat() {
        StatisticsSearchParam param = new StatisticsSearchParam();
        param.setTimeSpanEnum(TimeSpanEnum.toNow);
        List<String> userIds = new ArrayList<String>();
        userIds.add("0022287b3f7a404d8fcca44aa76842c2");
        userIds.add("001c52e79ee0484ca8158e926b5c05a3");
        param.setUserIds(userIds);
        System.out.println(statisticsService.getOppoStat(param));
    }

    @Test
    public void testGetDMoney() {
        StatisticsSearchParam param = new StatisticsSearchParam();
        param.setTimeSpanEnum(TimeSpanEnum.toNow);
        List<String> userIds = new ArrayList<String>();
        userIds.add("0022287b3f7a404d8fcca44aa76842c2");
        userIds.add("001c52e79ee0484ca8158e926b5c05a3");
        param.setUserIds(userIds);
        System.out.println(statisticsService.getDMoney(param));
    }

    @Test
    public void testGetOppoDetails() {
        StatisticsDetailsParam param = new StatisticsDetailsParam();
        param.setTimeSpanEnum(TimeSpanEnum.toNow);
        List<String> userIds = new ArrayList<String>();
        userIds.add("0022287b3f7a404d8fcca44aa76842c2");
        userIds.add("001c52e79ee0484ca8158e926b5c05a3");
        param.setStatisticsTypeEnum(StatisticsTypeEnum.newOpp);
        param.setUserIds(userIds);
        Gson gson = new Gson();
        System.out.println(gson.toJson(statisticsService.getOppoDetails(param)));
    }

    @Test
    public void testAllStaticstic() {
        long begin = System.currentTimeMillis();
        StatisticsSearchParam param = new StatisticsSearchParam();
        param.setTimeSpanEnum(TimeSpanEnum.toNow);
        List<String> userIds = new ArrayList<>();
        userIds.add("0022287b3f7a404d8fcca44aa76842c2");
        userIds.add("001c52e79ee0484ca8158e926b5c05a3");
        param.setUserIds(userIds);
        StatisticsTotalViewModel viewModel = statisticsService.getStatisticsTotalViewModel(param);
        long end = System.currentTimeMillis();
        Gson gson = new Gson();
        System.out.println(gson.toJson(viewModel));
        System.out.println(end - begin);

    }


    @Test
    public void testCustomerDetails() {
        StatisticsDetailsParam param = new StatisticsDetailsParam();
        List<String> userIds = new ArrayList<>();
        userIds.add("095a63e650984a3aa7b80901a313cc88");
        userIds.add("001c52e79ee0484ca8158e926b5c05a3");
        param.setUserIds(userIds);
        param.setTimeSpanEnum(TimeSpanEnum.toNow);
        List<ContactStatisticsViewModel> res = statisticsService.getContactsDetail(param);

        Gson gson = new Gson();
        System.out.println(gson.toJson(res));
    }

@Autowired
   private   SampleMapper sampleMapper;

    @Test
    public void testStatistics() {
        long begin = System.currentTimeMillis();
        StatisticsDetailsParam param = new StatisticsDetailsParam();
        List<String> userIds = sampleMapper.getUserIds();

        param.setUserIds(userIds);
        param.setTimeSpanEnum(TimeSpanEnum.toNow);
        JournalStatisticsModel res = statisticsService.getJournalStatistic(param);
        long end = System.currentTimeMillis();
        Gson gson = new Gson();
        System.out.println(gson.toJson(res));
        System.err.println(end - begin);
    }

    @Test
    public void testGetStatistics() {
        long begin = System.currentTimeMillis();
        StatisticsDetailsParam param = new StatisticsDetailsParam();
        List<String> userIds = sampleMapper.getUserIds();

        param.setUserIds(userIds);
        param.setTimeSpanEnum(TimeSpanEnum.toNow);
        List<Journal> journalList = journalService.searchJournalForStatistics(param);
        long end = System.currentTimeMillis();
        Gson gson = new Gson();
        System.err.println(end - begin);
    }
}
