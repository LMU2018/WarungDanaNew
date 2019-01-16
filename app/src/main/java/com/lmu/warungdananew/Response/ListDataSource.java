package com.lmu.warungdananew.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListDataSource {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("datasource")
    @Expose
    private String datasource;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }
}
