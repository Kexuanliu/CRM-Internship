package com.xuebei.crm.excelImport;

public enum ImportTypeEnum {

    AGENT(1), SCHOOL(2), OPP(3), JOURNAL(4);

    private Integer value;

    ImportTypeEnum(Integer value) {

        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
