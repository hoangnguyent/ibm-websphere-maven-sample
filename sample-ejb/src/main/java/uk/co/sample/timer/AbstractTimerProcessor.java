package uk.co.sample.timer;

import java.util.Date;

import uk.co.sample.constant.BaseConstant;
import uk.co.sample.security.Operator;
import uk.co.sample.util.ProcessingTimeUtil;

/**
 * <h3>
 *  AbstractTimerProcessor
 * </h3>
 *
 * @author hoangnguyen
 * @since 2021/05/13
 */
public abstract class AbstractTimerProcessor {

    private String     schedulerId;
    private final Date managerTime = new Date();
    private Operator   operator    = null;

    // ***** constructor *****
    public AbstractTimerProcessor() {
        // Do nothing
    }

    // ***** public method *****
    public Operator initOperator() {
        operator = new Operator();
        operator.setTracingId(BaseConstant.SYSTEM_USER);
        operator.setTimeZone(ProcessingTimeUtil.getSystemTimeZone());
        return operator;
    }

    // ***** protected method *****
    // ***** private method *****
    // ***** getter and setter *****
    public Date getManagerTime() {
        return managerTime;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getSchedulerId() {
        return schedulerId;
    }

    public void setSchedulerId(String schedulerId) {
        this.schedulerId = schedulerId;
    }

    // ***** injection field *****
    // ***** public method *****

}
