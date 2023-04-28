package uk.co.sample.logic.manager;

import uk.co.sample.base.AbstractBaseRequestDTO;
import uk.co.sample.security.Operator;

public class AnnouncementSampleEntryRequestDTO extends AbstractBaseRequestDTO {

    private static final long serialVersionUID = 49724068042050630L;

    private String            text;
    private String            status;

    //***** constructor *****
    public AnnouncementSampleEntryRequestDTO(Operator operator) {
        super(operator);
    }

    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    //***** getter and setter *****
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
