package com.lmu.warungdananew.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListLogDesc {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_mst_log_status")
    @Expose
    private Integer idMstLogStatus;
    @SerializedName("mst_log_status_status")
    @Expose
    private String mstLogStatusStatus;
    @SerializedName("description")
    @Expose
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdMstLogStatus() {
        return idMstLogStatus;
    }

    public void setIdMstLogStatus(Integer idMstLogStatus) {
        this.idMstLogStatus = idMstLogStatus;
    }

    public String getMstLogStatusStatus() {
        return mstLogStatusStatus;
    }

    public void setMstLogStatusStatus(String mstLogStatusStatus) {
        this.mstLogStatusStatus = mstLogStatusStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
