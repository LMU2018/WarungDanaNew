package com.lmu.warungdananew.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOrder {

    public ListOrder(Integer id, Integer idcontact, String firstName, String lastName, String status, Integer plafond,
                     Integer idUnit, String model, String createdAt) {
        this.id = id;
        this.idContact = idcontact;
        this.contactFirstName = firstName;
        this.contactLastName = lastName;
        this.orderMstStatusStatus = status;
        this.plafond = plafond;
        this.idMstUnit = idUnit;
        this.model = model;
        this.createdAt = createdAt;

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
    @SerializedName("order_mst_status_status")
    @Expose
    private String orderMstStatusStatus;
    @SerializedName("plafond")
    @Expose
    private Integer plafond;
    @SerializedName("id_mst_unit")
    @Expose
    private Integer idMstUnit;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

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

    public String getOrderMstStatusStatus() {
        return orderMstStatusStatus;
    }

    public void setOrderMstStatusStatus(String orderMstStatusStatus) {
        this.orderMstStatusStatus = orderMstStatusStatus;
    }

    public Integer getPlafond() {
        return plafond;
    }

    public void setPlafond(Integer plafond) {
        this.plafond = plafond;
    }

    public Integer getIdMstUnit() {
        return idMstUnit;
    }

    public void setIdMstUnit(Integer idMstUnit) {
        this.idMstUnit = idMstUnit;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
