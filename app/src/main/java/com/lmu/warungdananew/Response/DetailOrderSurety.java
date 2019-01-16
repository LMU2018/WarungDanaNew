package com.lmu.warungdananew.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailOrderSurety {
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
    @SerializedName("birth_place")
    @Expose
    private String birthPlace;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("api_http")
    @Expose
    private Integer apiHttp;
    @SerializedName("id_mst_job")
    @Expose
    private Integer idMstJob;
    @SerializedName("mst_job_job")
    @Expose
    private String mstJobJob;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("id_contact_mst_status_employee")
    @Expose
    private Integer idContactMstStatusEmployee;
    @SerializedName("contact_mst_status_employee_status")
    @Expose
    private String contactMstStatusEmployeeStatus;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("working_time")
    @Expose
    private Integer workingTime;
    @SerializedName("income")
    @Expose
    private Integer income;

    @SerializedName("outlay")
    @Expose
    private Integer outlay;

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

    public Integer getApiHttp() {
        return apiHttp;
    }

    public void setApiHttp(Integer apiHttp) {
        this.apiHttp = apiHttp;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getIdContactMstStatusEmployee() {
        return idContactMstStatusEmployee;
    }

    public void setIdContactMstStatusEmployee(Integer idContactMstStatusEmployee) {
        this.idContactMstStatusEmployee = idContactMstStatusEmployee;
    }

    public String getContactMstStatusEmployeeStatus() {
        return contactMstStatusEmployeeStatus;
    }

    public void setContactMstStatusEmployeeStatus(String contactMstStatusEmployeeStatus) {
        this.contactMstStatusEmployeeStatus = contactMstStatusEmployeeStatus;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(Integer workingTime) {
        this.workingTime = workingTime;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public Integer getOutlay() {
        return outlay;
    }

    public void setOutlay(Integer outlay) {
        this.outlay = outlay;
    }

}
