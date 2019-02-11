package com.lmu.warungdana.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListTargetVisit {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_target_mst_status")
    @Expose
    private Integer idTargetMstStatus;
    @SerializedName("target_mst_status_status")
    @Expose
    private String targetMstStatusStatus;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("cms_users_npm")
    @Expose
    private String cmsUsersNpm;
    @SerializedName("cms_users_name")
    @Expose
    private String cmsUsersName;
    @SerializedName("updated_by")
    @Expose
    private Integer updatedBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
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
    @SerializedName("id_mst_visum_status")
    @Expose
    private Integer idMstVisumStatus;
    @SerializedName("revisit")
    @Expose
    private String revisit;
    @SerializedName("visit_status")
    @Expose
    private String visitStatus;
    @SerializedName("created_at_target_visum")
    @Expose
    private String createdAtTargetVisum;
    @SerializedName("created_at_target_log")
    @Expose
    private String createdAtTargetLog;

    public ListTargetVisit(Integer id, Integer idTargetMstStatus, String targetMstStatusStatus, String category, String firstName, String lastName, String cmsUsersNpm, String cmsUsersName, Integer updatedBy, String createdAt, String recall, Integer idMstLogDesc, Integer idMstLogStatus, String description, String status, Integer idMstVisumStatus, String revisit, String visitStatus, String createdAtTargetVisum, String createdAtTargetLog) {
        this.id = id;
        this.idTargetMstStatus = idTargetMstStatus;
        this.targetMstStatusStatus = targetMstStatusStatus;
        this.category = category;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cmsUsersNpm = cmsUsersNpm;
        this.cmsUsersName = cmsUsersName;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.recall = recall;
        this.idMstLogDesc = idMstLogDesc;
        this.idMstLogStatus = idMstLogStatus;
        this.description = description;
        this.status = status;
        this.idMstVisumStatus = idMstVisumStatus;
        this.revisit = revisit;
        this.visitStatus = visitStatus;
        this.createdAtTargetVisum = createdAtTargetVisum;
        this.createdAtTargetLog = createdAtTargetLog;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTargetMstStatus() {
        return idTargetMstStatus;
    }

    public void setIdTargetMstStatus(Integer idTargetMstStatus) {
        this.idTargetMstStatus = idTargetMstStatus;
    }

    public String getTargetMstStatusStatus() {
        return targetMstStatusStatus;
    }

    public void setTargetMstStatusStatus(String targetMstStatusStatus) {
        this.targetMstStatusStatus = targetMstStatusStatus;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCmsUsersNpm() {
        return cmsUsersNpm;
    }

    public void setCmsUsersNpm(String cmsUsersNpm) {
        this.cmsUsersNpm = cmsUsersNpm;
    }

    public String getCmsUsersName() {
        return cmsUsersName;
    }

    public void setCmsUsersName(String cmsUsersName) {
        this.cmsUsersName = cmsUsersName;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public Integer getIdMstVisumStatus() {
        return idMstVisumStatus;
    }

    public void setIdMstVisumStatus(Integer idMstVisumStatus) {
        this.idMstVisumStatus = idMstVisumStatus;
    }

    public String getRevisit() {
        return revisit;
    }

    public void setRevisit(String revisit) {
        this.revisit = revisit;
    }

    public String getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(String visitStatus) {
        this.visitStatus = visitStatus;
    }

    public String getCreatedAtTargetVisum() {
        return createdAtTargetVisum;
    }

    public void setCreatedAtTargetVisum(String createdAtTargetVisum) {
        this.createdAtTargetVisum = createdAtTargetVisum;
    }

    public String getCreatedAtTargetLog() {
        return createdAtTargetLog;
    }

    public void setCreatedAtTargetLog(String createdAtTargetLog) {
        this.createdAtTargetLog = createdAtTargetLog;
    }
}
