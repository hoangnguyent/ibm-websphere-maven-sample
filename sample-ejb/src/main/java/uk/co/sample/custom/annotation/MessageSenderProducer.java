package uk.co.sample.custom.annotation;

import static uk.co.sample.util.MQUtil.getMessageType;
import static uk.co.sample.util.MQUtil.getQueueConnectionFactory;
import static uk.co.sample.util.MQUtil.getQueueName;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.LoggerFactory;

import uk.co.sample.base.FileMessageSender;
import uk.co.sample.base.IfMessageSender;
import uk.co.sample.base.MQMessageSender;
import uk.co.sample.constants.MessageDestinationName;
import uk.co.sample.constants.MessageTransportType;

public class MessageSenderProducer implements Serializable {

    private static final long serialVersionUID = -1563163750461187996L;

    // ***** constructor *****
    // ***** public method *****
    @Produces
    @MessageDestinationProducer
    public IfMessageSender getMessageSender(InjectionPoint injectionPoint) {
        Annotated annotated = injectionPoint.getAnnotated();
        MessageDestination msgDest = annotated.getAnnotation(MessageDestination.class);
        return createMessageSender(msgDest.value());
    }

    // ***** protected method *****
    // ***** private method *****

    /**
     * Create Message Sender based on Transport type
     * <br>
     * @param msgDestName MessageDestinationName
     * @return MessageSender implementation
     */
    private IfMessageSender createMessageSender(MessageDestinationName msgDestName) {
        MessageTransportType transportType = getMessageType(msgDestName);
        switch (transportType) {
        case MQ:
            return new MQMessageSender(getQueueConnectionFactory(msgDestName), getQueueName(msgDestName), LoggerFactory.getLogger(MQMessageSender.class));
        case FILE:
            return new FileMessageSender(LoggerFactory.getLogger(FileMessageSender.class));
        default:
            return new FileMessageSender(LoggerFactory.getLogger(FileMessageSender.class));
        }
    }
    // ***** call back method *****
    // ***** getter and setter *****

}
