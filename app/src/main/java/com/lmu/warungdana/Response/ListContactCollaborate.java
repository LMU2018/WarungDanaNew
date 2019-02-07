package com.lmu.warungdana.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListContactCollaborate {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cms_users_npm")
    @Expose
    private String cmsUsersNpm;
    @SerializedName("cms_users_name")
    @Expose
    private String cmsUsersName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCmsUsersNpm() {
        return cmsUsersNpm;
    }

    public void setCmsUsersNpm(String cmsUsersNpm) {
        this.cmsUsersNpm = cmsUsersNpm;
    }

    public String getCmsUsersName() {
        return cmsUsersName;
    }

    public void setCmsUsersName(String cmsUsersName) {
        this.cmsUsersName = cmsUsersName;
    }
}
