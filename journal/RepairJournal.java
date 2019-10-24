/**
 * @author ZXL
 * @date 2019/6/11 10:11
 */
package com.xuebei.crm.journal;

import java.util.Date;

public class RepairJournal {
    private Integer journalId;

    private Date repairTs;

    public Integer getJournalId() {
        return journalId;
    }

    public void setJournalId(Integer journalId) {
        this.journalId = journalId;
    }

    public Date getRepairTs() {
        return repairTs;
    }

    public void setRepairTs(Date repairTs) {
        this.repairTs = repairTs;
    }
}
