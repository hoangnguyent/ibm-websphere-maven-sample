package uk.co.sample.base;

import uk.co.sample.security.Operator;

public class AbstractBatchRequestDTO extends AbstractBaseRequestDTO {

    private static final long serialVersionUID = -436264824798211944L;
    private String            batchId;

    public AbstractBatchRequestDTO(Operator operator) {
        super(operator);
    }

    // ***** injection field *****
    // ***** constructor *****
    // ***** public method *****
    // ***** protected method *****
    // ***** private method *****
    // ***** getter and setter *****
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
}
