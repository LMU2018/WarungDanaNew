package com.lmu.warungdananew.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gigabyte on 10/1/2018.
 */

public class DetailContactAddress {

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
    @SerializedName("id_contact")
    @Expose
    private Integer idContact;
    @SerializedName("contact_nik")
    @Expose
    private String contactNik;
    @SerializedName("contact_first_name")
    @Expose
    private String contactFirstName;
    @SerializedName("contact_last_name")
    @Expose
    private String contactLastName;
    @SerializedName("contact_birth_place")
    @Expose
    private String contactBirthPlace;
    @SerializedName("contact_birth_date")
    @Expose
    private String contactBirthDate;
    @SerializedName("contact_gender")
    @Expose
    private String contactGender;
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
    @SerializedName("mst_category_address_category")
    @Expose
    private String mstCategoryAddressCategory;
    @SerializedName("id_cms_users")
    @Expose
    private Integer idCmsUsers;
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

    public Integer getIdContact() {
        return idContact;
    }

    public void setIdContact(Integer idContact) {
        this.idContact = idContact;
    }

    public String getContactNik() {
        return contactNik;
    }

    public void setContactNik(String contactNik) {
        this.contactNik = contactNik;
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

    public String getContactBirthPlace() {
        return contactBirthPlace;
    }

    public void setContactBirthPlace(String contactBirthPlace) {
        this.contactBirthPlace = contactBirthPlace;
    }

    public String getContactBirthDate() {
        return contactBirthDate;
    }

    public void setContactBirthDate(String contactBirthDate) {
        this.contactBirthDate = contactBirthDate;
    }

    public String getContactGender() {
        return contactGender;
    }

    public void setContactGender(String contactGender) {
        this.contactGender = contactGender;
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

    public String getMstCategoryAddressCategory() {
        return mstCategoryAddressCategory;
    }

    public void setMstCategoryAddressCategory(String mstCategoryAddressCategory) {
        this.mstCategoryAddressCategory = mstCategoryAddressCategory;
    }

    public Integer getIdCmsUsers() {
        return idCmsUsers;
    }

    public void setIdCmsUsers(Integer idCmsUsers) {
        this.idCmsUsers = idCmsUsers;
    }

    public Integer getApiHttp() {
        return apiHttp;
    }

    public void setApiHttp(Integer apiHttp) {
        this.apiHttp = apiHttp;
    }

}
