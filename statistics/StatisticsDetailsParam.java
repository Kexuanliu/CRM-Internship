/**
 * @author ZXL
 * @date 2019/7/24 10:11
 */
package com.xuebei.crm.statistics;

import java.util.*;

public class StatisticsDetailsParam extends  StatisticsSearchParam {

    /**
    *   类型
    */
    private StatisticsTypeEnum statisticsTypeEnum;


    private String level;

    private String[] types;

    public StatisticsTypeEnum getStatisticsTypeEnum() {
        return statisticsTypeEnum;
    }

    public void setStatisticsTypeEnum(StatisticsTypeEnum statisticsTypeEnum) {
        this.statisticsTypeEnum = statisticsTypeEnum;
        if (this.statisticsTypeEnum == StatisticsTypeEnum.newOpp) {
            this.types = new String[]{"A", "B1", "B2", "B3", "B4", "C", "D"};
        } else if (this.statisticsTypeEnum == StatisticsTypeEnum.newAOpp) {
            this.types = new String[]{"A"};
        } else if (this.statisticsTypeEnum == StatisticsTypeEnum.newBOpp) {
            this.types = new String[]{"B1", "B2", "B3", "B4"};
        } else if (this.statisticsTypeEnum == StatisticsTypeEnum.newCOpp) {
            this.types = new String[]{"C"};
        } else if (this.statisticsTypeEnum == StatisticsTypeEnum.newDOpp || this.statisticsTypeEnum == StatisticsTypeEnum.DMoney) {
            this.types = new String[]{"D"};
        }
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String[] getTypes() { return types; }

    public void setTypes(String[] types) { this.types = types; }
}
