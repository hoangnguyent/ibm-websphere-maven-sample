package uk.co.sample.constant;

import java.math.BigDecimal;

import org.apache.commons.lang3.BooleanUtils;

import uk.co.sample.util.PropertyUtil;

public class TimerDefintionConstant {

    public static final int     TASK_REPEAT_NONE                     = -1;
    public static final long    DELAY_MILISECOND                     = 20000;

    private static final String SUFFIX_START                         = ".ibm.scheduler.auto.start";
    private static final String SUFFIX_INTERVAL                      = ".ibm.scheduler.minute.interval";
    private static final String SUFFIX_LOG_FILE                      = ".ibm.scheduler.log.file";
    private static final int    SECOND_PER_MINUTE                    = 60;

    /*AnnouncementProcessTimer*/
    public static final String  ANNOUNCEMENT_PROCESS                 = "ANNOUNCEMENT_PROCESS";
    public static final String  ANNOUNCEMENT_PROCESS_NAME            = "AnnouncementProcessTimer";      // be the same to value in AdministrativeConsole
    public static final String  ANNOUNCEMENT_PROCESS_JNDI            = "sched/AnnouncementProcessTimer";// be the same to value in AdministrativeConsole
    public static final String  ANNOUNCEMENT_PROCESS_SECOND_INTERVAL = String.format("%dseconds", (obtainSingleton().readNumberProperty(ANNOUNCEMENT_PROCESS + SUFFIX_INTERVAL).multiply(new BigDecimal(SECOND_PER_MINUTE))).intValue());
    public static final boolean ANNOUNCEMENT_PROCESS_AUTO_START      = BooleanUtils.toBoolean(obtainSingleton().readProperty(ANNOUNCEMENT_PROCESS + SUFFIX_START));
    public static final String  ANNOUNCEMENT_PROCESS_LOG_FILE_NAME   = obtainSingleton().readProperty(ANNOUNCEMENT_PROCESS + SUFFIX_LOG_FILE);

    /*AnnouncementArchiveTimer*/
    public static final String  ANNOUNCEMENT_ARCHIVE                 = "ANNOUNCEMENT_ARCHIVE";
    public static final String  ANNOUNCEMENT_ARCHIVE_NAME            = "AnnouncementArchiveTimer";      // be the same to value in AdministrativeConsole
    public static final String  ANNOUNCEMENT_ARCHIVE_JNDI            = "sched/AnnouncementArchiveTimer";// be the same to value in AdministrativeConsole
    public static final String  ANNOUNCEMENT_ARCHIVE_SECOND_INTERVAL = String.format("%dseconds", (obtainSingleton().readNumberProperty(ANNOUNCEMENT_ARCHIVE + SUFFIX_INTERVAL).multiply(new BigDecimal(SECOND_PER_MINUTE))).intValue());
    public static final boolean ANNOUNCEMENT_ARCHIVE_AUTO_START      = BooleanUtils.toBoolean(obtainSingleton().readProperty(ANNOUNCEMENT_ARCHIVE + SUFFIX_START));
    public static final String  ANNOUNCEMENT_ARCHIVE_LOG_FILE_NAME   = obtainSingleton().readProperty(ANNOUNCEMENT_ARCHIVE + SUFFIX_LOG_FILE);

    // ***** constructor *****
    private TimerDefintionConstant() {
    }

    // ***** injection field *****
    // ***** public method *****
    public static PropertyUtil obtainSingleton() {
        return Holder.propertyUtil;
    }
    // ***** protected method *****

    private static final class Holder {
        private static final String PROPERTIES_FILE = "uk.co.sample.Timer";
        private static PropertyUtil propertyUtil    = new PropertyUtil(PROPERTIES_FILE);
    }
    // ***** private method *****
    // ***** getter and setter *****

}
