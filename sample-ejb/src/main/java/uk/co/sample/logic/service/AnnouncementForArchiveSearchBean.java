package uk.co.sample.logic.service;

import java.io.Serializable;

public class AnnouncementForArchiveSearchBean implements Serializable {

    private static final long serialVersionUID = 8821672435448468730L;

    private String            announcementSampleKey;
    private String            text;
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

}
