package test.integration.soap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.sample.constant.SystemErrorCodeConstant;

@RunWith(MockitoJUnitRunner.class)
public class TestSampleSoapIT {

    @Test
    public void readAnnouncement_whenInputInvalid_ThenStatusIsBadRequest() {

        // Prepare input parameters
        String invalidStatus = "invalid";

        // Make a call
        WsAnnouncementListRequestDTO wsRequest = new WsAnnouncementListRequestDTO();
        wsRequest.setStatus(invalidStatus);
        IfSoapWebService ws = (new AE()).getPort(IfSoapWebService.class);
        try {
            ws.callWsAnnouncementList(wsRequest);
        } catch (ApplicationSoapWrapperException_Exception e) {
            //Verify
            Assert.assertTrue(e.getFaultInfo().getErrorDataMessage().contains(SystemErrorCodeConstant.INVALID));
        }

    }

    @Test
    public void readAnnouncement_whenInputValid_ThenReturnData() {

        // Prepare input parameters
        String validStatus = "DELETED";

        // Make a call
        WsAnnouncementListRequestDTO wsRequest = new WsAnnouncementListRequestDTO();
        wsRequest.setStatus(validStatus);
        IfSoapWebService ws = (new AE()).getPort(IfSoapWebService.class);
        WsAnnouncementListReturnDTO wsReturn;
        try {
            wsReturn = ws.callWsAnnouncementList(wsRequest);

            // Verify
            Assert.assertTrue(!wsReturn.getAnnouncements().isEmpty());

        } catch (ApplicationSoapWrapperException_Exception e) {
            Assert.assertFalse(true);
        }

    }

}
