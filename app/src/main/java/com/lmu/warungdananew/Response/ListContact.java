package com.lmu.warungdananew.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListContact {

    public ListContact(Integer id, Integer idContact, String contactFirstName, String contactLastName,
                       String contactGender, Integer idOrderMstStatus, String status) {
        this.id = id;
        this.idContact = idContact;
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.contactGender = contactGender;
        this.idOrderMstStatus = idOrderMstStatus;
        this.status = status;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_contact")
    @Expose
    private Integer idContact;
    @SerializedName("contact_first_name")
    @Expose
    private String contactFirstName;
    @SerializedName("contact_last_name")
    @Expose
    private String contactLastName;
    @SerializedName("contact_gender")
    @Expose
    private String contactGender;
    @SerializedName("id_order_mst_status")
    @Expose
    private Integer idOrderMstStatus;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdContact() {
        return idContact;
    }

    public void setIdContact(Integer idContact) {
        this.idContact = idContact;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getContactGender() {
        return contactGender;
    }

    public void setContactGender(String contactGender) {
        this.contactGender = contactGender;
    }

    public Integer getIdOrderMstStatus() {
        return idOrderMstStatus;
    }

    public void setIdOrderMstStatus(Integer idOrderMstStatus) {
        this.idOrderMstStatus = idOrderMstStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
