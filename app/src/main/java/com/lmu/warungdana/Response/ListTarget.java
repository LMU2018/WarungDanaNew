package com.lmu.warungdana.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListTarget {

//    public ListTarget(Integer id, Integer idTargetMstStatus, String category, String firstName, String lastName, String recall, Integer idMstLogDesc, Integer idMstLogStatus, String description, String status, Integer idMstVisumStatus, String revisit, String visitStatus, String created_at_target_log) {
//        this.id = id;
//        this.idTargetMstStatus = idTargetMstStatus;
//        this.category = category;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.recall = recall;
//        this.idMstLogDesc = idMstLogDesc;
//        this.idMstLogStatus = idMstLogStatus;
//        this.description = description;
//        this.status = status;
//        this.idMstVisumStatus = idMstVisumStatus;
//        this.revisit = revisit;
//        this.visitStatus = visitStatus;
//        this.created_at_target_log = created_at_target_log;
//    }
//
//    public ListTarget(Integer id, Integer idTargetMstStatus, String category, String firstName, String lastName,
//                      String recall, Integer idMstLogDesc, Integer idMstLogStatus, String description, String status,
//                      Integer idMstVisumStatus, String revisit, String visitStatus) {
//        this.id = id;
//        this.idTargetMstStatus = idTargetMstStatus;
//        this.category = category;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.recall = recall;
//        this.idMstLogDesc = idMstLogDesc;
//        this.idMstLogStatus = idMstLogStatus;
//        this.description = description;
//        this.status = status;
//        this.idMstVisumStatus = idMstVisumStatus;
//        this.revisit = revisit;
//        this.visitStatus = visitStatus;
//
//    }


    public ListTarget(Integer id, Integer idTargetMstStatus, String category, String firstName, String lastName, String updated_by, String recall, Integer idMstLogDesc, Integer idMstLogStatus, String description, String status, Integer idMstVisumStatus, String revisit, String visitStatus, String created_at_target_log) {
        this.id = id;
        this.idTargetMstStatus = idTargetMstStatus;
        this.category = category;
        this.firstName = firstName;
        this.lastName = lastName;
        this.updated_by = updated_by;
        this.recall = recall;
        this.idMstLogDesc = idMstLogDesc;
        this.idMstLogStatus = idMstLogStatus;
        this.description = description;
        this.status = status;
        this.idMstVisumStatus = idMstVisumStatus;
        this.revisit = revisit;
        this.visitStatus = visitStatus;
        this.created_at_target_log = created_at_target_log;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_target_mst_status")
    @Expose
    private Integer idTargetMstStatus;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("updated_by")
    @Expose
    private String updated_by;
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
    @SerializedName("created_at_target_log")
    @Expose
    private String created_at_target_log;

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getCreated_at_target_log() {
        return created_at_target_log;
    }

    public void setCreated_at_target_log(String created_at_target_log) {
        this.created_at_target_log = created_at_target_log;
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
}
