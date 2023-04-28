package uk.co.sample.ws.dto;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import uk.co.sample.constant.DBFields;
import uk.co.sample.constant.Status;
import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.util.CoreCodeValidateUtil;
import uk.co.sample.ws.base.ApplicationSoapException;
import uk.co.sample.ws.base.SoapBaseRequestDTO;

public class WsAnnouncementListRequestDTO extends SoapBaseRequestDTO {

    private static final long serialVersionUID = 1114211869203933915L;

    private String            status;
    private String            text;

    // ***** constructor *****
    // ***** public method *****
    @Override
    public ApplicationSoapException validate(ApplicationSoapException ex) {

        return doValidate(ex);

    }

    // ***** protected method *****
    // ***** private method *****
    private ApplicationSoapException doValidate(ApplicationSoapException ex) {
        if (StringUtils.isBlank(status)) {
            ex.addErrorCode(SystemErrorCodeConstant.REQUIRED, "status");
        } else if (!EnumUtils.isValidEnum(Status.class, status)) {
            ex.addErrorCode(SystemErrorCodeConstant.INVALID, "status");
        }

        if (StringUtils.isBlank(text)) {
            boolean isSearchLikeConditionValid = CoreCodeValidateUtil.isValidAsLike(DBFields.ANNOUNCEMENT_SAMPLE.TEXT, text);
            if (!isSearchLikeConditionValid) {
                ex.addErrorCode(SystemErrorCodeConstant.INVALID, "text");
            }
        }
        return ex;
    }

    // ***** call back method *****
    // ***** getter and setter *****
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WsAnnouncementListRequestDTO [status=" + status + "]";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
