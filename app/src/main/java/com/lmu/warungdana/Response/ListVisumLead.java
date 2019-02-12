package com.lmu.warungdana.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListVisumLead {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("lead_first_name")
    @Expose
    private String leadFirstName;
    @SerializedName("lead_last_name")
    @Expose
    private Object leadLastName;
    @SerializedName("mst_visum_status_status")
    @Expose
    private String mstVisumStatusStatus;
    @SerializedName("revisit")
    @Expose
    private String revisit;
    @SerializedName("id_cms_users")
    @Expose
    private int id_cms_users;
    @SerializedName("cms_users_name")
    @Expose
    private String cmsUsersName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("photo")
    @Expose
    private String photo;

    public ListVisumLead(Integer id, String leadFirstName, Object leadLastName, String mstVisumStatusStatus, String revisit, int id_cms_users, String cmsUsersName, String createdAt, String photo) {
        this.id = id;
        this.leadFirstName = leadFirstName;
        this.leadLastName = leadLastName;
        this.mstVisumStatusStatus = mstVisumStatusStatus;
        this.revisit = revisit;
        this.id_cms_users = id_cms_users;
        this.cmsUsersName = cmsUsersName;
        this.createdAt = createdAt;
        this.photo = photo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLeadFirstName() {
        return leadFirstName;
    }

    public void setLeadFirstName(String leadFirstName) {
        this.leadFirstName = leadFirstName;
    }

    public Object getLeadLastName() {
        return leadLastName;
    }

    public void setLeadLastName(Object leadLastName) {
        this.leadLastName = leadLastName;
    }

    public String getMstVisumStatusStatus() {
        return mstVisumStatusStatus;
    }

    public void setMstVisumStatusStatus(String mstVisumStatusStatus) {
        this.mstVisumStatusStatus = mstVisumStatusStatus;
    }

    public String getRevisit() {
        return revisit;
    }

    public void setRevisit(String revisit) {
        this.revisit = revisit;
    }

    public int getId_cms_users() {
        return id_cms_users;
    }

    public void setId_cms_users(int id_cms_users) {
        this.id_cms_users = id_cms_users;
    }

    public String getCmsUsersName() {
        return cmsUsersName;
    }

    public void setCmsUsersName(String cmsUsersName) {
        this.cmsUsersName = cmsUsersName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
