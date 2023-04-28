package uk.co.sample.mdb;

import static uk.co.sample.constant.SystemErrorCodeConstant.ERROR_JMS_ACCESS;
import static uk.co.sample.constant.SystemErrorCodeConstant.ERROR_SYSTEM;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import uk.co.sample.base.IfMessageProcessor;
import uk.co.sample.base.IfMessageSender;
import uk.co.sample.exception.AbstractApplicationRuntimeException;
import uk.co.sample.exception.ApplicationSystemException;

public abstract class AbstractListenerMDBBean implements MessageListener {

    // 'MaxAttempts' is the attribute of the Listener. We cannot obtain it from the delivered message.
    // Must hard code here. The consequence is that: in AdminConsole/Server/Listener Ports, we must configure this value > 3. Seriously!!!
    // Otherwise, when there is a failure, doProcess() will be called forever and the Listener will be crashed (shutdown).
    private static final int    DEFAULT_MAX_RETRY_COUNT = 3;
    // Delivery count of the message/
    private static final String JMSX_DELIVERY_COUNT     = "JMSXDeliveryCount";

    //***** public method *****
    @Override
    public void onMessage(Message msg) {
        try {
            if (msg.getIntProperty(JMSX_DELIVERY_COUNT) - 1 > DEFAULT_MAX_RETRY_COUNT) {
                handlePoisonMessage(msg);
            } else {
                doProcess(msg);
            }
        } catch (AbstractApplicationRuntimeException e) {
            throw e;
        } catch (JMSException e) {
            throw new ApplicationSystemException(ERROR_JMS_ACCESS, e);
        } catch (Exception e) {
            throw new ApplicationSystemException(ERROR_SYSTEM, e);
        }
    }

    //***** protected method *****
    /**
     * Execute Message Process
     *
     * @param msg The Message Queue Contents
     */
    protected void doProcess(Message msg) {
        TextMessage textMessage = (TextMessage) msg;
        IfMessageProcessor processor = getMessageProcessor(textMessage);
        processor.onListenerMessage(textMessage);
    }

    /**
     * handlePoisonMessage
     * <br>
     * @param msg Message
     */
    protected void handlePoisonMessage(Message msg) {
        TextMessage textMessage = (TextMessage) msg;
        IfMessageProcessor processor = getMessageProcessor(textMessage);
        processor.onListenerRetryableMaxCount(textMessage, getDeadMessageSender());
    }

    protected abstract IfMessageProcessor getMessageProcessor(TextMessage textMessage);

    protected abstract IfMessageSender getDeadMessageSender();

    //***** constructor *****
    //***** private method *****
    //***** getter and setter *****

}
