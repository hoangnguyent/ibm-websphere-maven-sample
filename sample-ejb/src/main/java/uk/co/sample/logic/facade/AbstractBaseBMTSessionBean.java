package uk.co.sample.logic.facade;

import static javax.transaction.Status.STATUS_ACTIVE;
import static javax.transaction.Status.STATUS_NO_TRANSACTION;
import static uk.co.sample.constant.SystemErrorCodeConstant.ERROR_LOGIC;
import static uk.co.sample.constant.SystemErrorCodeConstant.ERROR_SYSTEM;

import javax.annotation.Resource;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import uk.co.log.LogWrapperImpl;
import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.exception.AbstractApplicationRuntimeException;
import uk.co.sample.exception.ApplicationLogicException;
import uk.co.sample.exception.ApplicationSystemException;
import uk.co.sample.security.Operator;

public abstract class AbstractBaseBMTSessionBean {

    protected static final int  DEFAULT_TIMEOUT_SECOND = 900;
    private static final String BEGIN_TRAN             = "Begin (BMT)transaction.";
    private static final String COMMIT_LOG             = "Commit (BMT)transaction.";
    private static final String ROLLBACK_LOG           = "Rollback (BMT)transaction.";
    private static final String END_TRANSACTION        = "endTransaction";

    @Resource(name = "jdbc/AEDBDS")
    protected UserTransaction   userTransaction;

    //***** constructor *****
    //***** public method *****
    //***** protected method *****
    protected abstract LogWrapperImpl getLogger();

    /**
     * begin transaction with TransactionTimeout value in BMT.<br>
     * Helper method for sub-classes.
     * 
     * @param operator Operator
     * @param methodName String
     * @param timeoutSeconds - TransactionTimeout value (seconds)
     */
    protected void beginTransactionWithTimeout(Operator operator, String methodName, int timeoutSeconds) {
        try {
            getLogger().trace(operator, methodName, BEGIN_TRAN);
            userTransaction.setTransactionTimeout(timeoutSeconds);
            userTransaction.begin();
        } catch (Exception e) {
            throw new ApplicationSystemException(SystemErrorCodeConstant.ERROR_SYSTEM, e);
        }
    }

    /**
     * commit or rollback the transaction.<br>
     * if transaction status is Status.STATUS_ACTIVE then commit the transaction, else rollback the transaction.
     * 
     * Helper method for sub-classes.
     * 
     * @param operator Operator
     * @param methodName String
     * @param hasException
     */
    protected void endTransaction(Operator operator, String methodName, boolean hasException) {
        try {
            if (userTransaction.getStatus() == STATUS_ACTIVE) {
                try {
                    getLogger().trace(operator, methodName, COMMIT_LOG);
                    userTransaction.commit();
                } catch (Exception e) {
                    doRollback(operator, methodName, userTransaction);
                    throw e;
                }
            } else if (userTransaction.getStatus() != STATUS_NO_TRANSACTION) {
                doRollback(operator, methodName, userTransaction);
                if (!hasException) {
                    throw new ApplicationSystemException(ERROR_SYSTEM);
                }
            }
        } catch (AbstractApplicationRuntimeException e) {
            getLogger().error(operator, new Throwable().getStackTrace()[0].getMethodName(), e);
            throw e;
        } catch (Exception e) {
            getLogger().error(operator, new Throwable().getStackTrace()[0].getMethodName(), e);
            throw new ApplicationSystemException(ERROR_SYSTEM, e);
        }
    }

    /**
     * Handle exception caused in business logics.<br>
     * Helper method for sub-classes.
     *
     * @param Operator operator
     * @param e Throwable
     * @param methodName String
     * @return AbstractApplicationRuntimeException
     */
    protected AbstractApplicationRuntimeException handleException(Operator operator, Throwable e, String methodName) {

        // Rollback transaction.
        try {

            userTransaction.setRollbackOnly();

        } catch (IllegalStateException | SystemException e1) {

            // IllegalStateException caused in BMT bean.
            // Force rollback.
            try {
                userTransaction.rollback();
            } catch (Exception e2) {
                // Not catch rollback exception.
            }
        }

        // Repackage exception.
        if (null == e) {

            return new ApplicationLogicException(ERROR_LOGIC);

        } else if (e instanceof AbstractApplicationRuntimeException) {

            getLogger().error(operator, methodName, "runtime exception");
            return (AbstractApplicationRuntimeException) e;

        } else if (e instanceof Error) {
            // This exception is not catched in business logic.
            getLogger().error(operator, methodName, "uncatch error");

            // wrap exception by ApplicationSystemException, then throw.
            return new ApplicationSystemException(ERROR_SYSTEM, e);

        } else if (e.getCause() instanceof AbstractApplicationRuntimeException) {

            return handleException(operator, e.getCause(), methodName);

        } else {
            // This exception is not catched in business logic.
            getLogger().error(operator, methodName, "uncatch exception");

            // wrap exception by ApplicationSystemException, then throw.
            return new ApplicationSystemException(ERROR_LOGIC, e);
        }
    }

    //***** private method *****

    /**
     * Quietly rollback.
     * <br>
     * @param operator Operator
     * @param methodName String
     * @param userTransaction
     */
    private void doRollback(Operator operator, String methodName, UserTransaction userTransaction) {
        try {
            getLogger().trace(operator, methodName, ROLLBACK_LOG);
            userTransaction.rollback();
        } catch (Exception e) {
            getLogger().error(operator, methodName, END_TRANSACTION, e);
        }
    }
    //***** call back method *****
    //***** getter and setter *****

}
