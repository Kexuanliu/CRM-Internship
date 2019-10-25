/**
 * @author ZXL
 * @date 2019/7/17 10:38
 */
package com.xuebei.crm.statistics;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.*;

@Mapper
public interface StatisticsMapper {

    int getAgentCount(StatisticsSearchParam param);

    int getCoreAgent(StatisticsSearchParam param);

    List<KeyValue> getOppoStat(StatisticsSearchParam param);

    Integer getDMoney(StatisticsSearchParam param);

    int getContactsCount(StatisticsSearchParam param);

    int getAllContacatsCount(StatisticsSearchParam param);

    List<Enclosure> getEnclosureStatus(@Param("deptIds") Set<String> deptIds);

    List<ContactStatisticsViewModel> getContactStatisticsViewModelList(StatisticsDetailsParam param);
}
