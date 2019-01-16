package com.lmu.warungdananew.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Andre Al Farysie on 24/05/2018.
 */

public class Login {

    @SerializedName("api_status")
    @Expose
    private Integer apiStatus;
    @SerializedName("api_message")
    @Expose
    private String apiMessage;
    @SerializedName("api_authorization")
    @Expose
    private String apiAuthorization;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id_cms_privileges")
    @Expose
    private Integer idCmsPrivileges;
    @SerializedName("cms_privileges_name")
    @Expose
    private String cmsPrivilegesName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("npm")
    @Expose
    private String npm;
    @SerializedName("id_mst_outlet")
    @Expose
    private Integer idMstOutlet;
    @SerializedName("mst_outlet_outlet_name")
    @Expose
    private String mstOutletOutletName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("api_http")
    @Expose
    private Integer apiHttp;

    public Integer getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(Integer apiStatus) {
        this.apiStatus = apiStatus;
    }

    public String getApiMessage() {
        return apiMessage;
    }

    public void setApiMessage(String apiMessage) {
        this.apiMessage = apiMessage;
    }

    public String getApiAuthorization() {
        return apiAuthorization;
    }

    public void setApiAuthorization(String apiAuthorization) {
        this.apiAuthorization = apiAuthorization;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdCmsPrivileges() {
        return idCmsPrivileges;
    }

    public void setIdCmsPrivileges(Integer idCmsPrivileges) {
        this.idCmsPrivileges = idCmsPrivileges;
    }

    public String getCmsPrivilegesName() {
        return cmsPrivilegesName;
    }

    public void setCmsPrivilegesName(String cmsPrivilegesName) {
        this.cmsPrivilegesName = cmsPrivilegesName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public Integer getIdMstOutlet() {
        return idMstOutlet;
    }

    public void setIdMstOutlet(Integer idMstOutlet) {
        this.idMstOutlet = idMstOutlet;
    }

    public String getMstOutletOutletName() {
        return mstOutletOutletName;
    }

    public void setMstOutletOutletName(String mstOutletOutletName) {
        this.mstOutletOutletName = mstOutletOutletName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getApiHttp() {
        return apiHttp;
    }

    public void setApiHttp(Integer apiHttp) {
        this.apiHttp = apiHttp;
    }
}
