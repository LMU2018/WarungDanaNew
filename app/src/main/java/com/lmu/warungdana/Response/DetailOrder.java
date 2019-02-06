package com.lmu.warungdana.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailOrder {

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
    @SerializedName("mst_data_source_datasource")
    @Expose
    private String mstDataSourceDatasource;
    @SerializedName("mst_outlet_outlet_name")
    @Expose
    private String mstOutletOutletName;
    @SerializedName("contact_first_name")
    @Expose
    private String contactFirstName;
    @SerializedName("contact_last_name")
    @Expose
    private String contactLastName;
    @SerializedName("mst_product_nama")
    @Expose
    private String mstProductNama;
    @SerializedName("no_order")
    @Expose
    private String noOrder;
    @SerializedName("order_mst_status_status")
    @Expose
    private String orderMstStatusStatus;
    @SerializedName("cms_users_name")
    @Expose
    private String cmsUsersName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("status_address")
    @Expose
    private String statusAddress;
    @SerializedName("survey")
    @Expose
    private String survey;
    @SerializedName("id_mst_cabang_fif")
    @Expose
    private Integer idMstCabangFif;
    @SerializedName("cabang_fif")
    @Expose
    private String cabangFif;
    @SerializedName("pos_fif")
    @Expose
    private String posFif;
    @SerializedName("api_http")
    @Expose
    private Integer apiHttp;
    @SerializedName("id_order_mst_reason")
    @Expose
    private Integer idOrderMstReason;

    public Integer getIdOrderMstReason() {
        return idOrderMstReason;
    }

    public void setIdOrderMstReason(Integer idOrderMstReason) {
        this.idOrderMstReason = idOrderMstReason;
    }

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

    public String getMstDataSourceDatasource() {
        return mstDataSourceDatasource;
    }

    public void setMstDataSourceDatasource(String mstDataSourceDatasource) {
        this.mstDataSourceDatasource = mstDataSourceDatasource;
    }

    public String getMstOutletOutletName() {
        return mstOutletOutletName;
    }

    public void setMstOutletOutletName(String mstOutletOutletName) {
        this.mstOutletOutletName = mstOutletOutletName;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public String getMstProductNama() {
        return mstProductNama;
    }

    public void setMstProductNama(String mstProductNama) {
        this.mstProductNama = mstProductNama;
    }

    public String getNoOrder() {
        return noOrder;
    }

    public void setNoOrder(String noOrder) {
        this.noOrder = noOrder;
    }

    public String getOrderMstStatusStatus() {
        return orderMstStatusStatus;
    }

    public void setOrderMstStatusStatus(String orderMstStatusStatus) {
        this.orderMstStatusStatus = orderMstStatusStatus;
    }

    public String getCmsUsersName() {
        return cmsUsersName;
    }

    public void setCmsUsersName(String cmsUsersName) {
        this.cmsUsersName = cmsUsersName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatusAddress() {
        return statusAddress;
    }

    public void setStatusAddress(String statusAddress) {
        this.statusAddress = statusAddress;
    }

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }

    public Integer getIdMstCabangFif() {
        return idMstCabangFif;
    }

    public void setIdMstCabangFif(Integer idMstCabangFif) {
        this.idMstCabangFif = idMstCabangFif;
    }

    public String getCabangFif() {
        return cabangFif;
    }

    public void setCabangFif(String cabangFif) {
        this.cabangFif = cabangFif;
    }

    public String getPosFif() {
        return posFif;
    }

    public void setPosFif(String posFif) {
        this.posFif = posFif;
    }

    public Integer getApiHttp() {
        return apiHttp;
    }

    public void setApiHttp(Integer apiHttp) {
        this.apiHttp = apiHttp;
    }

}
