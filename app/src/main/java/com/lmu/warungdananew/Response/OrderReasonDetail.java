package com.lmu.warungdananew.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderReasonDetail {

    @SerializedName("api_status")
    @Expose
    private Integer apiStatus;
    @SerializedName("api_message")
    @Expose
    private String apiMessage;
    @SerializedName("api_response_fields")
    @Expose
    private List<String> apiResponseFields = null;
    @SerializedName("api_authorization")
    @Expose
    private String apiAuthorization;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_order_mst_status")
    @Expose
    private Integer idOrderMstStatus;
    @SerializedName("order_mst_status_status")
    @Expose
    private String orderMstStatusStatus;
    @SerializedName("reason")
    @Expose
    private String reason;

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

    public List<String> getApiResponseFields() {
        return apiResponseFields;
    }

    public void setApiResponseFields(List<String> apiResponseFields) {
        this.apiResponseFields = apiResponseFields;
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

    public Integer getIdOrderMstStatus() {
        return idOrderMstStatus;
    }

    public void setIdOrderMstStatus(Integer idOrderMstStatus) {
        this.idOrderMstStatus = idOrderMstStatus;
    }

    public String getOrderMstStatusStatus() {
        return orderMstStatusStatus;
    }

    public void setOrderMstStatusStatus(String orderMstStatusStatus) {
        this.orderMstStatusStatus = orderMstStatusStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
