package test.junit.api;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.sample.logic.facade.AnnouncementCMTFacade;
import uk.co.sample.logic.service.RestAnnouncementListSearchRequestDTO;
import uk.co.sample.logic.service.RestAnnouncementListSearchReturnDTO;
import uk.co.sample.ws.AnnouncementApi;

@RunWith(MockitoJUnitRunner.class)
public class TestSampleApi {

    @Mock
    private AnnouncementCMTFacade facade;

    @InjectMocks
    private AnnouncementApi       api;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void readAnnouncement_whenThereIsNoRecord_ThenNormallyEnd() {

        // Expect
        when(facade.callRestAnnouncementListSearch(any(RestAnnouncementListSearchRequestDTO.class)))
            .thenReturn(new RestAnnouncementListSearchReturnDTO());

        // Prepare input parameters
        String status = "deleted";
        String text = "";

        // Verify
        Response response = api.searchAnnouncements(status, text, null, null);
        Assert.assertEquals(response.getStatus(), HttpStatus.SC_OK);
        Assert.assertEquals(response.getEntity(), "{\"AnnouncementInstanceDetails\":[]}");

    }

}
