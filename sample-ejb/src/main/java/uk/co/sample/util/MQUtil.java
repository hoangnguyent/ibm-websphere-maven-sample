package uk.co.sample.util;

import uk.co.sample.constant.CommonConstant;
import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.constants.MessageDestinationName;
import uk.co.sample.constants.MessageTransportType;
import uk.co.sample.exception.ApplicationDataException;

public class MQUtil {

    private static final String PROPERTY_FILE      = "MessageSender";
    private static final String PARAM_QUEUE_NAME   = "queueName";

    private static final String PARAM_FACTORY_NAME = "factoryName";
    private static final String PARAM_MESSAGE_TYPE = "type";

    private MQUtil() {
        // Do nothing
    }

    //***** public method *****
    /**
     * get Queue Connection Factory JNDI Name
     * <br>
     * @param destination
     * @return Queue Connection Factory JNDI Name
     */
    public static String getQueueConnectionFactory(MessageDestinationName destination) {
        return obtainSingleton().readProperty(destination.getTarget() + CommonConstant.DOT + PARAM_FACTORY_NAME);
    }

    /**
     * get Queue Name
     * <br>
     * @param destination
     * @return Queue's JNDI Name
     */
    public static String getQueueName(MessageDestinationName destination) {
        return obtainSingleton().readProperty(destination.getTarget() + CommonConstant.DOT + PARAM_QUEUE_NAME);
    }

    /**
     * get MessageType
     * <br>
     * @param destination
     * @return Message type
     */
    public static MessageTransportType getMessageType(MessageDestinationName destination) {
        String msgType = obtainSingleton().readProperty(destination.getTarget() + CommonConstant.DOT + PARAM_MESSAGE_TYPE);
        try {
            return MessageTransportType.valueOf(msgType);
        } catch (IllegalArgumentException e) {
            throw new ApplicationDataException(SystemErrorCodeConstant.ERROR_SYSTEM);
        }
    }

    //***** constructor *****
    //***** protected method *****
    //***** private method *****
    private static PropertyUtil obtainSingleton() {
        return Holder.propertyUtil;
    }

    private static final class Holder {

        private static PropertyUtil propertyUtil = new PropertyUtil(PROPERTY_FILE);
    }
    //***** getter and setter *****

}
