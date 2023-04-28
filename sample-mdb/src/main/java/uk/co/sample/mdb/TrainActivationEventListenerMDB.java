package uk.co.sample.mdb;

import static uk.co.sample.constants.MessageDestinationName.TRAIN_ACTIVATION_EVENT_DEADQ;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.annotation.Resources;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.TextMessage;

import uk.co.sample.base.IfMessageProcessor;
import uk.co.sample.base.IfMessageSender;
import uk.co.sample.custom.annotation.MessageDestination;
import uk.co.sample.custom.annotation.MessageDestinationProducer;

/**
 * Message-Driven Bean implementation class for: TrainActivationEventListenerMDB
 *
 */
@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
@Resources({
    @Resource(name = "jms/TrainActivationEventConnectionFactory", lookup = "jms/TrainActivationEventConnectionFactory", type = QueueConnectionFactory.class, authenticationType = AuthenticationType.CONTAINER),
    @Resource(name = "jms/TRAIN_ACTIVATION_EVENT_DEADQ", lookup = "jms/TRAIN_ACTIVATION_EVENT_DEADQ", type = Queue.class)
})
public class TrainActivationEventListenerMDB extends AbstractListenerMDBBean implements MessageListener {

    @Inject
    private TrainActivationEventMessageProcessor processor;

    @Inject
    @MessageDestination(TRAIN_ACTIVATION_EVENT_DEADQ)
    @MessageDestinationProducer
    private IfMessageSender                     deadQSender;

    @Override
    protected IfMessageProcessor getMessageProcessor(TextMessage textMessage) {
        return processor;
    }

    @Override
    protected IfMessageSender getDeadMessageSender() {
        return deadQSender;
    }
}
