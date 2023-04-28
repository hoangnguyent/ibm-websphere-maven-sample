package uk.co.sample.ws.processor;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJBException;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import uk.co.log.LogWrapperImpl;
import uk.co.sample.base.AbstractBaseRequestDTO;
import uk.co.sample.constant.CommonConstant;
import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.exception.AbstractApplicationRuntimeException;
import uk.co.sample.exception.ApplicationLogicException;
import uk.co.sample.security.Operator;
import uk.co.sample.ws.base.AbstractSoapException;
import uk.co.sample.ws.base.ApplicationSoapException;
import uk.co.sample.ws.base.ApplicationSoapWrapperException;
import uk.co.sample.ws.base.ErrorData;
import uk.co.sample.ws.base.IfSoapBaseRequestDTO;
import uk.co.sample.ws.base.IfSoapBaseReturnDTO;
import uk.co.sample.ws.base.WsOperatorUtil;
import uk.co.sample.ws.util.ErrorDataConvertUtil;

/**
 * <h3>
 *  AbstractTemplateWsProcessor
 * </h3>
 *
 * @author hoangnguyen
 * @param <WsIn>
 * @param <WsOut>
 * @param <BizIn>
 * @param <BizOut>
 * @since 2021/07/23
 */
public abstract class AbstractTemplateWsProcessor<WsIn extends IfSoapBaseRequestDTO, WsOut extends IfSoapBaseReturnDTO, BizIn extends AbstractBaseRequestDTO, BizOut> {

    @Inject
    protected LogWrapperImpl logger;

    //***** constructor *****
    //***** public method *****
    /**
     * Entry point method.<br>
     * <ol>
     * <li>createBizRequestDTO
     * <li>callBiz
     * <li>createWSReturnDTO
     * </ol>
     * <br>
     * @param wsRequestDTO requestDTO of WebService
     * @return returnDTO of Business
     * @throws ApplicationSoapWrapperException
     */
    public WsOut execute(WsIn wsRequestDTO) throws ApplicationSoapWrapperException {
        Operator operator = null;
        WsOut wsReturnDTO = null;
        String methodName = "execute";

        // Initiate Operator
        methodName = "obtainOperator";
        operator = obtainOperator(wsRequestDTO);

        try {

            logger.info(operator, new Throwable().getStackTrace()[0].getMethodName(), "WebService START");

            // Validate RequestDTO
            methodName = "validate";
            ApplicationSoapException check = new ApplicationSoapException();
            check = wsRequestDTO.validate(check);
            if (check.hasError()) {
                throw check;
            }

            // Create Business request
            methodName = "createBizRequestDTO";
            BizIn bizRequestDTO = createBizRequestDTO(wsRequestDTO, operator);

            // Invoke Business process
            methodName = "callBiz";
            BizOut bizReturnDTO = callBiz(bizRequestDTO);

            // Create WSReturnDTO
            methodName = "createWSReturnDTO";
            wsReturnDTO = createWSReturnDTO(bizReturnDTO);

            methodName = "execute";
        } catch (Exception e) {
            throw handleException(operator, e, methodName);
        } finally {
            logger.info(operator, new Throwable().getStackTrace()[0].getMethodName(), "WebService END");
        }
        return wsReturnDTO;
    }

    //***** protected method *****
    /**
     * obtainOperator<br>
     * @param wsRequestDTO ReturnDTO of WebService
     * @return Operator
     */
    protected Operator obtainOperator(WsIn wsRequestDTO) {

        return WsOperatorUtil.createDefaultOperator();

    }

    protected abstract BizIn createBizRequestDTO(WsIn wsRequestDTO, Operator operator);

    protected abstract BizOut callBiz(BizIn businessRequestDTO);

    protected abstract WsOut createWSReturnDTO(BizOut businessReturnDTO);

    ApplicationSoapWrapperException handleException(Operator operator, Throwable t, String methodName) {
        if (t == null) {
            t = new ApplicationLogicException(SystemErrorCodeConstant.ERROR_LOGIC_WITH_PARAM, new String[] { "throwable object is null at handleException" });
        }

        String errorMessage;
        String exceptionClassName;
        String errorDataMessage;

        if (t instanceof EJBException && t.getCause() instanceof AbstractApplicationRuntimeException) {
            AbstractApplicationRuntimeException ex = (AbstractApplicationRuntimeException) t.getCause();
            StackTraceElement[] st = ex.getStackTrace();
            String msg = ex.getClass().getSimpleName() + ": " + ex.getMessage() + (st.length > 0 ? " - " + st[0].toString() : CommonConstant.EMPTY);
            logger.info(operator, methodName, msg);
            ErrorData errorData = new ErrorData(ex.getCode(), ex.getParams());
            Set<ErrorData> errors = new HashSet<>();
            errors.add(errorData);

            errorMessage = ex.getMessage();
            exceptionClassName = ex.getClass().getSimpleName();
            errorDataMessage = ErrorDataConvertUtil.convertErrorDataToString(errors);

        } else if (t instanceof AbstractApplicationRuntimeException) {
            AbstractApplicationRuntimeException ex = (AbstractApplicationRuntimeException) t;
            StackTraceElement[] st = ex.getStackTrace();
            String msg = ex.getClass().getSimpleName() + ": " + ex.getMessage() + (st.length > 0 ? " - " + st[0].toString() : CommonConstant.EMPTY);
            logger.info(operator, methodName, msg);
            ErrorData errorData = new ErrorData(ex.getCode(), ex.getParams());
            Set<ErrorData> errors = new HashSet<>();
            errors.add(errorData);

            errorMessage = ex.getMessage();
            exceptionClassName = ex.getClass().getSimpleName();
            errorDataMessage = ErrorDataConvertUtil.convertErrorDataToString(errors);

        } else if (t instanceof ApplicationSoapException) {
            AbstractSoapException ex = (AbstractSoapException) t;
            StackTraceElement[] st = ex.getStackTrace();
            String msg = ex.getClass().getSimpleName() + ": " + ex.getMessage() + (st.length > 0 ? " - " + st[0].toString() : CommonConstant.EMPTY);
            logger.info(operator, methodName, msg);
            Set<ErrorData> errors = ex.getErrorDatas();

            errorMessage = convertToErrorMessage(ex);
            exceptionClassName = ex.getClass().getName();
            errorDataMessage = ErrorDataConvertUtil.convertErrorDataToString(errors);

        } else {
            logger.error(operator, methodName, t);
            Set<ErrorData> errors = new HashSet<>();
            ErrorData ed = new ErrorData(SystemErrorCodeConstant.ERROR_LOGIC, new String[] {});
            errors.add(ed);

            errorMessage = t.getMessage();
            exceptionClassName = t.getClass().getName();
            errorDataMessage = ErrorDataConvertUtil.convertErrorDataToString(errors);

        }
        logger.warn(operator, methodName, "WebServiceFault:" + errorMessage + ", " + exceptionClassName + ", " + errorDataMessage.replace("\n", CommonConstant.EMPTY));
        return new ApplicationSoapWrapperException(errorMessage, exceptionClassName, errorDataMessage);
    }

    //***** private method *****
    private static String convertToErrorMessage(AbstractSoapException ex) {
        String result = convertToErrorCodeString(ex.getErrorDatas());

        if (!StringUtils.isBlank(ex.getMessage())) {
            return result + "\n" + ex.getMessage();
        }
        Throwable t = ex.getCause();
        if (t != null && !(t instanceof AbstractSoapException)) {
            return result + "\n" + t.getMessage();
        } else {
            return result;
        }
    }

    private static String convertToErrorCodeString(Set<ErrorData> errorDataSet) {
        StringBuilder buf = new StringBuilder();
        buf.append("Error:[");
        boolean first = true;
        for (ErrorData errorData : errorDataSet) {
            if (!first) {
                buf.append("|");
            }
            buf.append(errorData.getErrorCode());
            first = false;
        }
        buf.append("]");
        return buf.toString();
    }

    //***** call back method *****
    //***** getter and setter *****
}
