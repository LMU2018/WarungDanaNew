package com.lmu.warungdana.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailContact {


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
    @SerializedName("nik")
    @Expose
    private String nik;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("birth_place")
    @Expose
    private String birthPlace;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("mst_religion_agama")
    @Expose
    private String mstReligionAgama;
    @SerializedName("contact_mst_status_marital_status")
    @Expose
    private String contactMstStatusMaritalStatus;
    @SerializedName("mst_job_job")
    @Expose
    private String mstJobJob;
    @SerializedName("cms_users_name")
    @Expose
    private String cmsUsersName;
    @SerializedName("id_order_mst_status")
    @Expose
    private Integer idOrderMstStatus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("potential_order")
    @Expose
    private Integer potentialOrder;
    @SerializedName("won_order")
    @Expose
    private Integer wonOrder;
    @SerializedName("api_http")
    @Expose
    private Integer apiHttp;
    @SerializedName("id_mst_data_source")
    @Expose
    private Integer idMstDataSource;
    @SerializedName("mst_data_source_datasource")
    @Expose
    private String mstDataSourceDatasource;

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

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
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

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMstReligionAgama() {
        return mstReligionAgama;
    }

    public void setMstReligionAgama(String mstReligionAgama) {
        this.mstReligionAgama = mstReligionAgama;
    }

    public String getContactMstStatusMaritalStatus() {
        return contactMstStatusMaritalStatus;
    }

    public void setContactMstStatusMaritalStatus(String contactMstStatusMaritalStatus) {
        this.contactMstStatusMaritalStatus = contactMstStatusMaritalStatus;
    }

    public String getMstJobJob() {
        return mstJobJob;
    }

    public void setMstJobJob(String mstJobJob) {
        this.mstJobJob = mstJobJob;
    }

    public String getCmsUsersName() {
        return cmsUsersName;
    }

    public void setCmsUsersName(String cmsUsersName) {
        this.cmsUsersName = cmsUsersName;
    }

    public Integer getIdOrderMstStatus() {
        return idOrderMstStatus;
    }

    public void setIdOrderMstStatus(Integer idOrderMstStatus) {
        this.idOrderMstStatus = idOrderMstStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPotentialOrder() {
        return potentialOrder;
    }

    public void setPotentialOrder(Integer potentialOrder) {
        this.potentialOrder = potentialOrder;
    }

    public Integer getWonOrder() {
        return wonOrder;
    }

    public void setWonOrder(Integer wonOrder) {
        this.wonOrder = wonOrder;
    }

    public Integer getApiHttp() {
        return apiHttp;
    }

    public void setApiHttp(Integer apiHttp) {
        this.apiHttp = apiHttp;
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

}
