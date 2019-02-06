package com.lmu.warungdana.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListUserJadwal {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cms_users_name")
    @Expose
    private String cmsUsersName;
    @SerializedName("cms_users_status")
    @Expose
    private String cmsUsersStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCmsUsersName() {
        return cmsUsersName;
    }

    public void setCmsUsersName(String cmsUsersName) {
        this.cmsUsersName = cmsUsersName;
    }

    public String getCmsUsersStatus() {
        return cmsUsersStatus;
    }

    public void setCmsUsersStatus(String cmsUsersStatus) {
        this.cmsUsersStatus = cmsUsersStatus;
    }

}
