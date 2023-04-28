package uk.co.sample.ws.processor;

import javax.ejb.Stateless;
import javax.inject.Inject;

import uk.co.sample.constant.Status;
import uk.co.sample.logic.facade.AnnouncementCMTFacade;
import uk.co.sample.logic.service.SoapAnnouncementListSearchRequestDTO;
import uk.co.sample.logic.service.SoapAnnouncementListSearchReturnDTO;
import uk.co.sample.security.Operator;
import uk.co.sample.ws.dto.WsAnnouncementListBean;
import uk.co.sample.ws.dto.WsAnnouncementListRequestDTO;
import uk.co.sample.ws.dto.WsAnnouncementListReturnDTO;

@Stateless
public class WsAnnouncementListProcessor extends AbstractTemplateWsProcessor<WsAnnouncementListRequestDTO, WsAnnouncementListReturnDTO, SoapAnnouncementListSearchRequestDTO, SoapAnnouncementListSearchReturnDTO> {

    // ***** injection field *****
    @Inject
    private AnnouncementCMTFacade announcementCMTFacade;

    // ***** constructor *****
    public WsAnnouncementListProcessor() {
        // Do nothing
    }

    //***** public method *****
    //***** protected method *****
    @Override
    protected SoapAnnouncementListSearchReturnDTO callBiz(SoapAnnouncementListSearchRequestDTO businessRequestDTO) {
        return announcementCMTFacade.callSoapAnnouncementListSearch(businessRequestDTO);
    }

    @Override
    protected WsAnnouncementListReturnDTO createWSReturnDTO(SoapAnnouncementListSearchReturnDTO businessReturnDTO) {
        WsAnnouncementListReturnDTO wsReturnDTO = new WsAnnouncementListReturnDTO();
        businessReturnDTO.getAnnouncements().forEach(item -> {
            WsAnnouncementListBean announcement = new WsAnnouncementListBean();
            announcement.setAnnouncementSampleKey(item.getAnnouncementSampleKey());
            announcement.setText(item.getText());
            announcement.setVersion(item.getVersion());

            wsReturnDTO.getAnnouncements().add(announcement);
        });

        return wsReturnDTO;
    }

    @Override
    protected SoapAnnouncementListSearchRequestDTO createBizRequestDTO(WsAnnouncementListRequestDTO wsRequestDTO, Operator operator) {
        SoapAnnouncementListSearchRequestDTO businessRequestDTO = new SoapAnnouncementListSearchRequestDTO(operator);
        businessRequestDTO.setStatus(Status.valueOf(wsRequestDTO.getStatus()));
        businessRequestDTO.setText(wsRequestDTO.getText());

        return businessRequestDTO;
    }
}
