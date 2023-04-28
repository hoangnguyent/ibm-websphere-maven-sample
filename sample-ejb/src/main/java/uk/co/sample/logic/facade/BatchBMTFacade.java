package uk.co.sample.logic.facade;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import uk.co.log.LogWrapperImpl;
import uk.co.sample.base.AbstractBatchRequestDTO;
import uk.co.sample.base.Result;
import uk.co.sample.logic.manager.BatchAnnouncementDeleteRequestDTO;
import uk.co.sample.logic.manager.BatchManager;

@Local
@Stateless
@Remote(BatchBMTFacadeRemote.class)
@TransactionManagement(TransactionManagementType.BEAN)
public class BatchBMTFacade extends AbstractBaseBMTSessionBean implements BatchBMTFacadeRemote {

    // ***** injection field *****
    @Inject
    private LogWrapperImpl logger;
    @Inject
    private BatchManager   batchManager;

    @Override
    public Result callAnnouncementDelete(AbstractBatchRequestDTO abstractBatchRequestDTO) {

        BatchAnnouncementDeleteRequestDTO requestDTO = (BatchAnnouncementDeleteRequestDTO) abstractBatchRequestDTO;

        logger.logStart(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());

        boolean hasException = false;
        try {

            super.beginTransactionWithTimeout(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName(), DEFAULT_TIMEOUT_SECOND);

            return batchManager.callAnnouncementDelete(requestDTO);

        } catch (Exception e) {

            hasException = true;
            throw handleException(requestDTO.getOperator(), e, new Throwable().getStackTrace()[0].getMethodName());

        } finally {
            logger.logEnd(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
            super.endTransaction(requestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName(), hasException);
        }
    }

    // ***** private method *****
    // ***** constructor *****
    // ***** protected method *****
    @Override
    protected LogWrapperImpl getLogger() {
        return logger;
    }
    // ***** getter and setter *****

}
