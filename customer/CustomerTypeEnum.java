package com.xuebei.crm.customer;

public enum CustomerTypeEnum {

    SCHOOL("学校"), COMPANY("公司"),COLLEAGE ("高校"),
    VOCATIONSCHOOL("高职"), SECONDARYSCHOOL("中职"), k12("k12"),AGENT("代理商");


    private String name;

    CustomerTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CustomerTypeEnum getCustomerTypeEnum(String name) {
        for (CustomerTypeEnum item : values()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }
}
