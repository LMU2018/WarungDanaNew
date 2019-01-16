package com.lmu.warungdananew.Response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class ListUnitUfi {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("mst_branch_branch_name")
    @Expose
    private String mstBranchBranchName;
    @SerializedName("year")
    @Expose
    private Integer year;
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
    @SerializedName("otr")
    @Expose
    private Integer otr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMstBranchBranchName() {
        return mstBranchBranchName;
    }

    public void setMstBranchBranchName(String mstBranchBranchName) {
        this.mstBranchBranchName = mstBranchBranchName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public Integer getOtr() {
        return otr;
    }

    public void setOtr(Integer otr) {
        this.otr = otr;
    }


}
