package uk.co.sample.logic.facade;

import static uk.co.sample.constant.SystemErrorCodeConstant.ERROR_LOGIC;
import static uk.co.sample.constant.SystemErrorCodeConstant.ERROR_SYSTEM;

import javax.annotation.Resource;
import javax.ejb.EJBContext;

import uk.co.log.LogWrapperImpl;
import uk.co.sample.exception.AbstractApplicationRuntimeException;
import uk.co.sample.exception.ApplicationLogicException;
import uk.co.sample.exception.ApplicationSystemException;
import uk.co.sample.security.Operator;

/**
 * <H3>
 * AbstractBaseCMTSessionBean</H3>
 * 
 * @author hoangnguyen
 * @since 2021/04/29
 */
public abstract class AbstractBaseCMTSessionBean {

    private static final String CMT_ROLLBACK_LOG       = "Rollback (CMT)transaction.";
    private static final String BMT_FORCE_ROLLBACK_LOG = "Forced rollback (BMT)transaction.";

    @Resource(name = "java:comp/EJBContext")
    private EJBContext          ejbContext;

    // ***** constructor *****
    // ***** public method *****
    // ***** protected method *****
    protected abstract LogWrapperImpl getLogger();
    /**
     * Handle exception caused in business logics.<br>
     * Helper method for sub-classes.
     * 
     * @param operator Operator
     * @param e Throwable
     * @param methodName String
     * @return AbstractApplicationRuntimeException
     */
    protected AbstractApplicationRuntimeException handleException(Operator operator, Throwable e, String methodName) {

        try {

            getLogger().trace(operator, methodName, CMT_ROLLBACK_LOG);
            ejbContext.setRollbackOnly();

        } catch (IllegalStateException e1) {

            // IllegalStateException caused in BMT bean.
            try {
                getLogger().trace(operator, methodName, BMT_FORCE_ROLLBACK_LOG);
                ejbContext.getUserTransaction().rollback();
            } catch (Exception e2) {
                // Not catch rollback exception.
            }
        }

        // Repackage exception.
        if (null == e) {

            return new ApplicationLogicException(ERROR_LOGIC);

        } else if (e instanceof AbstractApplicationRuntimeException) {

            getLogger().error(operator, methodName, "runtime exception", e);
            return (AbstractApplicationRuntimeException) e;

        } else if (e instanceof Error) {
            // This exception is not catched in business logic.
            getLogger().error(operator, methodName, "uncatch error", e);

            // wrap exception by ApplicationSystemException, then throw.
            return new ApplicationSystemException(ERROR_SYSTEM, e);

        } else if (e.getCause() instanceof AbstractApplicationRuntimeException) {

            return handleException(operator, e.getCause(), methodName);

        } else {
            // This exception is not catched in business logic.
            getLogger().error(operator, methodName, "uncatch exception", e);

            // wrap exception by ApplicationSystemException, then throw.
            return new ApplicationSystemException(ERROR_LOGIC, e);
        }
    }

    // ***** private method *****
    // ***** call back method *****
    // ***** getter and setter *****

}
