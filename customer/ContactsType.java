package com.xuebei.crm.customer;

import com.google.gson.annotations.Expose;

public class ContactsType {

    @Expose
    private String contactsTypeId;

    @Expose
    private String typeName;

    public String getContactsTypeId() {
        return contactsTypeId;
    }

    public void setContactsTypeId(String contactsTypeId) {
        this.contactsTypeId = contactsTypeId;
    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


}
