package uk.co.sample.logic.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestAnnouncementListSearchReturnDTO implements Serializable {

    private static final long                serialVersionUID = 2329028947141040871L;

    @JsonProperty("AnnouncementInstanceDetails")
    private List<RestAnnouncementSearchBean> announcements    = new ArrayList<>();

    @JsonProperty("The number of records that is returned")
    private int                              count;

    @JsonProperty("The total number of records in the database")
    private int                              totalCount;

    // ***** constructor *****
    // ***** public method *****
    // ***** protected method *****
    // ***** private method *****
    // ***** call back method *****
    // ***** getter and setter *****
    public List<RestAnnouncementSearchBean> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<RestAnnouncementSearchBean> announcements) {
        this.announcements = announcements;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
