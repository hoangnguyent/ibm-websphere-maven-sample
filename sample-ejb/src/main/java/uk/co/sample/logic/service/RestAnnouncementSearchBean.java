package uk.co.sample.logic.service;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import uk.co.sample.constant.Status;

public class RestAnnouncementSearchBean implements Serializable {

    private static final long serialVersionUID = 6536881956846428740L;
    @JsonProperty("key")
    private String            announcementSampleKey;
    @JsonProperty("text")
    private String            text;
    @JsonProperty("status")
    private Status            status;
    @JsonProperty("VERSION")
    private long              version;

    // ***** injection field *****
    // ***** constructor *****
    // ***** public method *****
    // ***** protected method *****
    // ***** private method *****
    // ***** getter and setter *****
    public String getAnnouncementSampleKey() {
        return announcementSampleKey;
    }

    public void setAnnouncementSampleKey(String announcementSampleKey) {
        this.announcementSampleKey = announcementSampleKey;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
