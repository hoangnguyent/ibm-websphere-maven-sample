package uk.co.sample.logic.manager;

import uk.co.sample.base.AbstractBatchRequestDTO;
import uk.co.sample.constant.Status;
import uk.co.sample.security.Operator;

public class BatchAnnouncementDeleteRequestDTO extends AbstractBatchRequestDTO {

    private static final long serialVersionUID = 2237278382018293712L;

    private Status            status;

    // ***** injection field *****
    // ***** constructor *****
    public BatchAnnouncementDeleteRequestDTO(Operator operator) {
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
