package uk.co.sample.logic.service;

import java.util.List;

import uk.co.sample.base.AbstractBaseRequestDTO;
import uk.co.sample.constant.Status;

public class AnnouncementForProcessListSearchRequestDTO extends AbstractBaseRequestDTO {

    private static final long serialVersionUID = 3840804662850901596L;

    public AnnouncementForProcessListSearchRequestDTO(AbstractBaseRequestDTO requestDTO) {
        super(requestDTO);
    }

    private List<Status> status;

    // ***** constructor *****

    // ***** injection field *****
    // ***** public method *****
    // ***** protected method *****
    // ***** private method *****
    // ***** getter and setter *****

    public void setStatus(List<Status> status) {
        this.status = status;
    }

    public List<Status> getStatus() {
        return status;
    }

}
