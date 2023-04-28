package uk.co.sample.logic.manager;

import java.util.Arrays;
import java.util.List;

import uk.co.sample.constant.Status;
import uk.co.sample.logic.service.AnnouncementDeleteServiceRequestDTO;
import uk.co.sample.logic.service.AnnouncementForArchiveListSearchRequestDTO;
import uk.co.sample.logic.service.AnnouncementForDeleteListSearchRequestDTO;
import uk.co.sample.logic.service.AnnouncementForDeleteSearchBean;
import uk.co.sample.logic.service.AnnouncementForProcessListSearchRequestDTO;
import uk.co.sample.security.Operator;

public class AnnouncementManagerHelper {

    // ***** injection field *****
    // ***** constructor *****
    private AnnouncementManagerHelper() {
        // Do nothing
    }
    // ***** public method *****
    public static AnnouncementArchiveRequestDTO createAnnouncementArchiveRequestDTO(Operator operator) {
        AnnouncementArchiveRequestDTO managerRequest = new AnnouncementArchiveRequestDTO(operator);
        managerRequest.setStatus(Status.SUCCEEDED);

        return managerRequest;
    }

    public static BatchAnnouncementDeleteReturnDTO createAnnouncementArchiveReturnDTO() {
        return new BatchAnnouncementDeleteReturnDTO();
    }

    public static AnnouncementForArchiveListSearchRequestDTO createAnnouncementForArchiveListSearchRequestDTO(AnnouncementArchiveRequestDTO managerRequest) {
        AnnouncementForArchiveListSearchRequestDTO searchRequest = new AnnouncementForArchiveListSearchRequestDTO(managerRequest);
        searchRequest.setStatus(managerRequest.getStatus());

        return searchRequest;
    }

    public static AnnouncementProcessRequestDTO createAnnouncementProcessRequestDTO(Operator operator) {
        AnnouncementProcessRequestDTO managerRequest = new AnnouncementProcessRequestDTO(operator);
        managerRequest.setStatus(Arrays.asList(Status.PENDING, Status.RETRY));

        return managerRequest;
    }

    public static AnnouncementProcessReturnDTO createAnnouncementProcessReturnDTO() {
        return new AnnouncementProcessReturnDTO();
    }

    public static AnnouncementForProcessListSearchRequestDTO createAnnouncementForProcessListSearchRequestDTO(AnnouncementProcessRequestDTO managerRequest) {
        AnnouncementForProcessListSearchRequestDTO searchRequest = new AnnouncementForProcessListSearchRequestDTO(managerRequest);
        searchRequest.setStatus(managerRequest.getStatus());

        return searchRequest;
    }

    public static AnnouncementForDeleteListSearchRequestDTO createAnnouncementForDeleteListSearchRequestDTO(BatchAnnouncementDeleteRequestDTO managerRequest) {
        AnnouncementForDeleteListSearchRequestDTO searchRequest = new AnnouncementForDeleteListSearchRequestDTO(managerRequest);
        searchRequest.setStatus(Status.DELETED);

        return searchRequest;
    }

    public static AnnouncementDeleteServiceRequestDTO createAnnouncementDeleteServiceRequestDTO(BatchAnnouncementDeleteRequestDTO managerRequest, List<AnnouncementForDeleteSearchBean> announcements) {
        AnnouncementDeleteServiceRequestDTO deleteRequest = new AnnouncementDeleteServiceRequestDTO(managerRequest);
        announcements.forEach(announcement -> {
            deleteRequest.getKeys().add(announcement.getAnnouncementSampleKey());
        });

        return deleteRequest;
    }
    // ***** private method *****
    // ***** protected method *****
    // ***** getter and setter *****

}
