package com.lmu.warungdana.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespKPICfa {
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
    @SerializedName("brosur_sum")
    @Expose
    private Integer brosurSum;
    @SerializedName("new_db_sum")
    @Expose
    private Integer newDbSum;
    @SerializedName("target_log_sum")
    @Expose
    private Integer targetLogSum;
    @SerializedName("order_sum")
    @Expose
    private Integer orderSum;
    @SerializedName("booking_sum")
    @Expose
    private Integer bookingSum;
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

    public Integer getBrosurSum() {
        return brosurSum;
    }

    public void setBrosurSum(Integer brosurSum) {
        this.brosurSum = brosurSum;
    }

    public Integer getNewDbSum() {
        return newDbSum;
    }

    public void setNewDbSum(Integer newDbSum) {
        this.newDbSum = newDbSum;
    }

    public Integer getTargetLogSum() {
        return targetLogSum;
    }

    public void setTargetLogSum(Integer targetLogSum) {
        this.targetLogSum = targetLogSum;
    }

    public Integer getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(Integer orderSum) {
        this.orderSum = orderSum;
    }

    public Integer getBookingSum() {
        return bookingSum;
    }

    public void setBookingSum(Integer bookingSum) {
        this.bookingSum = bookingSum;
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
