/**
 * @author ZXL
 * @date 2019/7/17 10:37
 */
package com.xuebei.crm.statistics;


import java.util.List;

public interface StatisticsService {

    StatisticsTotalViewModel getStatisticsTotalViewModel(StatisticsSearchParam param);

    int getALevelContacts(StatisticsSearchParam param);

    int getAllContacats(StatisticsSearchParam param);

    int getAgentCount(StatisticsSearchParam param);

    int getCoreAgent(StatisticsSearchParam param);

    JournalStatisticsModel getJournalStatistic(StatisticsSearchParam param);

    /**
     * return number of sales opportunities corresponding to each level
     * @param param StatisticsSearchParam param
     * @return a list of key-value pairs where key is the level (A, B1, B2, B3, B4, C, D)
     * and value is the count of sales opportunity in this level
     */
    List<KeyValue> getOppoStat(StatisticsSearchParam param);

    Integer getDMoney(StatisticsSearchParam param);

    List<OpportunityViewModel> getOppoDetails(StatisticsDetailsParam param);

    List<AgentStatisticsViewModel> getAgentDetails(StatisticsSearchParam param);

    List<ContactStatisticsViewModel> getContactsDetail(StatisticsDetailsParam param);

    List<VisitStatisticsViewModel> getVisitDetails(StatisticsSearchParam param);
}
