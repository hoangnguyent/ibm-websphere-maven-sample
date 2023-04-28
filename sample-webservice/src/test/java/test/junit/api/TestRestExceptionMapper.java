package test.junit.api;

import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.exception.ApplicationCheckException;
import uk.co.sample.exception.ApplicationDataException;
import uk.co.sample.exception.ApplicationLogicException;
import uk.co.sample.exception.ApplicationSystemException;
import uk.co.sample.ws.base.RestExceptionMapper;

@RunWith(MockitoJUnitRunner.class)
public class TestRestExceptionMapper {

    @Test
    public void whenApplicationSystemException_ThenStatusIs500() {

        ApplicationSystemException exception = new ApplicationSystemException(SystemErrorCodeConstant.DEFAULT_ERROR_MSG);

        Response response = new RestExceptionMapper().toResponse(exception);
        Assert.assertEquals(response.getStatus(), HttpStatus.SC_INTERNAL_SERVER_ERROR);

    }

    @Test
    public void whenApplicationDataException_ThenStatusIs500() {

        ApplicationDataException exception = new ApplicationDataException(SystemErrorCodeConstant.DEFAULT_ERROR_MSG);

        Response response = new RestExceptionMapper().toResponse(exception);
        Assert.assertEquals(response.getStatus(), HttpStatus.SC_INTERNAL_SERVER_ERROR);

    }

    @Test
    public void whenApplicationLogicException_ThenStatusIs406() {

        ApplicationLogicException exception = new ApplicationLogicException(SystemErrorCodeConstant.DEFAULT_ERROR_MSG);

        Response response = new RestExceptionMapper().toResponse(exception);
        Assert.assertEquals(response.getStatus(), HttpStatus.SC_NOT_ACCEPTABLE);

    }

    @Test
    public void whenApplicationCheckException_ThenStatusIs400() {

        ApplicationCheckException exception = new ApplicationCheckException(SystemErrorCodeConstant.DEFAULT_ERROR_MSG);

        Response response = new RestExceptionMapper().toResponse(exception);
        Assert.assertEquals(response.getStatus(), HttpStatus.SC_BAD_REQUEST);

    }

    // TODO: Test for security exception

    @Test
    public void whenOtherException_ThenStatusIs500() {

        Exception exception = new Exception("Something went wrong!");

        Response response = new RestExceptionMapper().toResponse(exception);
        Assert.assertEquals(response.getStatus(), HttpStatus.SC_INTERNAL_SERVER_ERROR);

    }
}
