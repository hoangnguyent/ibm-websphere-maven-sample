package uk.co.sample.ws.dto;

import java.io.Serializable;

public class WsAnnouncementListBean implements Serializable {

    private static final long serialVersionUID = 2978603120269487626L;

    private String            announcementSampleKey;
    private String            text;
    private long              version;

    //***** constructor *****
    //***** protected method *****
    //***** private method *****
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
