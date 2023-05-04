package uk.co.sample;

import static uk.co.sample.constant.BatchConstant.BATCH_PROP_FILE;
import static uk.co.sample.constant.BatchConstant.END_CODE_ERROR;
import static uk.co.sample.constant.BatchConstant.SUFFIX_CLASS;
import static uk.co.sample.constant.BatchErrorCodeConstant.ERROR_CLASS_NOT_FOUND;
import static uk.co.sample.constant.CommonConstant.HYPHEN;
import static uk.co.sample.constant.CommonConstant.LOG_SOURCE_BATCH;
import static uk.co.sample.constant.CommonConstant.LOG_SOURCE_KEY;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import uk.co.sample.base.AbstractBatch;
import uk.co.sample.constant.CommonConstant;
import uk.co.sample.exception.ApplicationSystemException;
import uk.co.sample.util.PropertyUtil;

public class BatchRunner {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    //***** injection field *****
    //***** constructor *****
    //***** public method *****
    public static void main(String[] args) {

        if (null == args || 0 == args.length || StringUtils.isBlank(args[0])) {
            System.exit(END_CODE_ERROR);
        }

        BatchRunner batchRunner = new BatchRunner();
        int batchResult = batchRunner.execute(args);
        System.exit(batchResult);
    }

    //***** protected method *****
    //***** private method *****
    private PropertyUtil obtainSingleton() {
        return Holder.propertyUtil;
    }

    private static final class Holder {

        private static PropertyUtil propertyUtil = new PropertyUtil(BATCH_PROP_FILE);
    }

    private int execute(String[] args) {

        // Setting Logging Source
        MDC.put(LOG_SOURCE_KEY, LOG_SOURCE_BATCH + HYPHEN + args[0]);

        int batchResult = 0;
        String logStart = "start BATCH ID: {} - {}";
        String logEnd = "end BATCH ID: {} - {}. Return code = {}";
        String batchName = CommonConstant.EMPTY;
        try {

            //Get flow manager
            AbstractBatch flowManager = (AbstractBatch) Class.forName(obtainSingleton().readProperty(args[0] + SUFFIX_CLASS)).newInstance();
            if (null == flowManager) {
                throw new ApplicationSystemException(ERROR_CLASS_NOT_FOUND);
            }

            batchName = flowManager.getClass().getName();

            logger.info(logStart, args[0], batchName);

            //Execute flow manager.
            flowManager.setMainArgs(args);
            batchResult = flowManager.execute();

        } catch (Exception e) {

            logger.error("{} - execute. Error occurred in BATCH ID = {}", this.getClass().getName(), args[0]);
            batchResult = END_CODE_ERROR;

        } finally {
            logger.info(logEnd, args[0], batchName, batchResult);
            MDC.remove(LOG_SOURCE_KEY);
        }

        return batchResult;
    }

    //***** call back method *****
    //***** getter and setter *****
    public void setLogger(Logger logger) {//Used for injection
        this.logger = logger;
    }
}
