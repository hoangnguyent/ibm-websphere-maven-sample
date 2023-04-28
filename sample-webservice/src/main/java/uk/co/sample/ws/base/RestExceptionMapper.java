package uk.co.sample.ws.base;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJBException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.exception.AbstractApplicationRuntimeException;
import uk.co.sample.exception.ApplicationCheckException;
import uk.co.sample.exception.ApplicationDataException;
import uk.co.sample.exception.ApplicationLogicException;
import uk.co.sample.exception.ApplicationSystemException;
import uk.co.sample.util.MessagePropertiesUtil;

@Provider
public class RestExceptionMapper implements ExceptionMapper<Throwable> {

    // ***** injection field *****
    // ***** constructor *****
    // ***** public method *****
    @Override
    public Response toResponse(Throwable e) {

        RestErrorData restErrorData = obtainRestErrorData(e);

        if (e instanceof ApplicationSystemException) {
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(restErrorData).type(MediaType.APPLICATION_JSON).build();

        } else if (e instanceof ApplicationDataException) {
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(restErrorData).type(MediaType.APPLICATION_JSON).build();

        } else if (e instanceof ApplicationLogicException) {
            return Response.status(HttpStatus.SC_NOT_ACCEPTABLE).entity(restErrorData).type(MediaType.APPLICATION_JSON).build();

        } else if (e instanceof ApplicationCheckException) {
            return Response.status(HttpStatus.SC_BAD_REQUEST).entity(restErrorData).type(MediaType.APPLICATION_JSON).build();

        } else if (e instanceof ApplicationRestException) {
            return Response.status(HttpStatus.SC_BAD_REQUEST).entity(restErrorData).type(MediaType.APPLICATION_JSON).build();

        } else if (e.getCause() instanceof AbstractApplicationRuntimeException || e.getCause() instanceof EJBException) {
            return toResponse(e.getCause());

        } else {// TODO: add handler for Security Exception
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity(restErrorData).type(MediaType.APPLICATION_JSON).build();
        }
    }

    // ***** protected method *****
    // ***** private method *****
    private RestErrorData obtainRestErrorData(Throwable t) {

        String exceptionClassName;
        Set<ErrorData> errors = new HashSet<>();

        if (t instanceof EJBException && t.getCause() instanceof AbstractApplicationRuntimeException) {

            return obtainRestErrorData(t.getCause());

        } else if (t instanceof AbstractRestException) {
            AbstractRestException ex = (AbstractRestException) t;
            errors.addAll(ex.getErrorDatas());

            exceptionClassName = t.getClass().getSimpleName();

        } else if (t instanceof AbstractApplicationRuntimeException) {
            AbstractApplicationRuntimeException ex = (AbstractApplicationRuntimeException) t;
            ErrorData errorData = new ErrorData(ex.getCode(), ex.getParams());
            errors.add(errorData);

            exceptionClassName = ex.getClass().getSimpleName();

        } else {
            ErrorData ed = new ErrorData(SystemErrorCodeConstant.ERROR_LOGIC, new String[] {});
            errors.add(ed);

            exceptionClassName = t.getClass().getSimpleName();

        }

        RestErrorData restErrorData = obtainErrorDetails(errors);
        restErrorData.setClassName(exceptionClassName);

        return restErrorData;
    }

    private RestErrorData obtainErrorDetails(Set<ErrorData> errorDataSet) {

        RestErrorData restErrorData = new RestErrorData();

        if (errorDataSet.isEmpty()) {
            return restErrorData;
        }

        for (ErrorData errorData : errorDataSet) {

            String code = errorData.getErrorCode();
            String detailMsg = MessagePropertiesUtil.getMessage(code, Arrays.asList(errorData.getParams()));
            if (StringUtils.isBlank(detailMsg)) {
                code = SystemErrorCodeConstant.DEFAULT_ERROR_MSG;
                detailMsg = MessagePropertiesUtil.getMessage(SystemErrorCodeConstant.DEFAULT_ERROR_MSG);
            }

            restErrorData.getCodes().add(code);
            restErrorData.getDetails().add(new ErrorDetails(code, detailMsg));
        }

        return restErrorData;
    }
    // ***** getter and setter *****
}
