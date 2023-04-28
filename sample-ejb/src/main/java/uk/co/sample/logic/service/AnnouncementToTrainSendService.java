package uk.co.sample.logic.service;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.annotation.Resources;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;

import org.slf4j.Logger;

import uk.co.sample.base.IfMessageSender;
import uk.co.sample.constants.MessageDestinationName;
import uk.co.sample.custom.annotation.MessageDestination;
import uk.co.sample.custom.annotation.MessageDestinationProducer;
import uk.co.sample.util.XmlUtil;
import uk.co.sample.xml.AnnouncemenToTrainXmlMessage;

@Stateless
@Resources({
    @Resource(name = "jms/AnnToTrainConnectionFactory", lookup = "jms/AnnToTrainConnectionFactory", type = QueueConnectionFactory.class, authenticationType = AuthenticationType.CONTAINER),
    @Resource(name = "jms/ANN_SEND_TO_TRAIN_Q", lookup = "jms/ANN_SEND_TO_TRAIN_Q", type = Queue.class)
})
public class AnnouncementToTrainSendService {

    @Inject
    private Logger          logger;

    @Inject
    @MessageDestination(MessageDestinationName.ANN_SEND_TO_TRAIN_Q)
    @MessageDestinationProducer
    private IfMessageSender messageSender;

    //***** constructor *****
    //***** public method *****
    public boolean sendXml(AnnouncemenToTrainXmlMessage xmlFormatObject) {
        return sendMessage(XmlUtil.marshalXmlObjectToString(xmlFormatObject));
    }

    //***** protected method *****
    //***** private method *****
    private boolean sendMessage(String msg) {
        try {
            messageSender.send(msg);
            return true;
        } catch (Exception e) {
            logger.debug("Send failed", e);
            return false;
        }
    }
    //***** call back method *****
    //***** getter and setter *****
}
