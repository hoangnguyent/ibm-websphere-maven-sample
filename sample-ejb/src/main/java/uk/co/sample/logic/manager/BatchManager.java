package uk.co.sample.logic.manager;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import uk.co.log.LogWrapperImpl;
import uk.co.sample.base.Result;
import uk.co.sample.logic.service.AnnouncementDeleteService;
import uk.co.sample.logic.service.AnnouncementDeleteServiceRequestDTO;
import uk.co.sample.logic.service.AnnouncementForDeleteListSearchRequestDTO;
import uk.co.sample.logic.service.AnnouncementForDeleteSearchBean;
import uk.co.sample.logic.service.AnnouncementForDeleteSearchService;

@Stateless
public class BatchManager {

    // ***** injection field *****
    @Inject
    private LogWrapperImpl      logger;
    @Inject
    private AnnouncementDeleteService          announcementDeleteService;
    @Inject
    private AnnouncementForDeleteSearchService announcementForDeleteSearchService;

    // ***** public method *****
    public Result callAnnouncementDelete(BatchAnnouncementDeleteRequestDTO managerRequestDTO) {

        logger.logStart(managerRequestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());

        Result result = new Result();

        try {

            AnnouncementForDeleteListSearchRequestDTO searchRequestDTO = AnnouncementManagerHelper.createAnnouncementForDeleteListSearchRequestDTO(managerRequestDTO);
            List<AnnouncementForDeleteSearchBean> announcements = announcementForDeleteSearchService.doSearch(searchRequestDTO);

            AnnouncementDeleteServiceRequestDTO deleteRequestDTO = AnnouncementManagerHelper.createAnnouncementDeleteServiceRequestDTO(managerRequestDTO, announcements);
            announcementDeleteService.execute(deleteRequestDTO);

            return result;

        } finally {
            logger.logEnd(managerRequestDTO.getOperator(), new Throwable().getStackTrace()[0].getMethodName());
        }

    }

    // ***** constructor *****
    // ***** protected method *****
    // ***** getter and setter *****

}
