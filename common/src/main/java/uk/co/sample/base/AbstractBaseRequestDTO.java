package uk.co.sample.base;

import java.io.Serializable;
import java.util.Date;

import uk.co.sample.security.Operator;
import uk.co.sample.util.ProcessingTimeUtil;

public abstract class AbstractBaseRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Operator          operator;
    private Date              managerTime;

    // ***** constructor *****
    public AbstractBaseRequestDTO(Operator operator) {
        this.operator = operator;
        this.managerTime = ProcessingTimeUtil.getProcessingTime(operator.getTimeZone());
    }

    public AbstractBaseRequestDTO(AbstractBaseRequestDTO requestDTO) {
        this.operator = requestDTO.getOperator();
        this.managerTime = requestDTO.getManagerTime();
    }

    public AbstractBaseRequestDTO(Operator operator, Date managerTime) {
        this.operator = operator;
        this.managerTime = managerTime;
    }

    // ***** public method *****
    // ***** getter and setter *****
    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Date getManagerTime() {
        return managerTime;
    }

    public void setManagerTime(Date managerTime) {
        this.managerTime = managerTime;
    }

    // ***** injection field *****
    // ***** protected method *****
    // ***** private method *****

}
