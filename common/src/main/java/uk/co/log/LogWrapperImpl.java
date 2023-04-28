package uk.co.log;

import static uk.co.sample.constant.CommonConstant.COLON;
import static uk.co.sample.constant.CommonConstant.EMPTY;
import static uk.co.sample.constant.CommonConstant.NUMBER_SIGN;
import static uk.co.sample.constant.CommonConstant.WHITE_SPACE;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import uk.co.sample.security.Operator;

public class LogWrapperImpl {

    private static final String POST_FIX_START = EMPTY;
    private static final String POST_FIX_END   = EMPTY;
    private Logger              logger;
    private String              className;

    public LogWrapperImpl(Logger logger, String className) {
        this.logger = logger;
        this.className = className;
    }

    public void logStart(Operator operator, String methodName) {
        String wrappedMsg = wrapMessage(operator, className, methodName, POST_FIX_START);
        logger.info(wrappedMsg);
    }

    public void logEnd(Operator operator, String methodName) {
        String wrappedMsg = wrapMessage(operator, className, methodName, POST_FIX_END);
        logger.info(wrappedMsg);
    }

    public void trace(Operator operator, String methodName, String message, Object... paramVarArgs) {
        String wrappedMsg = wrapMessage(operator, className, methodName, message);
        logger.trace(wrappedMsg, paramVarArgs);
    }

    public void debug(Operator operator, String methodName, String message, Object... paramVarArgs) {
        String wrappedMsg = wrapMessage(operator, className, methodName, message);
        logger.debug(wrappedMsg, paramVarArgs);
    }

    public void warn(Operator operator, String methodName, String message, Object... paramVarArgs) {
        String wrappedMsg = wrapMessage(operator, className, methodName, message);
        logger.warn(wrappedMsg, paramVarArgs);
    }

    public void info(Operator operator, String methodName, String message, Object... paramVarArgs) {
        String wrappedMsg = wrapMessage(operator, className, methodName, message);
        logger.info(wrappedMsg, paramVarArgs);
    }

    public void error(Operator operator, String methodName, String message, Object... paramVarArgs) {
        String wrappedMsg = wrapMessage(operator, className, methodName, message);
        logger.error(wrappedMsg, paramVarArgs);
    }

    public void error(Operator operator, String methodName, String message, Throwable t) {
        String wrappedMsg = wrapMessage(operator, className, methodName, message);
        logger.error(wrappedMsg, t);
    }

    public void error(Operator operator, String methodName, Throwable t) {
        String wrappedMsg = wrapMessage(operator, className, methodName, EMPTY);
        logger.error(wrappedMsg, t);
    }

    private String wrapMessage(Operator operator, String className, String methodName, String message) {
        if (operator == null) {
            return className + NUMBER_SIGN + methodName + (StringUtils.isNotBlank(message) ? COLON + message : EMPTY);
        } else if (StringUtils.isNotBlank(operator.getTracingId())) {
            return WHITE_SPACE + operator.getTracingId() + WHITE_SPACE + className + NUMBER_SIGN + methodName + (StringUtils.isNotBlank(message) ? COLON + message : EMPTY);
        } else {
            return className + NUMBER_SIGN + methodName + (StringUtils.isNotBlank(message) ? COLON + message : EMPTY);
        }
    }
}
