package com.xuebei.crm.excelImport;

public enum ErrorMsgEnum {

    USERERROR("用户不正确"), FIELDERROR("字段不正确"), SAMEAGENT("代理商重名"),
    NOTEXIST("不存在"),SAMEOPP("商机重名"),SAMECONTACT("用户已存在");


    private String name;

    ErrorMsgEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
