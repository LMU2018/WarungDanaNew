package com.lmu.warungdana.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListTargetVisit {

    @SerializedName("id_target")
    @Expose
    private Integer idTarget;
    @SerializedName("target_category")
    @Expose
    private String targetCategory;
    @SerializedName("target_first_name")
    @Expose
    private String targetFirstName;
    @SerializedName("target_last_name")
    @Expose
    private String targetLastName;
    @SerializedName("target_updated_by")
    @Expose
    private Integer targetUpdatedBy;
    @SerializedName("id_mst_visum_status")
    @Expose
    private Integer idMstVisumStatus;
    @SerializedName("revisit")
    @Expose
    private String revisit;
    @SerializedName("cms_users_npm")
    @Expose
    private String cmsUsersNpm;
    @SerializedName("cms_users_name")
    @Expose
    private String cmsUsersName;
    @SerializedName("id_mst_log_desc")
    @Expose
    private Integer idMstLogDesc;
    @SerializedName("recall")
    @Expose
    private String recall;
    @SerializedName("id_mst_log_status")
    @Expose
    private Integer idMstLogStatus;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("visit_status")
    @Expose
    private String visitStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("created_at_target_log")
    @Expose
    private String createdAtTargetLog;
    @SerializedName("id_target_mst_status")
    @Expose
    private Integer idTargetMstStatus;

    public ListTargetVisit(Integer idTarget, String targetCategory, String targetFirstName, String targetLastName, Integer targetUpdatedBy, Integer idMstVisumStatus, String revisit, String cmsUsersNpm, String cmsUsersName, Integer idMstLogDesc, String recall, Integer idMstLogStatus, String description, String status, String visitStatus, String createdAt, String createdAtTargetLog, Integer idTargetMstStatus) {
        this.idTarget = idTarget;
        this.targetCategory = targetCategory;
        this.targetFirstName = targetFirstName;
        this.targetLastName = targetLastName;
        this.targetUpdatedBy = targetUpdatedBy;
        this.idMstVisumStatus = idMstVisumStatus;
        this.revisit = revisit;
        this.cmsUsersNpm = cmsUsersNpm;
        this.cmsUsersName = cmsUsersName;
        this.idMstLogDesc = idMstLogDesc;
        this.recall = recall;
        this.idMstLogStatus = idMstLogStatus;
        this.description = description;
        this.status = status;
        this.visitStatus = visitStatus;
        this.createdAt = createdAt;
        this.createdAtTargetLog = createdAtTargetLog;
        this.idTargetMstStatus = idTargetMstStatus;
    }

    public Integer getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(Integer idTarget) {
        this.idTarget = idTarget;
    }

    public String getTargetCategory() {
        return targetCategory;
    }

    public void setTargetCategory(String targetCategory) {
        this.targetCategory = targetCategory;
    }

    public String getTargetFirstName() {
        return targetFirstName;
    }

    public void setTargetFirstName(String targetFirstName) {
        this.targetFirstName = targetFirstName;
    }

    public String getTargetLastName() {
        return targetLastName;
    }

    public void setTargetLastName(String targetLastName) {
        this.targetLastName = targetLastName;
    }

    public Integer getTargetUpdatedBy() {
        return targetUpdatedBy;
    }

    public void setTargetUpdatedBy(Integer targetUpdatedBy) {
        this.targetUpdatedBy = targetUpdatedBy;
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

    public Integer getIdMstLogDesc() {
        return idMstLogDesc;
    }

    public void setIdMstLogDesc(Integer idMstLogDesc) {
        this.idMstLogDesc = idMstLogDesc;
    }

    public String getRecall() {
        return recall;
    }

    public void setRecall(String recall) {
        this.recall = recall;
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

    public String getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(String visitStatus) {
        this.visitStatus = visitStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAtTargetLog() {
        return createdAtTargetLog;
    }

    public void setCreatedAtTargetLog(String createdAtTargetLog) {
        this.createdAtTargetLog = createdAtTargetLog;
    }

    public Integer getIdTargetMstStatus() {
        return idTargetMstStatus;
    }

    public void setIdTargetMstStatus(Integer idTargetMstStatus) {
        this.idTargetMstStatus = idTargetMstStatus;
    }
}
