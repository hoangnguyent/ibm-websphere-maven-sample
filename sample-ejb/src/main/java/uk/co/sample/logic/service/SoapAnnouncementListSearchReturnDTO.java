package uk.co.sample.logic.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SoapAnnouncementListSearchReturnDTO implements Serializable {

    private static final long                serialVersionUID = -3466318544644108883L;
    private List<SoapAnnouncementSearchBean> announcements    = new ArrayList<>();

    //***** constructor *****
    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    //***** getter and setter *****
    public List<SoapAnnouncementSearchBean> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<SoapAnnouncementSearchBean> announcements) {
        this.announcements = announcements;
    }

}
