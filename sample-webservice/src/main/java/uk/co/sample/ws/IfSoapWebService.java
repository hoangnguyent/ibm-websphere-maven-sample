package uk.co.sample.ws;

import javax.jws.WebService;

import uk.co.sample.ws.base.ApplicationSoapWrapperException;
import uk.co.sample.ws.dto.WsAnnouncementListRequestDTO;
import uk.co.sample.ws.dto.WsAnnouncementListReturnDTO;

@WebService
public interface IfSoapWebService {

    public WsAnnouncementListReturnDTO callWsAnnouncementList(WsAnnouncementListRequestDTO wsRequest) throws ApplicationSoapWrapperException;
}
