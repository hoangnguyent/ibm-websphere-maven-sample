package uk.co.sample.base;

import static uk.co.sample.constant.BatchConstant.BATCH_PROP_FILE;
import static uk.co.sample.constant.BatchErrorCodeConstant.ERROR_BATCH_ID_NOT_FOUND;

import uk.co.sample.constant.BatchConstant;
import uk.co.sample.exception.ApplicationLogicException;
import uk.co.sample.security.Operator;
import uk.co.sample.util.ProcessingTimeUtil;
import uk.co.sample.util.PropertyUtil;

public abstract class AbstractBatch {

    private String   batchId;
    private String[] mainArgs = null;
    private Operator operator = null;

    // ***** constructor *****
    // ***** public method *****
    /**
     * initialize Operator by batchId.
     * <br>
     * @return initialized Operator
     */
    private void initializeOperator() {
        if (getBatchId() == null) {
            throw new ApplicationLogicException(ERROR_BATCH_ID_NOT_FOUND);
        }

        String batchUserId = obtainSingleton().readProperty(getBatchId() + BatchConstant.SUFFIX_USER);
        this.operator = new Operator();
        this.operator.setTracingId(batchUserId);
        this.operator.setTimeZone(ProcessingTimeUtil.getSystemTimeZone());
    }

    /**
     * Execute any business logic.<br>
     * sub-class must implement this method for specific use.
     * @return Return code.
     */
    public abstract int execute();

    /**
     * Set arguments and initialize Operator.<br>
     * arguments set this method do not include batchId.<br>
     *
     * @param strings arguments
     */
    public void setMainArgs(String[] strings) {

        String[] str = new String[strings.length - 1];

        for (int i = 0; i < strings.length; i++) {

            if (0 == i) {
                batchId = strings[i];
            } else {
                str[i - 1] = strings[i];
            }
        }

        mainArgs = str;

    }

    // ***** protected method *****
    // ***** private method *****
    private static PropertyUtil obtainSingleton() {
        return Holder.propertyUtil;
    }

    private static final class Holder {

        private static PropertyUtil propertyUtil = new PropertyUtil(BATCH_PROP_FILE);
    }

    // ***** getter and setter *****
    /**
     * Returns the mainArgs.<br>
     * @return mainArgs[].
     */
    public String[] getMainArgs() {
        return mainArgs;
    }

    /**
     * Returns batch id.<br>
     * @return batchId.
     */
    public String getBatchId() {
        return batchId;
    }

    public Operator getOperator() {
        if (operator == null) {
            initializeOperator();
        }
        return operator;
    }

    //***** injection field *****
    //***** private method *****

}
