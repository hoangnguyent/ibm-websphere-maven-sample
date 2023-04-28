package uk.co.sample.logic.service;

import uk.co.sample.base.AbstractBaseRequestDTO;
import uk.co.sample.constant.Status;

public class AnnouncementForDeleteListSearchRequestDTO extends AbstractBaseRequestDTO {

    private static final long serialVersionUID = 21474902027062416L;

    public AnnouncementForDeleteListSearchRequestDTO(AbstractBaseRequestDTO requestDTO) {
        super(requestDTO);
    }

    private Status status;

    // ***** constructor *****

    // ***** injection field *****
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
