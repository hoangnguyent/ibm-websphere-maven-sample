package uk.co.sample.ws;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import uk.co.sample.constant.DBFields;
import uk.co.sample.constant.Status;
import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.logic.service.RestAnnouncementListSearchRequestDTO;
import uk.co.sample.security.Operator;
import uk.co.sample.util.CoreCodeValidateUtil;
import uk.co.sample.ws.base.ApplicationRestException;
import uk.co.sample.ws.base.WsOperatorUtil;

public class AnnouncementApiHelper {

    // ***** constructor *****
    private AnnouncementApiHelper() {
        // Do nothing
    }

    // ***** public method *****
    public static RestAnnouncementListSearchRequestDTO createRestAnnouncementListSearchRequestDTO(String status, String text, Integer offset, Integer pageSize) {
        Operator operator = WsOperatorUtil.createDefaultOperator();
        RestAnnouncementListSearchRequestDTO request = new RestAnnouncementListSearchRequestDTO(operator);
        request.setStatus(status);
        request.setText(text);

        // for paging
        request.setOffset(offset);
        request.setPageSize(pageSize);
        return request;
    }

    public static void validateRestAnnouncementListSearchRequestDTO(RestAnnouncementListSearchRequestDTO request) {

        ApplicationRestException ex = new ApplicationRestException();

        if (StringUtils.isBlank(request.getStatus())) {
            ex.addErrorCode(SystemErrorCodeConstant.REQUIRED, "status");

        } else if (!EnumUtils.isValidEnum(Status.class, request.getStatus())
            && !EnumUtils.isValidEnum(Status.class, request.getStatus().toLowerCase())
            && !EnumUtils.isValidEnum(Status.class, request.getStatus().toUpperCase())) {

            ex.addErrorCode(SystemErrorCodeConstant.INVALID, "status");

        }

        if (StringUtils.isNotBlank(request.getText())) {
            boolean isSearchLikeConditionValid = CoreCodeValidateUtil.isValidAsLike(DBFields.ANNOUNCEMENT_SAMPLE.TEXT, request.getText());
            if (!isSearchLikeConditionValid) {
                ex.addErrorCode(SystemErrorCodeConstant.INVALID, "text");
            }
        }

        if (null != request.getPageSize() && null != request.getOffset()) {
            boolean isPageSizePositiveInt = CoreCodeValidateUtil.isPositiveInteger(request.getPageSize().toString());
            if (!isPageSizePositiveInt) {
                ex.addErrorCode(SystemErrorCodeConstant.INVALID, "pageSize");
            }

            boolean isOffsetSizePositiveInt = CoreCodeValidateUtil.isPositiveInteger(request.getOffset().toString());
            if (!isOffsetSizePositiveInt) {
                ex.addErrorCode(SystemErrorCodeConstant.INVALID, "offset");
            }
        }

        if (!ex.getErrorCode().isEmpty()) {
            throw ex;
        }
    }
    // ***** protected method *****
    // ***** private method *****
    // ***** call back method *****
    // ***** getter and setter *****

}
