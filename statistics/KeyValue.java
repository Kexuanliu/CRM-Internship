package com.xuebei.crm.statistics;

import com.google.gson.annotations.Expose;

public class KeyValue {
    @Expose
    private String key;
    @Expose
    private Integer value;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
