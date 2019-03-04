package com.lmu.warungdana.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespRekapActivity {

    @SerializedName("api_status")
    @Expose
    private Integer apiStatus;
    @SerializedName("api_message")
    @Expose
    private String apiMessage;
    @SerializedName("api_authorization")
    @Expose
    private String apiAuthorization;
    @SerializedName("count_brosur")
    @Expose
    private Integer countBrosur;
    @SerializedName("count_tele")
    @Expose
    private Integer countTele;
    @SerializedName("count_newDB")
    @Expose
    private Integer countNewDB;
    @SerializedName("count_order")
    @Expose
    private Integer countOrder;
    @SerializedName("count_booking")
    @Expose
    private Integer countBooking;
    @SerializedName("count_order_outlet")
    @Expose
    private Integer countOrderOutlet;
    @SerializedName("count_booking_outlet")
    @Expose
    private Integer countBookingOutlet;

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

    public Integer getCountBrosur() {
        return countBrosur;
    }

    public void setCountBrosur(Integer countBrosur) {
        this.countBrosur = countBrosur;
    }

    public Integer getCountTele() {
        return countTele;
    }

    public void setCountTele(Integer countTele) {
        this.countTele = countTele;
    }

    public Integer getCountNewDB() {
        return countNewDB;
    }

    public void setCountNewDB(Integer countNewDB) {
        this.countNewDB = countNewDB;
    }

    public Integer getCountOrder() {
        return countOrder;
    }

    public void setCountOrder(Integer countOrder) {
        this.countOrder = countOrder;
    }

    public Integer getCountBooking() {
        return countBooking;
    }

    public void setCountBooking(Integer countBooking) {
        this.countBooking = countBooking;
    }

    public Integer getCountOrderOutlet() {
        return countOrderOutlet;
    }

    public void setCountOrderOutlet(Integer countOrderOutlet) {
        this.countOrderOutlet = countOrderOutlet;
    }

    public Integer getCountBookingOutlet() {
        return countBookingOutlet;
    }

    public void setCountBookingOutlet(Integer countBookingOutlet) {
        this.countBookingOutlet = countBookingOutlet;
    }
}
