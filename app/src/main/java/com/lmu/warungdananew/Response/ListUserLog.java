package com.lmu.warungdananew.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListUserLog {

    public ListUserLog(Integer id, String cmsUsersName, Integer idModul, Integer idData, String jenis, String createdAt) {
        this.id = id;
        this.cmsUsersName = cmsUsersName;
        this.idModul = idModul;
        this.idData = idData;
        this.jenis = jenis;
        this.createdAt = createdAt;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cms_users_name")
    @Expose
    private String cmsUsersName;
    @SerializedName("id_modul")
    @Expose
    private Integer idModul;
    @SerializedName("id_data")
    @Expose
    private Integer idData;
    @SerializedName("jenis")
    @Expose
    private String jenis;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCmsUsersName() {
        return cmsUsersName;
    }

    public void setCmsUsersName(String cmsUsersName) {
        this.cmsUsersName = cmsUsersName;
    }

    public Integer getIdModul() {
        return idModul;
    }

    public void setIdModul(Integer idModul) {
        this.idModul = idModul;
    }

    public Integer getIdData() {
        return idData;
    }

    public void setIdData(Integer idData) {
        this.idData = idData;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
