/**
 * @author ZXL
 * @date 2019/6/5 9:59
 */
package com.xuebei.crm.journal;

public enum ContactType {

    NoContact(0), TenMinutes(1), AboveTen(2);

    private Integer value;

    ContactType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
