package com.xuebei.crm.customer;

public enum CustomerFromEnum {

    HIGHEREDUCATION("高教展"),GOLDCOMPETITION("金砖赛"), EXHIBITION("展会"), INTRODUCTION("转介绍"),INTERNET("网络"),
    COMPANY("公司");


    private String name;

    CustomerFromEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CustomerFromEnum getCustomerFromEnum(String name) {
        for (CustomerFromEnum item : values()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }
}
