package com.lmu.warungdananew.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListLogTarget {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("target_first_name")
    @Expose
    private String targetFirstName;
    @SerializedName("target_last_name")
    @Expose
    private Object targetLastName;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("id_mst_log_desc")
    @Expose
    private Integer idMstLogDesc;
    @SerializedName("mst_log_desc_description")
    @Expose
    private String mstLogDescDescription;
    @SerializedName("recall")
    @Expose
    private String recall;
    @SerializedName("cms_users_name")
    @Expose
    private String cmsUsersName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id_mst_log_status")
    @Expose
    private Integer idMstLogStatus;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTargetFirstName() {
        return targetFirstName;
    }

    public void setTargetFirstName(String targetFirstName) {
        this.targetFirstName = targetFirstName;
    }

    public Object getTargetLastName() {
        return targetLastName;
    }

    public void setTargetLastName(Object targetLastName) {
        this.targetLastName = targetLastName;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getIdMstLogDesc() {
        return idMstLogDesc;
    }

    public void setIdMstLogDesc(Integer idMstLogDesc) {
        this.idMstLogDesc = idMstLogDesc;
    }

    public String getMstLogDescDescription() {
        return mstLogDescDescription;
    }

    public void setMstLogDescDescription(String mstLogDescDescription) {
        this.mstLogDescDescription = mstLogDescDescription;
    }

    public String getRecall() {
        return recall;
    }

    public void setRecall(String recall) {
        this.recall = recall;
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

    public Integer getIdMstLogStatus() {
        return idMstLogStatus;
    }

    public void setIdMstLogStatus(Integer idMstLogStatus) {
        this.idMstLogStatus = idMstLogStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
