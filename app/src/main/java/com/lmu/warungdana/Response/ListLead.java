package com.lmu.warungdana.Response;


import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListLead implements Comparable<ListLead> {

//    public ListLead(Integer id, String firstName, String lastName, Integer idLeadMstStatus, String recall,
//                    Integer idMstLogDesc, Integer idMstLogStatus, String description, String status, Integer favorite) {
//
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.idLeadMstStatus = idLeadMstStatus;
//        this.recall = recall;
//        this.idMstLogDesc = idMstLogDesc;
//        this.idMstLogStatus = idMstLogStatus;
//        this.description = description;
//        this.status = status;
//        this.favorite = favorite;
//
//    }


    public ListLead(Integer id, String firstName, String lastName, Integer idLeadMstStatus, String recall, Integer idMstLogDesc, Integer idMstLogStatus, String description, String status, Integer favorite, String created_at_lead) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idLeadMstStatus = idLeadMstStatus;
        this.recall = recall;
        this.idMstLogDesc = idMstLogDesc;
        this.idMstLogStatus = idMstLogStatus;
        this.description = description;
        this.status = status;
        this.favorite = favorite;
        this.created_at_lead = created_at_lead;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("id_lead_mst_status")
    @Expose
    private Integer idLeadMstStatus;
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
    @SerializedName("favorite")
    @Expose
    private Integer favorite;
    @SerializedName("created_at_lead")
    @Expose
    private String created_at_lead;

    public String getCreated_at_lead() {
        return created_at_lead;
    }

    public void setCreated_at_lead(String created_at_lead) {
        this.created_at_lead = created_at_lead;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getIdLeadMstStatus() {
        return idLeadMstStatus;
    }

    public void setIdLeadMstStatus(Integer idLeadMstStatus) {
        this.idLeadMstStatus = idLeadMstStatus;
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

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }

    @Override
    public int compareTo(@NonNull ListLead o) {
        return getFirstName().compareToIgnoreCase(o.getFirstName());
    }

}
