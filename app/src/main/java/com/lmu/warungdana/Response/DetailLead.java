package com.lmu.warungdana.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailLead {

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
    @SerializedName("id_mst_outlet")
    @Expose
    private Integer idMstOutlet;
    @SerializedName("mst_outlet_outlet_name")
    @Expose
    private String mstOutletOutletName;
    @SerializedName("id_mst_data_source")
    @Expose
    private Integer idMstDataSource;
    @SerializedName("mst_data_source_datasource")
    @Expose
    private String mstDataSourceDatasource;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("id_mst_job")
    @Expose
    private Integer idMstJob;
    @SerializedName("mst_job_job")
    @Expose
    private String mstJobJob;
    @SerializedName("id_lead_mst_status")
    @Expose
    private Integer idLeadMstStatus;
    @SerializedName("lead_mst_status_status")
    @Expose
    private String leadMstStatusStatus;
    @SerializedName("cms_users_name")
    @Expose
    private String cmsUsersName;
    @SerializedName("id_mst_log_desc")
    @Expose
    private Integer idMstLogDesc;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("id_mst_log_status")
    @Expose
    private Integer idMstLogStatus;
    @SerializedName("log_total")
    @Expose
    private String logTotal;
    @SerializedName("note_total")
    @Expose
    private String noteTotal;
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

    public Integer getIdMstDataSource() {
        return idMstDataSource;
    }

    public void setIdMstDataSource(Integer idMstDataSource) {
        this.idMstDataSource = idMstDataSource;
    }

    public String getMstDataSourceDatasource() {
        return mstDataSourceDatasource;
    }

    public void setMstDataSourceDatasource(String mstDataSourceDatasource) {
        this.mstDataSourceDatasource = mstDataSourceDatasource;
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

    public Integer getIdMstJob() {
        return idMstJob;
    }

    public void setIdMstJob(Integer idMstJob) {
        this.idMstJob = idMstJob;
    }

    public String getMstJobJob() {
        return mstJobJob;
    }

    public void setMstJobJob(String mstJobJob) {
        this.mstJobJob = mstJobJob;
    }

    public Integer getIdLeadMstStatus() {
        return idLeadMstStatus;
    }

    public void setIdLeadMstStatus(Integer idLeadMstStatus) {
        this.idLeadMstStatus = idLeadMstStatus;
    }

    public String getLeadMstStatusStatus() {
        return leadMstStatusStatus;
    }

    public void setLeadMstStatusStatus(String leadMstStatusStatus) {
        this.leadMstStatusStatus = leadMstStatusStatus;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIdMstLogStatus() {
        return idMstLogStatus;
    }

    public void setIdMstLogStatus(Integer idMstLogStatus) {
        this.idMstLogStatus = idMstLogStatus;
    }

    public String getLogTotal() {
        return logTotal;
    }

    public void setLogTotal(String logTotal) {
        this.logTotal = logTotal;
    }

    public String getNoteTotal() {
        return noteTotal;
    }

    public void setNoteTotal(String noteTotal) {
        this.noteTotal = noteTotal;
    }

    public Integer getApiHttp() {
        return apiHttp;
    }

    public void setApiHttp(Integer apiHttp) {
        this.apiHttp = apiHttp;
    }

}
