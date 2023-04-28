package uk.co.sample.ws;

import javax.inject.Inject;
import javax.jws.WebService;

import uk.co.sample.ws.base.ApplicationSoapWrapperException;
import uk.co.sample.ws.dto.WsAnnouncementListRequestDTO;
import uk.co.sample.ws.dto.WsAnnouncementListReturnDTO;
import uk.co.sample.ws.processor.WsAnnouncementListProcessor;

@WebService(endpointInterface = "uk.co.sample.ws.IfSoapWebService", serviceName = "AE")
public class AnnouncementSoapWebService implements IfSoapWebService {

    // ***** injection field *****
    @Inject
    private WsAnnouncementListProcessor wsAnnouncementListProcessor;

    // ***** constructor *****
    // ***** public method *****
    @Override
    public WsAnnouncementListReturnDTO callWsAnnouncementList(WsAnnouncementListRequestDTO wsRequest) throws ApplicationSoapWrapperException {
        return wsAnnouncementListProcessor.execute(wsRequest);
    }
}
