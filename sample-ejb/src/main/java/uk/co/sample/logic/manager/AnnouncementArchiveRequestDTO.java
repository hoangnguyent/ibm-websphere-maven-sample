package uk.co.sample.logic.manager;

import uk.co.sample.base.AbstractBaseRequestDTO;
import uk.co.sample.constant.Status;
import uk.co.sample.security.Operator;

public class AnnouncementArchiveRequestDTO extends AbstractBaseRequestDTO {

    private static final long serialVersionUID = -3655531232238629867L;

    private Status            status;

    // ***** injection field *****
    // ***** constructor *****
    public AnnouncementArchiveRequestDTO(Operator operator) {
        super(operator);
    }

    // ***** public method *****
    // ***** protected method *****
    // ***** private method *****
    // ***** getter and setter *****
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
