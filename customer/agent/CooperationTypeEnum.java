/**
 * @author TRY
 * @date 2019/3/12 14:13
 */
package com.xuebei.crm.customer.agent;

public enum CooperationTypeEnum {

    SPARATE("合作分成"), PROCUREMENT("采购模式"), CONTROLSTANDARD("硬件控标");

    private String name;

    CooperationTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CooperationTypeEnum getCooperationTypeEnum(String name) {
        for (CooperationTypeEnum item : values()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }
}
