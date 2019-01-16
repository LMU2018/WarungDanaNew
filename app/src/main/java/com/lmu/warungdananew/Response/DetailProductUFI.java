package com.lmu.warungdananew.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailProductUFI {

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
    @SerializedName("id_order")
    @Expose
    private Integer idOrder;
    @SerializedName("mst_unit_year")
    @Expose
    private Integer mstUnitYear;
    @SerializedName("mst_unit_kode_unit")
    @Expose
    private String mstUnitKodeUnit;
    @SerializedName("mst_unit_merk")
    @Expose
    private String mstUnitMerk;
    @SerializedName("mst_unit_type")
    @Expose
    private String mstUnitType;
    @SerializedName("mst_unit_model")
    @Expose
    private String mstUnitModel;
    @SerializedName("mst_unit_otr")
    @Expose
    private Integer mstUnitOtr;
    @SerializedName("nopol")
    @Expose
    private String nopol;
    @SerializedName("tax_status")
    @Expose
    private String taxStatus;
    @SerializedName("owner")
    @Expose
    private String owner;
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

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Integer getMstUnitYear() {
        return mstUnitYear;
    }

    public void setMstUnitYear(Integer mstUnitYear) {
        this.mstUnitYear = mstUnitYear;
    }

    public String getMstUnitKodeUnit() {
        return mstUnitKodeUnit;
    }

    public void setMstUnitKodeUnit(String mstUnitKodeUnit) {
        this.mstUnitKodeUnit = mstUnitKodeUnit;
    }

    public String getMstUnitMerk() {
        return mstUnitMerk;
    }

    public void setMstUnitMerk(String mstUnitMerk) {
        this.mstUnitMerk = mstUnitMerk;
    }

    public String getMstUnitType() {
        return mstUnitType;
    }

    public void setMstUnitType(String mstUnitType) {
        this.mstUnitType = mstUnitType;
    }

    public String getMstUnitModel() {
        return mstUnitModel;
    }

    public void setMstUnitModel(String mstUnitModel) {
        this.mstUnitModel = mstUnitModel;
    }

    public Integer getMstUnitOtr() {
        return mstUnitOtr;
    }

    public void setMstUnitOtr(Integer mstUnitOtr) {
        this.mstUnitOtr = mstUnitOtr;
    }

    public String getNopol() {
        return nopol;
    }

    public void setNopol(String nopol) {
        this.nopol = nopol;
    }

    public String getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(String taxStatus) {
        this.taxStatus = taxStatus;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getApiHttp() {
        return apiHttp;
    }

    public void setApiHttp(Integer apiHttp) {
        this.apiHttp = apiHttp;
    }
}
