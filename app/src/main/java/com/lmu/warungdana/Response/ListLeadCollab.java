package com.lmu.warungdana.Response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListLeadCollab {

    public ListLeadCollab(Integer id, Integer idLead, String leadFirstName, String leadLastName, String cmsUserName, String recall,
                          Integer idMstLogDesc, Integer idMstLogStatus, String description, String status) {

        this.id = id;
        this.idLead = idLead;
        this.leadFirstName = leadFirstName;
        this.leadLastName = leadLastName;
        this.cmsUsersName = cmsUserName;
        this.recall = recall;
        this.idMstLogDesc = idMstLogDesc;
        this.idMstLogStatus = idMstLogStatus;
        this.description = description;
        this.status = status;

    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_lead")
    @Expose
    private Integer idLead;
    @SerializedName("lead_first_name")
    @Expose
    private String leadFirstName;
    @SerializedName("lead_last_name")
    @Expose
    private String leadLastName;
    @SerializedName("cms_users_name")
    @Expose
    private String cmsUsersName;
    @SerializedName("recall")
    @Expose
    private String recall;
    @SerializedName("id_mst_log_desc")
    @Expose
    private Integer idMstLogDesc;
    @SerializedName("id_mst_log_status")
    @Expose
    private Integer idMstLogStatus;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdLead() {
        return idLead;
    }

    public void setIdLead(Integer idLead) {
        this.idLead = idLead;
    }

    public String getLeadFirstName() {
        return leadFirstName;
    }

    public void setLeadFirstName(String leadFirstName) {
        this.leadFirstName = leadFirstName;
    }

    public String getLeadLastName() {
        return leadLastName;
    }

    public void setLeadLastName(String leadLastName) {
        this.leadLastName = leadLastName;
    }

    public String getCmsUsersName() {
        return cmsUsersName;
    }

    public void setCmsUsersName(String cmsUsersName) {
        this.cmsUsersName = cmsUsersName;
    }

    public String getRecall() {
        return recall;
    }

    public void setRecall(String recall) {
        this.recall = recall;
    }

    public Integer getIdMstLogDesc() {
        return idMstLogDesc;
    }

    public void setIdMstLogDesc(Integer idMstLogDesc) {
        this.idMstLogDesc = idMstLogDesc;
    }

    public Integer getIdMstLogStatus() {
        return idMstLogStatus;
    }

    public void setIdMstLogStatus(Integer idMstLogStatus) {
        this.idMstLogStatus = idMstLogStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
