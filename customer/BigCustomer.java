package com.xuebei.crm.customer;

import com.google.gson.annotations.Expose;

import java.util.List;

public class BigCustomer {

    public BigCustomer(String name, List<Department> d) {
        this.name = name;
        this.depts = d;
    }

    @Expose
    private String name;
    @Expose
    private List<Department> depts;
    @Expose
    private boolean contactsFold = true;

    public boolean getContactsFold() {
        return contactsFold;
    }

    public void setContactsFold(boolean contactsFold) {
        this.contactsFold = contactsFold;
    }

    public String getRealname() {
        return name;
    }

    public void setRealname(String realname) {
        this.name = realname;
    }

    public List<Department> getDepts() {
        return depts;
    }

    public void setDepts(List<Department> depts) {
        this.depts = depts;
    }
}
