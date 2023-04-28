package uk.co.sample.mdb;

import static uk.co.sample.constant.Status.PENDING;
import static uk.co.sample.constant.SystemErrorCodeConstant.ERROR_JMS_ACCESS;
import static uk.co.sample.constant.SystemErrorCodeConstant.ERROR_SYSTEM;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import uk.co.sample.base.IfMessageProcessor;
import uk.co.sample.constant.BaseConstant;
import uk.co.sample.entity.AnnouncementSample;
import uk.co.sample.exception.AbstractApplicationRuntimeException;
import uk.co.sample.exception.ApplicationSystemException;
import uk.co.sample.logic.facade.AnnouncementCMTFacade;
import uk.co.sample.logic.manager.AnnouncementSampleEntryRequestDTO;
import uk.co.sample.security.Operator;
import uk.co.sample.util.ProcessingTimeUtil;

public class TrainActivationEventMessageProcessor extends AbstractMessageProcessor<AnnouncementSample> implements IfMessageProcessor {

    @Inject
    private AnnouncementCMTFacade announcementCMTFacade;

    // ***** public method *****
    @Override
    public void onListenerMessage(TextMessage message) {
        try {

            logger.debug(getOperator(), new Throwable().getStackTrace()[0].getMethodName(), message.getText());

            // TrainActivationXmlMessage xmlObject = XmlUtil.unmarshalXmlStringToObject(message.getText(), TrainActivationXmlMessage.class);

            AnnouncementSampleEntryRequestDTO requestDTO = new AnnouncementSampleEntryRequestDTO(getOperator());
            requestDTO.setStatus(PENDING.name());
            requestDTO.setText(message.getText()); // TODO: get data from xmlObject
            announcementCMTFacade.execAnnouncementSampleEntry(requestDTO); // TODO: update instead of create

        } catch (AbstractApplicationRuntimeException e) {
            throw e;
        } catch (JMSException e) {
            throw new ApplicationSystemException(ERROR_JMS_ACCESS, e);
        } catch (Exception e) {
            throw new ApplicationSystemException(ERROR_SYSTEM, e);
        }
    }

    // ***** protected method *****
    @Override
    protected Operator getOperator() {
        Operator operator = new Operator();
        operator.setTracingId(BaseConstant.SYSTEM_USER);
        operator.setTimeZone(ProcessingTimeUtil.getSystemTimeZone());
        return operator;
    }

    // ***** private method *****
    // ***** constructor *****
    // ***** getter and setter *****

}
