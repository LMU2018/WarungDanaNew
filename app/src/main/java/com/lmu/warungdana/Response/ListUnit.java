package com.lmu.warungdana.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListUnit {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_mst_product")
    @Expose
    private Integer idMstProduct;
    @SerializedName("mst_product_nama")
    @Expose
    private String mstProductNama;
    @SerializedName("mst_product_status")
    @Expose
    private String mstProductStatus;
    @SerializedName("id_mst_unit")
    @Expose
    private Integer idMstUnit;
    @SerializedName("nopol")
    @Expose
    private String nopol;
    @SerializedName("tax_status")
    @Expose
    private String taxStatus;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("kode_unit")
    @Expose
    private String kodeUnit;
    @SerializedName("merk")
    @Expose
    private String merk;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("otr")
    @Expose
    private Integer otr;
    @SerializedName("id_lead_product_detail")
    @Expose
    private Integer idLeadProductDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdMstProduct() {
        return idMstProduct;
    }

    public void setIdMstProduct(Integer idMstProduct) {
        this.idMstProduct = idMstProduct;
    }

    public String getMstProductNama() {
        return mstProductNama;
    }

    public void setMstProductNama(String mstProductNama) {
        this.mstProductNama = mstProductNama;
    }

    public String getMstProductStatus() {
        return mstProductStatus;
    }

    public void setMstProductStatus(String mstProductStatus) {
        this.mstProductStatus = mstProductStatus;
    }

    public Integer getIdMstUnit() {
        return idMstUnit;
    }

    public void setIdMstUnit(Integer idMstUnit) {
        this.idMstUnit = idMstUnit;
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

    public String getKodeUnit() {
        return kodeUnit;
    }

    public void setKodeUnit(String kodeUnit) {
        this.kodeUnit = kodeUnit;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getOtr() {
        return otr;
    }

    public void setOtr(Integer otr) {
        this.otr = otr;
    }

    public Integer getIdLeadProductDetail() {
        return idLeadProductDetail;
    }

    public void setIdLeadProductDetail(Integer idLeadProductDetail) {
        this.idLeadProductDetail = idLeadProductDetail;
    }

}
