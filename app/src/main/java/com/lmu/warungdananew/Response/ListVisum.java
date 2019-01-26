package com.lmu.warungdananew.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListVisum {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("target_first_name")
    @Expose
    private String targetFirstName;
    @SerializedName("target_last_name")
    @Expose
    private Object targetLastName;
    @SerializedName("mst_visum_status_status")
    @Expose
    private String mstVisumStatusStatus;
    @SerializedName("revisit")
    @Expose
    private String revisit;
    @SerializedName("cms_users_name")
    @Expose
    private String cmsUsersName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("photo")
    @Expose
    private String photo;


    public ListVisum(Integer id, String targetFirstName, Object targetLastName, String mstVisumStatusStatus, String revisit, String cmsUsersName, String createdAt, String photo) {
        this.id = id;
        this.targetFirstName = targetFirstName;
        this.targetLastName = targetLastName;
        this.mstVisumStatusStatus = mstVisumStatusStatus;
        this.revisit = revisit;
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
