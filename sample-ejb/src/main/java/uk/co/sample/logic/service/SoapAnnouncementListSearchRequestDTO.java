package uk.co.sample.logic.service;

import uk.co.sample.base.AbstractBaseRequestDTO;
import uk.co.sample.constant.Status;
import uk.co.sample.security.Operator;

public class SoapAnnouncementListSearchRequestDTO extends AbstractBaseRequestDTO {

    private static final long serialVersionUID = -1658464156124726598L;

    private Status            status;
    private String            text;

    // ***** constructor *****
    public SoapAnnouncementListSearchRequestDTO(Operator operator) {
        super(operator);
    }

    // ***** public method *****
    // ***** protected method *****
    // ***** private method *****
    // ***** call back method *****
    // ***** getter and setter *****
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
