package com.xuebei.crm.customer.agent;

public enum CooperationEnum {

    STRONG("强烈"),MIDDLE("中等"),NORMAL("普通"),WEAK("弱");

    private String name;

    CooperationEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CooperationEnum getCooperationEnum(String name) {
        for (CooperationEnum item : values()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }
}
