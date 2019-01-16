package com.lmu.warungdananew.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListAddress {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("id_mst_address")
    @Expose
    private Integer idMstAddress;
    @SerializedName("mst_address_kelurahan")
    @Expose
    private String mstAddressKelurahan;
    @SerializedName("mst_address_kecamatan")
    @Expose
    private String mstAddressKecamatan;
    @SerializedName("mst_address_kabupaten")
    @Expose
    private String mstAddressKabupaten;
    @SerializedName("mst_address_provinsi")
    @Expose
    private String mstAddressProvinsi;
    @SerializedName("mst_address_kodepos")
    @Expose
    private String mstAddressKodepos;
    @SerializedName("id_mst_category_address")
    @Expose
    private Integer idMstCategoryAddress;
    @SerializedName("mst_category_address_category")
    @Expose
    private String mstCategoryAddressCategory;
    @SerializedName("rt")
    @Expose
    private String rt;
    @SerializedName("rw")
    @Expose
    private String rw;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIdMstAddress() {
        return idMstAddress;
    }

    public void setIdMstAddress(Integer idMstAddress) {
        this.idMstAddress = idMstAddress;
    }

    public String getMstAddressKelurahan() {
        return mstAddressKelurahan;
    }

    public void setMstAddressKelurahan(String mstAddressKelurahan) {
        this.mstAddressKelurahan = mstAddressKelurahan;
    }

    public String getMstAddressKecamatan() {
        return mstAddressKecamatan;
    }

    public void setMstAddressKecamatan(String mstAddressKecamatan) {
        this.mstAddressKecamatan = mstAddressKecamatan;
    }

    public String getMstAddressKabupaten() {
        return mstAddressKabupaten;
    }

    public void setMstAddressKabupaten(String mstAddressKabupaten) {
        this.mstAddressKabupaten = mstAddressKabupaten;
    }

    public String getMstAddressProvinsi() {
        return mstAddressProvinsi;
    }

    public void setMstAddressProvinsi(String mstAddressProvinsi) {
        this.mstAddressProvinsi = mstAddressProvinsi;
    }

    public String getMstAddressKodepos() {
        return mstAddressKodepos;
    }

    public void setMstAddressKodepos(String mstAddressKodepos) {
        this.mstAddressKodepos = mstAddressKodepos;
    }

    public Integer getIdMstCategoryAddress() {
        return idMstCategoryAddress;
    }

    public void setIdMstCategoryAddress(Integer idMstCategoryAddress) {
        this.idMstCategoryAddress = idMstCategoryAddress;
    }

    public String getMstCategoryAddressCategory() {
        return mstCategoryAddressCategory;
    }

    public void setMstCategoryAddressCategory(String mstCategoryAddressCategory) {
        this.mstCategoryAddressCategory = mstCategoryAddressCategory;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }

}
