package uk.co.sample.ws;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.jaxrs.PATCH;
import uk.co.log.LogWrapperImpl;
import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.exception.ApplicationDataException;
import uk.co.sample.logic.facade.AnnouncementCMTFacade;
import uk.co.sample.logic.service.RestAnnouncementListSearchRequestDTO;
import uk.co.sample.logic.service.RestAnnouncementListSearchReturnDTO;
import uk.co.sample.security.Operator;
import uk.co.sample.ws.base.WsOperatorUtil;

@Stateless
@Api(tags = { "Announcement APIs" })
@Path("/announcements")
public class AnnouncementApi {

    // ***** injection field *****
    @Inject
    private LogWrapperImpl        logger;
    @Inject
    private AnnouncementCMTFacade announcementCMTFacade;

    // ***** constructor *****
    // ***** public method *****
    @ApiOperation(value = "Fetches all Announcements")
    @ApiResponses({
            @ApiResponse(code = HttpStatus.SC_OK, message = "Success."),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Unable to process request."),
            @ApiResponse(code = HttpStatus.SC_NOT_ACCEPTABLE, message = "A logical error has been fired."),
            @ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "The request is invalid.")
    })
    @GET
    @Path("/read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAnnouncements(
        @QueryParam("status") String status,
        @QueryParam("text") String text,
        @QueryParam("offset") Integer offset,
        @QueryParam("pageSize") Integer pageSize) {

        Operator operator = WsOperatorUtil.createDefaultOperator();
        logger.logStart(operator, new Throwable().getStackTrace()[0].getMethodName());

        try {

            RestAnnouncementListSearchRequestDTO searchRequest = AnnouncementApiHelper.createRestAnnouncementListSearchRequestDTO(status, text, offset, pageSize);
            AnnouncementApiHelper.validateRestAnnouncementListSearchRequestDTO(searchRequest);
            RestAnnouncementListSearchReturnDTO returnDTO = announcementCMTFacade.callRestAnnouncementListSearch(searchRequest);

            return Response.status(HttpStatus.SC_OK).entity(doJson(returnDTO)).build();

        } finally {
            logger.logEnd(operator, new Throwable().getStackTrace()[0].getMethodName());
        }

    }

    @ApiOperation(value = "Patch an Announcement (demo using HttpMethod.PATCH)")
    @ApiResponses({
            @ApiResponse(code = HttpStatus.SC_OK, message = "Success."),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Unable to process request."),
            @ApiResponse(code = HttpStatus.SC_NOT_ACCEPTABLE, message = "A logical error has been fired."),
            @ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "The request is invalid.")
    })
    @PATCH
    @Path("/testpatch")
    @Produces(MediaType.APPLICATION_JSON)
    public String test(@QueryParam("status") String status) {

        Operator operator = WsOperatorUtil.createDefaultOperator();
        logger.logStart(operator, new Throwable().getStackTrace()[0].getMethodName());

        try {
            RestAnnouncementListSearchRequestDTO searchRequest = AnnouncementApiHelper.createRestAnnouncementListSearchRequestDTO(status, null, null, null);
            AnnouncementApiHelper.validateRestAnnouncementListSearchRequestDTO(searchRequest);
            RestAnnouncementListSearchReturnDTO returnDTO = announcementCMTFacade.callRestAnnouncementListSearch(searchRequest);

            return doJson(returnDTO);

        } finally {
            logger.logEnd(operator, new Throwable().getStackTrace()[0].getMethodName());
        }
    }

    // ***** protected method *****
    // ***** private method *****
    private String doJson(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
            mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

            return mapper.writeValueAsString(obj);

        } catch (Exception e) {
            throw new ApplicationDataException(SystemErrorCodeConstant.ERROR_DATA);
        }
    }
    // ***** getter and setter *****

}
