package com.lmu.warungdana.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListMarital {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
