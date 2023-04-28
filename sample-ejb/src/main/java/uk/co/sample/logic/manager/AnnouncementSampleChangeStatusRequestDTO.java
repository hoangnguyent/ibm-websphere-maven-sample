package uk.co.sample.logic.manager;

import uk.co.sample.base.AbstractBaseRequestDTO;
import uk.co.sample.security.Operator;

public class AnnouncementSampleChangeStatusRequestDTO extends AbstractBaseRequestDTO {

    private static final long serialVersionUID = -1646586066288473081L;

    private String            announcementSampleKey;
    private String            statusSign;

    //***** constructor *****
    public AnnouncementSampleChangeStatusRequestDTO(Operator operator) {
        super(operator);
    }

    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    //***** getter and setter *****
    public String getAnnouncementSampleKey() {
        return announcementSampleKey;
    }

    public void setAnnouncementSampleKey(String announcementSampleKey) {
        this.announcementSampleKey = announcementSampleKey;
    }

    public String getStatusSign() {
        return statusSign;
    }

    public void setStatusSign(String statusSign) {
        this.statusSign = statusSign;
    }

}
