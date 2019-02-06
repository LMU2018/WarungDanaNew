package com.lmu.warungdana.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CounterBrosur {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("brosur")
    @Expose
    private Integer brosur;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrosur() {
        return brosur;
    }

    public void setBrosur(Integer brosur) {
        this.brosur = brosur;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
