package uk.co.sample.logic.manager;

import static uk.co.sample.converter.TimestampAndDateConverter.convertDateToTimestamp;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import uk.co.log.LogWrapperImpl;
import uk.co.sample.constant.Status;
import uk.co.sample.dao.AnnouncementSampleDao;
import uk.co.sample.entity.AnnouncementSample;
import uk.co.sample.logic.service.AnnouncementForArchiveListSearchRequestDTO;
import uk.co.sample.logic.service.AnnouncementForArchiveSearchService;
import uk.co.sample.logic.service.AnnouncementForDeleteSearchBean;
import uk.co.sample.logic.service.AnnouncementForProcessListSearchRequestDTO;
import uk.co.sample.logic.service.AnnouncementForProcessSearchBean;
import uk.co.sample.logic.service.AnnouncementForProcessSearchService;
import uk.co.sample.logic.service.AnnouncementToTrainSendService;
import uk.co.sample.logic.service.SequenceSearchService;
import uk.co.sample.xml.AnnouncemenToTrainXmlMessage;

@Stateless
public class AnnouncementManager {

    // ***** injection field *****
    @Inject
    private LogWrapperImpl                      logger;
    @Inject
    AnnouncementSampleDao                       announcementSampleDao;
    @Inject
    AnnouncementToTrainSendService              announcementToTrainSendService;
    @Inject
    private SequenceSearchService               sequenceSearchService;
    @Inject
    private AnnouncementForArchiveSearchService announcementForArchiveSearchService;
    @Inject
    private AnnouncementForProcessSearchService announcementForProcessSearchService;

    // ***** constructor *****
    // ***** public method *****
    public BatchAnnouncementDeleteReturnDTO archiveAnnouncementSamples(AnnouncementArchiveRequestDTO managerRequest) {

        logger.logStart(managerRequest.getOperator(), new Throwable().getStackTrace()[0].getMethodName());

        try {
            BatchAnnouncementDeleteReturnDTO returnDTO = AnnouncementManagerHelper.createAnnouncementArchiveReturnDTO();

            AnnouncementForArchiveListSearchRequestDTO searchRequestDTO = AnnouncementManagerHelper.createAnnouncementForArchiveListSearchRequestDTO(managerRequest);
            List<AnnouncementForDeleteSearchBean> announcements = announcementForArchiveSearchService.doSearch(searchRequestDTO);
            if (announcements.isEmpty()) {
                logger.debug(managerRequest.getOperator(), new Throwable().getStackTrace()[0].getMethodName(), "No Announcement to archive.");
                return returnDTO;
            }

            announcements.forEach(item -> {

                boolean archived = false;
                archived = true; //TODO: call UI api to archive

                if (!archived) {
                    logger.debug(managerRequest.getOperator(), new Throwable().getStackTrace()[0].getMethodName(), "Cannot archive Announcement: " + item.getAnnouncementSampleKey());
                    return;
                }

                AnnouncementSample entity = announcementSampleDao.find(item.getAnnouncementSampleKey());
                entity.setModDate(convertDateToTimestamp(managerRequest.getManagerTime()));
                entity.setStatus(Status.DELETED.name());
                announcementSampleDao.edit(entity);

            });

            return returnDTO;

        } finally {
            logger.logEnd(managerRequest.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        }

    }

    public AnnouncementProcessReturnDTO processAnnouncementSamples(AnnouncementProcessRequestDTO managerRequest) {

        logger.logStart(managerRequest.getOperator(), new Throwable().getStackTrace()[0].getMethodName());

        try {
            AnnouncementProcessReturnDTO returnDTO = AnnouncementManagerHelper.createAnnouncementProcessReturnDTO();

            AnnouncementForProcessListSearchRequestDTO searchRequestDTO = AnnouncementManagerHelper.createAnnouncementForProcessListSearchRequestDTO(managerRequest);
            List<AnnouncementForProcessSearchBean> announcements = announcementForProcessSearchService.doSearch(searchRequestDTO);

            if (announcements.isEmpty()) {
                logger.debug(managerRequest.getOperator(), new Throwable().getStackTrace()[0].getMethodName(), "No Announcement to process.");
                return returnDTO;
            }

            announcements.forEach(item -> {

                AnnouncemenToTrainXmlMessage xmlObject = new AnnouncemenToTrainXmlMessage();
                xmlObject.setAnnouncement(item.getText());

                boolean sent = announcementToTrainSendService.sendXml(xmlObject);

                if (!sent) {
                    logger.debug(managerRequest.getOperator(), new Throwable().getStackTrace()[0].getMethodName(), "Cannot sent Announcement: " + item.getAnnouncementSampleKey());
                    return;
                }

                AnnouncementSample entity = announcementSampleDao.find(item.getAnnouncementSampleKey());
                entity.setModDate(convertDateToTimestamp(managerRequest.getManagerTime()));
                entity.setStatus(sent ? Status.SUCCEEDED.name() : Status.FAILED.name());
                announcementSampleDao.edit(entity);

            });

            return returnDTO;

        } finally {
            logger.logEnd(managerRequest.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        }
    }

    public String execAnnouncementSampleEntry(AnnouncementSampleEntryRequestDTO requestDTO) {

        logger.logStart(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());

        try {
            AnnouncementSample entity = new AnnouncementSample();
            entity.setStatus(Status.PENDING.name());
            entity.setRegDate(convertDateToTimestamp(requestDTO.getManagerTime()));
            entity.setModDate(convertDateToTimestamp(requestDTO.getManagerTime()));
            entity.setAnnouncementSampleKey(sequenceSearchService.obtainSqNoForAnnouncementSample());
            entity.setText(requestDTO.getText());

            announcementSampleDao.create(entity);
            logger.debug(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName(), "Announcement created: " + entity.getAnnouncementSampleKey());

            return entity.getAnnouncementSampleKey();

        } finally {
            logger.logEnd(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        }
    }

    public void changeAnnouncementSampleStatus(AnnouncementSampleChangeStatusRequestDTO requestDTO) {

        logger.logStart(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());

        try {

            AnnouncementSample entity = announcementSampleDao.find(requestDTO.getAnnouncementSampleKey());
            entity.setStatus(requestDTO.getStatusSign());

            announcementSampleDao.edit(entity);

        } finally {
            logger.logEnd(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        }
    }
    // ***** private method *****
    // ***** protected method *****
    // ***** getter and setter *****

}
