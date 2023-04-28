package uk.co.sample.logic.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import uk.co.log.LogWrapperImpl;
import uk.co.sample.logic.manager.AnnouncementArchiveRequestDTO;
import uk.co.sample.logic.manager.AnnouncementManager;
import uk.co.sample.logic.manager.AnnouncementProcessRequestDTO;
import uk.co.sample.logic.manager.AnnouncementSampleChangeStatusRequestDTO;
import uk.co.sample.logic.manager.AnnouncementSampleEntryRequestDTO;
import uk.co.sample.logic.service.RestAnnouncementListSearchRequestDTO;
import uk.co.sample.logic.service.RestAnnouncementListSearchReturnDTO;
import uk.co.sample.logic.service.RestAnnouncementListSearchService;
import uk.co.sample.logic.service.SoapAnnouncementListSearchRequestDTO;
import uk.co.sample.logic.service.SoapAnnouncementListSearchReturnDTO;
import uk.co.sample.logic.service.SoapAnnouncementListSearchService;

/**
 * <h3>
 *  Implementation class for Enterprise Bean: AnnouncementCMTFacade
 * </h3>
 * 
 *  By default, transaction is not opened for Inquiry
 *  For Insert/Update/Delete, must indicate new Transaction
 * 
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AnnouncementCMTFacade extends AbstractBaseCMTSessionBean {

    // ***** injection field *****
    @Inject
    private LogWrapperImpl                    logger;
    @Inject
    private AnnouncementManager               announcementManager;
    @Inject
    private RestAnnouncementListSearchService restAnnouncementListSearchService;
    @Inject
    private SoapAnnouncementListSearchService soapAnnouncementListSearchService;

    // ***** constructor *****
    // ***** public method *****
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void archiveAnnouncementSamples(AnnouncementArchiveRequestDTO managerRequest) {
        logger.logStart(managerRequest.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        try {
            announcementManager.archiveAnnouncementSamples(managerRequest);
        } catch (Exception e) {
            throw handleException(managerRequest.getOperator(), e, new Throwable().getStackTrace()[0].getMethodName());
        } finally {
            logger.logEnd(managerRequest.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void processAnnouncements(AnnouncementProcessRequestDTO managerRequest) {
        logger.logStart(managerRequest.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        try {
            announcementManager.processAnnouncementSamples(managerRequest);
        } catch (Exception e) {
            throw handleException(managerRequest.getOperator(), e, new Throwable().getStackTrace()[0].getMethodName());
        } finally {
            logger.logEnd(managerRequest.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) //Separately with the Queue transaction. Otherwise, when there is an issue, ListenerPort may be stopped due to the Message returning so many times.
    public String execAnnouncementSampleEntry(AnnouncementSampleEntryRequestDTO requestDTO) {
        logger.logStart(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        try {
            return announcementManager.execAnnouncementSampleEntry(requestDTO);
        } catch (Exception e) {
            throw handleException(requestDTO.getOperator(), e, new Throwable().getStackTrace()[0].getMethodName());
        } finally {
            logger.logEnd(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) //Separately with the Queue transaction. Otherwise, when there is an issue, ListenerPort may be stopped due to the Message returning so many times.
    public void changeAnnouncementSampleStatus(AnnouncementSampleChangeStatusRequestDTO requestDTO) {
        logger.logStart(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        try {
            announcementManager.changeAnnouncementSampleStatus(requestDTO);
        } catch (Exception e) {
            throw handleException(requestDTO.getOperator(), e, new Throwable().getStackTrace()[0].getMethodName());
        } finally {
            logger.logEnd(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        }
    }

    public RestAnnouncementListSearchReturnDTO callRestAnnouncementListSearch(RestAnnouncementListSearchRequestDTO requestDTO) {
        logger.logStart(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        try {
            return restAnnouncementListSearchService.doSearch(requestDTO);
        } catch (Exception e) {
            throw handleException(requestDTO.getOperator(), e, new Throwable().getStackTrace()[0].getMethodName());
        } finally {
            logger.logEnd(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        }
    }

    public SoapAnnouncementListSearchReturnDTO callSoapAnnouncementListSearch(SoapAnnouncementListSearchRequestDTO requestDTO) {
        logger.logStart(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        try {
            return soapAnnouncementListSearchService.doSearch(requestDTO);
        } catch (Exception e) {
            throw handleException(requestDTO.getOperator(), e, new Throwable().getStackTrace()[0].getMethodName());
        } finally {
            logger.logEnd(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        }
    }
    // ***** protected method *****
    @Override
    protected LogWrapperImpl getLogger() {
        return logger;
    }
    // ***** private method *****
    // ***** getter and setter *****

}
