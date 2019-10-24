package com.xuebei.crm.journal;

public enum VisitTypeEnum {
    VISIT("拜访"), VISIT_INSIDE("市内拜访"), OFFLINE("拜访"), PHONE("电话联系"),
    NORMAL_VISIT("普通拜访"),TRAN_VISIT("培训拜访"),ACCOMPANY_VISIT("陪同拜访"),COMPANY_RECEPTION("公司接待"),OTHER("其他");

    private String name;

    VisitTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static VisitTypeEnum getVisitTypeEnumEnum(String name) {
        for (VisitTypeEnum item : values()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }
}
