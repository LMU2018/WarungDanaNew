package com.lmu.warungdana.Response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Gigabyte on 11/28/2018.
 */

public class RespListCabangFif {

    @SerializedName("api_status")
    @Expose
    private Integer apiStatus;
    @SerializedName("api_message")
    @Expose
    private String apiMessage;
    @SerializedName("api_authorization")
    @Expose
    private String apiAuthorization;
    @SerializedName("data")
    @Expose
    private List<ListCabangFif> data = null;

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

    public List<ListCabangFif> getData() {
        return data;
    }

    public void setData(List<ListCabangFif> data) {
        this.data = data;
    }

}
