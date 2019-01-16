package com.lmu.warungdananew.Response;

/**
 * Created by Gigabyte on 12/7/2018.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailUpdateVersion {

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
        @SerializedName("link")
        @Expose
        private String link;
        @SerializedName("version")
        @Expose
        private Integer version;

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

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public Integer getVersion() {
            return version;
        }

        public void setVersion(Integer version) {
            this.version = version;
        }

}
