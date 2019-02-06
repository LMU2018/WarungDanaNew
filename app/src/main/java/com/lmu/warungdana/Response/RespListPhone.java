package com.lmu.warungdana.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RespListPhone {
    @SerializedName("api_status")
    @Expose
    private Integer apiStatus;
    @SerializedName("api_message")
    @Expose
    private String apiMessage;
    @SerializedName("api_authorization")
    @Expose
    private String apiAuthorization;
    @SerializedName("data")
    @Expose
    private List<ListPhone> data = null;
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

    public List<ListPhone> getData() {
        return data;
    }

    public void setData(List<ListPhone> data) {
        this.data = data;
    }

    public Integer getApiHttp() {
        return apiHttp;
    }

    public void setApiHttp(Integer apiHttp) {
        this.apiHttp = apiHttp;
    }
}
