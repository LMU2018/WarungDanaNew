package com.lmu.warungdananew.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListJadwal {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_activity_schedule")
    @Expose
    private Integer idActivitySchedule;
    @SerializedName("activity_schedule_start_date")
    @Expose
    private String activityScheduleStartDate;
    @SerializedName("activity_schedule_end_date")
    @Expose
    private String activityScheduleEndDate;
    @SerializedName("activity_schedule_started")
    @Expose
    private String activityScheduleStarted;
    @SerializedName("activity_schedule_ended")
    @Expose
    private String activityScheduleEnded;
    @SerializedName("activity_schedule_note")
    @Expose
    private String activityScheduleNote;
    @SerializedName("id_activity_mst_type")
    @Expose
    private Integer idActivityMstType;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id_status")
    @Expose
    private Integer idStatus;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdActivitySchedule() {
        return idActivitySchedule;
    }

    public void setIdActivitySchedule(Integer idActivitySchedule) {
        this.idActivitySchedule = idActivitySchedule;
    }

    public String getActivityScheduleStartDate() {
        return activityScheduleStartDate;
    }

    public void setActivityScheduleStartDate(String activityScheduleStartDate) {
        this.activityScheduleStartDate = activityScheduleStartDate;
    }

    public String getActivityScheduleEndDate() {
        return activityScheduleEndDate;
    }

    public void setActivityScheduleEndDate(String activityScheduleEndDate) {
        this.activityScheduleEndDate = activityScheduleEndDate;
    }

    public String getActivityScheduleStarted() {
        return activityScheduleStarted;
    }

    public void setActivityScheduleStarted(String activityScheduleStarted) {
        this.activityScheduleStarted = activityScheduleStarted;
    }

    public String getActivityScheduleEnded() {
        return activityScheduleEnded;
    }

    public void setActivityScheduleEnded(String activityScheduleEnded) {
        this.activityScheduleEnded = activityScheduleEnded;
    }

    public String getActivityScheduleNote() {
        return activityScheduleNote;
    }

    public void setActivityScheduleNote(String activityScheduleNote) {
        this.activityScheduleNote = activityScheduleNote;
    }

    public Integer getIdActivityMstType() {
        return idActivityMstType;
    }

    public void setIdActivityMstType(Integer idActivityMstType) {
        this.idActivityMstType = idActivityMstType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
