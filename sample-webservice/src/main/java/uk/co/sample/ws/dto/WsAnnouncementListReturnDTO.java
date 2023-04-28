package uk.co.sample.ws.dto;

import java.util.ArrayList;
import java.util.List;

import uk.co.sample.ws.base.SoapBaseReturnDTO;

public class WsAnnouncementListReturnDTO extends SoapBaseReturnDTO {

    private static final long            serialVersionUID = 6294193929941199501L;

    private List<WsAnnouncementListBean> announcements    = new ArrayList<>();

    //***** constructor *****
    public WsAnnouncementListReturnDTO() {
        super();
    }

    //***** protected method *****
    //***** private method *****
    public List<WsAnnouncementListBean> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<WsAnnouncementListBean> announcements) {
        this.announcements = announcements;
    }
}
