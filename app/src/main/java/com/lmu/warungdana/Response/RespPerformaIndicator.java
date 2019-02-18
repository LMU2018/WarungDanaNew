package com.lmu.warungdana.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RespPerformaIndicator {

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
    @SerializedName("count_teleBlnKemarin")
    @Expose
    private Integer countTeleBlnKemarin;
    @SerializedName("count_newDBBlnKemarin")
    @Expose
    private Integer countNewDBBlnKemarin;
    @SerializedName("count_orderBulanKemarin")
    @Expose
    private Integer countOrderBulanKemarin;
    @SerializedName("count_bookingBulanKemarin")
    @Expose
    private Integer countBookingBulanKemarin;
    @SerializedName("count_teleTotal")
    @Expose
    private Integer countTeleTotal;
    @SerializedName("count_newDBTotal")
    @Expose
    private Integer countNewDBTotal;
    @SerializedName("count_orderTotal")
    @Expose
    private Integer countOrderTotal;
    @SerializedName("count_bookingTotal")
    @Expose
    private Integer countBookingTotal;
    @SerializedName("count_brosurBulanKemarin")
    @Expose
    private Integer countBrosurBulanKemarin;
    @SerializedName("count_brosurBulanTotal")
    @Expose
    private Integer countBrosurBulanTotal;

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

    public Integer getCountTeleBlnKemarin() {
        return countTeleBlnKemarin;
    }

    public void setCountTeleBlnKemarin(Integer countTeleBlnKemarin) {
        this.countTeleBlnKemarin = countTeleBlnKemarin;
    }

    public Integer getCountNewDBBlnKemarin() {
        return countNewDBBlnKemarin;
    }

    public void setCountNewDBBlnKemarin(Integer countNewDBBlnKemarin) {
        this.countNewDBBlnKemarin = countNewDBBlnKemarin;
    }

    public Integer getCountOrderBulanKemarin() {
        return countOrderBulanKemarin;
    }

    public void setCountOrderBulanKemarin(Integer countOrderBulanKemarin) {
        this.countOrderBulanKemarin = countOrderBulanKemarin;
    }

    public Integer getCountBookingBulanKemarin() {
        return countBookingBulanKemarin;
    }

    public void setCountBookingBulanKemarin(Integer countBookingBulanKemarin) {
        this.countBookingBulanKemarin = countBookingBulanKemarin;
    }

    public Integer getCountTeleTotal() {
        return countTeleTotal;
    }

    public void setCountTeleTotal(Integer countTeleTotal) {
        this.countTeleTotal = countTeleTotal;
    }

    public Integer getCountNewDBTotal() {
        return countNewDBTotal;
    }

    public void setCountNewDBTotal(Integer countNewDBTotal) {
        this.countNewDBTotal = countNewDBTotal;
    }

    public Integer getCountOrderTotal() {
        return countOrderTotal;
    }

    public void setCountOrderTotal(Integer countOrderTotal) {
        this.countOrderTotal = countOrderTotal;
    }

    public Integer getCountBookingTotal() {
        return countBookingTotal;
    }

    public void setCountBookingTotal(Integer countBookingTotal) {
        this.countBookingTotal = countBookingTotal;
    }

    public Integer getCountBrosurBulanKemarin() {
        return countBrosurBulanKemarin;
    }

    public void setCountBrosurBulanKemarin(Integer countBrosurBulanKemarin) {
        this.countBrosurBulanKemarin = countBrosurBulanKemarin;
    }

    public Integer getCountBrosurBulanTotal() {
        return countBrosurBulanTotal;
    }

    public void setCountBrosurBulanTotal(Integer countBrosurBulanTotal) {
        this.countBrosurBulanTotal = countBrosurBulanTotal;
    }
}
