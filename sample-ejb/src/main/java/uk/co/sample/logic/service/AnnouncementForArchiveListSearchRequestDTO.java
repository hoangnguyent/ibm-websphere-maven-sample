package uk.co.sample.logic.service;

import uk.co.sample.base.AbstractBaseRequestDTO;
import uk.co.sample.constant.Status;

public class AnnouncementForArchiveListSearchRequestDTO extends AbstractBaseRequestDTO {

    private static final long serialVersionUID = 691004354678310434L;

    public AnnouncementForArchiveListSearchRequestDTO(AbstractBaseRequestDTO requestDTO) {
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
