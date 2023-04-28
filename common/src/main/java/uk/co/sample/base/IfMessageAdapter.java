package uk.co.sample.base;

import java.util.Date;

import uk.co.sample.constant.Status;
import uk.co.sample.exception.AbstractApplicationRuntimeException;
import uk.co.sample.security.Operator;

public interface IfMessageAdapter<T> {
    /**
     * callManager
     * <br>
     * @param receivedBean Receive TBL
     * @param operator  Operator
     * @param startDate Date
     */
    void callManager(T receivedBean, Operator operator, Date startDate);

    /**
     * Handle Error 
     * <br>
     * @param e exception
     * @param receivedBean Receive TBL
     * @param operator Operator
     * @param startDate Date
     * @return MQReceiveTblStatus
     */
    Status onError(AbstractApplicationRuntimeException e, T receivedBean, Operator operator, Date startDate);
}
