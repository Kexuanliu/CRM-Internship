/**
 * @author TRY
 * @date 2019/3/12 14:11
 */
package com.xuebei.crm.customer.agent;

public enum CustomerLevelEnum {

    CORE("核心"), EXCELLENT("优质"), MIDDLE("中等"), NORMAL("普通");

    private String name;

    CustomerLevelEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CustomerLevelEnum getCustomerLevelEnum(String name) {
        for (CustomerLevelEnum item : values()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }
}
