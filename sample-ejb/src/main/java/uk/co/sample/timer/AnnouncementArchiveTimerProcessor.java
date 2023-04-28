package uk.co.sample.timer;

import static uk.co.sample.constant.TimerDefintionConstant.ANNOUNCEMENT_ARCHIVE;

import javax.ejb.Remote;
import javax.ejb.RemoteHome;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.MDC;

import com.ibm.websphere.scheduler.TaskStatus;

import uk.co.log.LogWrapperImpl;
import uk.co.sample.constant.TimerDefintionConstant;
import uk.co.sample.constant.CommonConstant;
import uk.co.sample.logic.facade.AnnouncementCMTFacade;
import uk.co.sample.logic.manager.AnnouncementArchiveRequestDTO;
import uk.co.sample.logic.manager.AnnouncementManagerHelper;
import uk.co.sample.security.Operator;

@Stateless
@Remote(AnnouncementArchiveTimerTaskRemote.class)
@RemoteHome(AnnouncementArchiveTimerTaskRemoteHome.class)
public class AnnouncementArchiveTimerProcessor extends AbstractTimerProcessor implements AnnouncementArchiveTimerTaskRemote {

    // ***** injection field *****
    @Inject
    private LogWrapperImpl        logger;
    @Inject
    private AnnouncementCMTFacade announcementCMTFacade;

    // ***** constructor *****
    public AnnouncementArchiveTimerProcessor() {
        setSchedulerId(ANNOUNCEMENT_ARCHIVE);
    }

    // ***** public method *****
    @Override
    public void process(TaskStatus taskStatus) {

        Operator operator = initOperator();
        try {

            MDC.put(CommonConstant.LOG_SOURCE_KEY, TimerDefintionConstant.ANNOUNCEMENT_ARCHIVE_LOG_FILE_NAME);
            logger.logStart(operator, new Throwable().getStackTrace()[0].getMethodName());

            AnnouncementArchiveRequestDTO managerRequest = AnnouncementManagerHelper.createAnnouncementArchiveRequestDTO(initOperator());
            announcementCMTFacade.archiveAnnouncementSamples(managerRequest);

        } finally {
            logger.logEnd(operator, new Throwable().getStackTrace()[0].getMethodName());
            MDC.remove(CommonConstant.LOG_SOURCE_KEY);
        }
    }
    // ***** private method *****
    // ***** protected method *****
    // ***** getter and setter *****

}
