package test.integration.api;

import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.ws.base.RestErrorData;

@RunWith(MockitoJUnitRunner.class)
public class TestSampleApiIT {

    private static final String BASE_URL = "http://localhost:9177/sample-webservice/api/announcements";

    @Test
    public void readAnnouncement_whenInputInvalid_ThenStatusIsBadRequest() {

        // Prepare input parameters
        String invalidStatus = "invalid";
        String overLengthText = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789over";

        // Make a call
        RestErrorData restErrorData = given().queryParam("status", invalidStatus)
            .queryParam("text", overLengthText)
            .header("token", "nothing")
            .when().get(BASE_URL + "/read")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .extract().as(RestErrorData.class);

        // Verify
        Assert.assertEquals(restErrorData.getCodes().size(), 2);
        Assert.assertTrue(restErrorData.getCodes().contains(SystemErrorCodeConstant.INVALID));
        Assert.assertEquals(restErrorData.getDetails().size(), 2);
        Assert.assertTrue(restErrorData.getDetails().get(0).getMsg().contains("status"));
        Assert.assertTrue(restErrorData.getDetails().get(1).getMsg().contains("text"));
    }

}
