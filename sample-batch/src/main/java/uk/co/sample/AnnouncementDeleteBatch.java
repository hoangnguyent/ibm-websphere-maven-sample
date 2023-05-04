package uk.co.sample;

import static uk.co.sample.constant.BatchConstant.END_CODE_ERROR;
import static uk.co.sample.constant.BatchConstant.END_CODE_SUCCESS;
import static uk.co.sample.constant.BatchConstant.END_CODE_WARNING;
import static uk.co.sample.util.EJBUtil.lookupRemoteEJB;

import org.slf4j.LoggerFactory;

import uk.co.log.LogWrapperImpl;
import uk.co.sample.base.AbstractBatch;
import uk.co.sample.base.Result;
import uk.co.sample.logic.facade.BatchBMTFacadeRemote;
import uk.co.sample.logic.manager.BatchAnnouncementDeleteRequestDTO;
import uk.co.sample.security.Operator;

public class AnnouncementDeleteBatch extends AbstractBatch {

    private static LogWrapperImpl logger = new LogWrapperImpl(LoggerFactory.getLogger(AnnouncementDeleteBatch.class), AnnouncementDeleteBatch.class.getName());

    // ***** injection field *****
    // ***** constructor *****
    // ***** public method *****
    @Override
    public int execute() {

        Operator operator = getOperator();
        logger.logStart(operator, new Throwable().getStackTrace()[0].getMethodName());

        try {

            BatchAnnouncementDeleteRequestDTO requestDTO = new BatchAnnouncementDeleteRequestDTO(operator);
            requestDTO.setBatchId(getBatchId());
            BatchBMTFacadeRemote facade = lookupRemoteEJB(BatchBMTFacadeRemote.class);
            Result result = facade.callAnnouncementDelete(requestDTO);

            if (result.hasWarn()) {
                return END_CODE_WARNING;
            }

        } catch (Exception e) {
            return END_CODE_ERROR;

        } finally {
            logger.logEnd(operator, new Throwable().getStackTrace()[0].getMethodName());
        }

        return END_CODE_SUCCESS;
    }
    // ***** protected method *****
    // ***** private method *****
    // ***** getter and setter *****

}
