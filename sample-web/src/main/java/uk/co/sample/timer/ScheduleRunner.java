package uk.co.sample.timer;

import static java.lang.System.currentTimeMillis;
import static uk.co.sample.constant.TimerDefintionConstant.ANNOUNCEMENT_ARCHIVE_AUTO_START;
import static uk.co.sample.constant.TimerDefintionConstant.ANNOUNCEMENT_ARCHIVE_JNDI;
import static uk.co.sample.constant.TimerDefintionConstant.ANNOUNCEMENT_ARCHIVE_LOG_FILE_NAME;
import static uk.co.sample.constant.TimerDefintionConstant.ANNOUNCEMENT_ARCHIVE_NAME;
import static uk.co.sample.constant.TimerDefintionConstant.ANNOUNCEMENT_ARCHIVE_SECOND_INTERVAL;
import static uk.co.sample.constant.TimerDefintionConstant.ANNOUNCEMENT_PROCESS_AUTO_START;
import static uk.co.sample.constant.TimerDefintionConstant.ANNOUNCEMENT_PROCESS_JNDI;
import static uk.co.sample.constant.TimerDefintionConstant.ANNOUNCEMENT_PROCESS_LOG_FILE_NAME;
import static uk.co.sample.constant.TimerDefintionConstant.ANNOUNCEMENT_PROCESS_NAME;
import static uk.co.sample.constant.TimerDefintionConstant.ANNOUNCEMENT_PROCESS_SECOND_INTERVAL;
import static uk.co.sample.constant.TimerDefintionConstant.DELAY_MILISECOND;
import static uk.co.sample.constant.TimerDefintionConstant.TASK_REPEAT_NONE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.MDC;

import com.ibm.websphere.scheduler.BeanTaskInfo;
import com.ibm.websphere.scheduler.Scheduler;
import com.ibm.websphere.scheduler.SchedulerNotAvailableException;
import com.ibm.websphere.scheduler.TaskHandlerHome;
import com.ibm.websphere.scheduler.TaskStatus;

import uk.co.sample.constant.CommonConstant;

/**
 * <h3>
 *  ScheduleRunner
 * </h3>
 *
 * @author hoangnguyent
 * @since 2021/05/15
 */
@WebListener
@ManagedBean(name = "ScheduleRunner")
public class ScheduleRunner implements ServletContextListener {

    private static final List<Integer>             TASK_DISABLE_STATUS = Arrays.asList(TaskStatus.INVALID, TaskStatus.CANCELLED, TaskStatus.SUSPENDED);

    @Inject
    private Logger                                 logger;

    @Resource(lookup = ANNOUNCEMENT_ARCHIVE_JNDI)
    private Scheduler                              announcementArchiveTimer;
    @EJB(lookup = "java:app/sample-ejb/AnnouncementArchiveTimerProcessor!uk.co.sample.timer.AnnouncementArchiveTimerTaskRemoteHome")
    private AnnouncementArchiveTimerTaskRemoteHome announcementArchiveTimerTaskRemoteHome;

    @Resource(lookup = ANNOUNCEMENT_PROCESS_JNDI)
    private Scheduler                              announcementProcessTimer;
    @EJB(lookup = "java:app/sample-ejb/AnnouncementProcessTimerProcessor!uk.co.sample.timer.AnnouncementProcessTimerTaskRemoteHome")
    private AnnouncementProcessTimerTaskRemoteHome announcementProcessTimerTaskRemoteHome;

    // ***** constructor *****
    // ***** public method *****
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        logger.info("ANNOUNCEMENT_ARCHIVE_AUTO_START: {}", ANNOUNCEMENT_ARCHIVE_AUTO_START);
        if (ANNOUNCEMENT_ARCHIVE_AUTO_START) {
            wakeUp(announcementArchiveTimer, ANNOUNCEMENT_ARCHIVE_NAME, ANNOUNCEMENT_ARCHIVE_LOG_FILE_NAME, ANNOUNCEMENT_ARCHIVE_SECOND_INTERVAL, announcementArchiveTimerTaskRemoteHome);
        }

        logger.info("ANNOUNCEMENT_PROCESS_AUTO_START: {}", ANNOUNCEMENT_PROCESS_AUTO_START);
        if (ANNOUNCEMENT_PROCESS_AUTO_START) {
            wakeUp(announcementProcessTimer, ANNOUNCEMENT_PROCESS_NAME, ANNOUNCEMENT_PROCESS_LOG_FILE_NAME, ANNOUNCEMENT_PROCESS_SECOND_INTERVAL, announcementProcessTimerTaskRemoteHome);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        stopAllTaskByName(announcementArchiveTimer, ANNOUNCEMENT_ARCHIVE_NAME, ANNOUNCEMENT_ARCHIVE_LOG_FILE_NAME);

        stopAllTaskByName(announcementProcessTimer, ANNOUNCEMENT_PROCESS_NAME, ANNOUNCEMENT_PROCESS_LOG_FILE_NAME);

    }

    // ***** protected method *****
    // ***** private method *****
    private void wakeUp(Scheduler targetScheduler, String targetTaskName, String logFileName, String interval, TaskHandlerHome home) {

        try {

            MDC.put(CommonConstant.LOG_SOURCE_KEY, logFileName);

            if (null == targetScheduler) {
                logger.info("Cannot alocate IBM RAD scheduler. Normally end.");
                return;
            }

            List<BeanTaskInfo> taskList = obtainTaskList(targetScheduler, targetTaskName);
            if (taskList.size() > 1) {
                logger.warn("There are multiple instances of [{}]. Only the first one will be controlled.", targetTaskName);
            }

            TaskStatus taskWakeUpStatus = null;

            if (taskList.isEmpty()) { // create a new one

                Calendar delay = Calendar.getInstance();
                delay.setTimeInMillis(currentTimeMillis() + DELAY_MILISECOND);

                BeanTaskInfo task = (BeanTaskInfo) targetScheduler.createTaskInfo(BeanTaskInfo.class);
                task.setTaskHandler(home);
                task.setStartTime(delay.getTime());
                task.setName(targetTaskName);
                task.setNumberOfRepeats(TASK_REPEAT_NONE);
                task.setRepeatInterval(interval);

                taskWakeUpStatus = targetScheduler.create(task);

            } else if (TaskStatus.SUSPENDED == taskList.get(0).getStatus()) { // wake up
                taskWakeUpStatus = targetScheduler.resume(taskList.get(0).getTaskId());

            } else {
                logger.info("Task [name={}, id={}] started failed. Status={}. Cancelled or completed tasks cannot be resumed. Please remove it manually.", taskList.get(0).getName(),
                    taskList.get(0).getTaskId(), taskList.get(0).getStatus());
                return;
            }

            // Check status and print log
            if (null == taskWakeUpStatus) {
                logger.info("Task [id=N/A, name={}] started failed. Status=N/A", targetTaskName);
            } else if (TASK_DISABLE_STATUS.contains(taskWakeUpStatus.getStatus())) {
                logger.info("Task [id={}, name={}] started failed. Status={}", taskWakeUpStatus.getTaskId(), taskWakeUpStatus.getName(), taskWakeUpStatus.getStatus());
            } else {
                logger.info("Task [id={}, name={}] started. Status={}", taskWakeUpStatus.getTaskId(), taskWakeUpStatus.getName(), taskWakeUpStatus.getStatus());
            }

        } catch (Exception e) {
            logger.error("Task [name={}] started failed", targetTaskName, e);

        } finally {
            MDC.remove(CommonConstant.LOG_SOURCE_KEY);
        }

    }

    private void stopAllTaskByName(Scheduler targetScheduler, String targetTaskName, String logFileName) {

        try {

            MDC.put(CommonConstant.LOG_SOURCE_KEY, logFileName);

            if (null == targetScheduler) {
                logger.info("Cannot alocate IBM RAD scheduler. END");
                return;
            }

            List<BeanTaskInfo> listTask = obtainTaskList(targetScheduler, targetTaskName);
            Iterator<BeanTaskInfo> tasksIterator = listTask.iterator();
            while (tasksIterator.hasNext()) {

                if (!isNameRemainInTaskList(listTask, targetTaskName)) {
                    break;
                }

                BeanTaskInfo taskInfo = tasksIterator.next();
                stopSingleTask(targetScheduler, targetTaskName, taskInfo);
                tasksIterator.remove();

            }
        } catch (Exception e) {
            logger.debug("Task [id={}], name={}] stopped failed", null, targetTaskName);
        } finally {
            MDC.remove(CommonConstant.LOG_SOURCE_KEY);
        }

    }

    @SuppressWarnings("unchecked")
    private List<BeanTaskInfo> obtainTaskList(Scheduler targetScheduler, String targetName) throws SchedulerNotAvailableException {

        List<BeanTaskInfo> tasksList = new ArrayList<>();

        Iterator<BeanTaskInfo> tasks = targetScheduler.findTasksByName(targetName);
        while (tasks.hasNext()) {
            tasksList.add(tasks.next());
        }

        if (tasksList.size() > 1) {
            Collections.sort(tasksList, (obj1, obj2) -> ObjectUtils.compare(obj1.getTaskId(), obj2.getTaskId()));
        }

        return tasksList;
    }

    private BeanTaskInfo doLookupInListTaskList(List<BeanTaskInfo> taskList, String name) {
        for (BeanTaskInfo task : taskList) {
            if (task.getName().equals(name)) {
                return task;
            }
        }
        return null;
    }

    private boolean isNameRemainInTaskList(List<BeanTaskInfo> taskList, String name) {
        return doLookupInListTaskList(taskList, name) != null;
    }

    private void stopSingleTask(Scheduler targetScheduler, String targetTaskName, BeanTaskInfo taskInfo) {
        try {

            if (!targetTaskName.equals(taskInfo.getName())) {
                return;
            }

            // Cancel and completely remove the task
            TaskStatus taskStopStatus = targetScheduler.cancel(taskInfo.getTaskId(), true);

            if (TASK_DISABLE_STATUS.contains(taskStopStatus.getStatus())) {
                logger.info("Task [id={}, name={}] stopped. Status={}", taskStopStatus.getTaskId(), taskStopStatus.getName(), taskStopStatus.getStatus());
            } else {
                logger.info("Task [id={}], name={}] stopped failed. Status={}", taskStopStatus.getTaskId(), taskStopStatus.getName(), taskStopStatus.getStatus());
            }

        } catch (Exception e) {
            logger.error("Task [id={}], name={}] stopped failed", taskInfo.getTaskId(), taskInfo.getName(), e);
        }
    }
    // ***** getter and setter *****

}
