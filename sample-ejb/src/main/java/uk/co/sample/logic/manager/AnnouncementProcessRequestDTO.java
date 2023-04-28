package uk.co.sample.logic.manager;

import java.util.List;

import uk.co.sample.base.AbstractBaseRequestDTO;
import uk.co.sample.constant.Status;
import uk.co.sample.security.Operator;

public class AnnouncementProcessRequestDTO extends AbstractBaseRequestDTO {

    private static final long serialVersionUID = 3840701045389572836L;

    private List<Status>      status;

    // ***** injection field *****
    // ***** constructor *****
    public AnnouncementProcessRequestDTO(Operator operator) {
        super(operator);
    }

    // ***** public method *****
    // ***** protected method *****
    // ***** private method *****
    // ***** getter and setter *****
    public List<Status> getStatus() {
        return status;
    }

    public void setStatus(List<Status> status) {
        this.status = status;
    }

}
