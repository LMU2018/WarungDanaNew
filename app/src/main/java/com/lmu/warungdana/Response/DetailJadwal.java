package com.lmu.warungdana.Response;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

        import java.util.List;

public class DetailJadwal {


    @SerializedName("api_status")
    @Expose
    private Integer apiStatus;
    @SerializedName("api_message")
    @Expose
    private String apiMessage;
    @SerializedName("api_response_fields")
    @Expose
    private List<String> apiResponseFields = null;
    @SerializedName("api_authorization")
    @Expose
    private String apiAuthorization;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_activity")
    @Expose
    private Integer idActivity;
    @SerializedName("activity_location")
    @Expose
    private String activityLocation;
    @SerializedName("activity_lat")
    @Expose
    private String activityLat;
    @SerializedName("activity_lng")
    @Expose
    private String activityLng;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("started")
    @Expose
    private String started;
    @SerializedName("ended")
    @Expose
    private String ended;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("id_activity_mst_type")
    @Expose
    private Integer idActivityMstType;
    @SerializedName("type")
    @Expose
    private String type;

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

    public List<String> getApiResponseFields() {
        return apiResponseFields;
    }

    public void setApiResponseFields(List<String> apiResponseFields) {
        this.apiResponseFields = apiResponseFields;
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

    public Integer getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(Integer idActivity) {
        this.idActivity = idActivity;
    }

    public String getActivityLocation() {
        return activityLocation;
    }

    public void setActivityLocation(String activityLocation) {
        this.activityLocation = activityLocation;
    }

    public String getActivityLat() {
        return activityLat;
    }

    public void setActivityLat(String activityLat) {
        this.activityLat = activityLat;
    }

    public String getActivityLng() {
        return activityLng;
    }

    public void setActivityLng(String activityLng) {
        this.activityLng = activityLng;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public String getEnded() {
        return ended;
    }

    public void setEnded(String ended) {
        this.ended = ended;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getIdActivityMstType() {
        return idActivityMstType;
    }

    public void setIdActivityMstType(Integer idActivityMstType) {
        this.idActivityMstType = idActivityMstType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
