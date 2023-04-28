package uk.co.sample.mdb;

import javax.inject.Inject;
import javax.jms.TextMessage;

import uk.co.log.LogWrapperImpl;
import uk.co.sample.base.IfMessageProcessor;
import uk.co.sample.base.IfMessageSender;
import uk.co.sample.exception.AbstractApplicationRuntimeException;
import uk.co.sample.security.Operator;

public abstract class AbstractMessageProcessor<T> implements IfMessageProcessor {

    @Inject
    protected LogWrapperImpl logger;

    //***** public method *****
    @Override
    public void onListenerRetryableMaxCount(TextMessage message, IfMessageSender messageSender) {
        logger.debug(getOperator(), new Throwable().getStackTrace()[0].getMethodName(),
            "The number of Retry to process the message has excessed the max value. The message {} will be put to DEAD letter Queue.");

        messageSender.send(message);
    }

    @Override
    public boolean isEnableListenerProcess() {
        return true;
    }

    //***** protected method *****
    protected void doCollectErrorCodesToText(final AbstractApplicationRuntimeException e, final StringBuilder errorCodeContainer) {
        errorCodeContainer.append(e.getCode());
    }

    protected abstract Operator getOperator();

    //***** constructor *****
    //***** private method *****
    //***** call back method *****
    //***** getter and setter *****

}
