package uk.co.sample.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

import uk.co.sample.constant.BaseConstant;
import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.exception.ApplicationDataException;

public class ProcessingTimeUtil {
    private static final String   PROPERTY_FILE            = "System";
    private static final String   PROPERTY_SYSTEM_TIMEZONE = "system.timezone";

    // ***** constructor *****
    private ProcessingTimeUtil() {
    }

    // ***** public method *****
    public static Date getProcessingTime(String targetTimeZoneStr) {
        if (StringUtils.isBlank(targetTimeZoneStr)) {
            return new Date();
        }
        try {
            String systemTimeZone = obtainSingleton().readProperty(PROPERTY_SYSTEM_TIMEZONE);
            SimpleDateFormat formatter = new SimpleDateFormat(BaseConstant.PATTERN_ORA_TIMESTAMP);
            Date processingTime = new Date();
            if (!systemTimeZone.equals(targetTimeZoneStr)) {
                SimpleDateFormat sdf = new SimpleDateFormat(BaseConstant.PATTERN_ORA_TIMESTAMP);
                TimeZone targetTimeZone = TimeZone.getTimeZone(targetTimeZoneStr);
                sdf.setTimeZone(targetTimeZone);
                String processingTimeInStr = sdf.format(processingTime);

                processingTime = formatter.parse(processingTimeInStr);
            }
            return processingTime;
        } catch (ParseException e) {
            throw new ApplicationDataException(SystemErrorCodeConstant.ERROR_LOGIC, e);
        }
    }

    public static String getSystemTimeZone() {
        return obtainSingleton().readProperty(PROPERTY_SYSTEM_TIMEZONE);
    }

    // ***** private method *****
    private static PropertyUtil obtainSingleton() {
        return Holder.propertyUtil;
    }
    
    private static final class Holder {
        private static PropertyUtil propertyUtil = new PropertyUtil(PROPERTY_FILE);
    }
    // ***** injection field *****
    // ***** protected method *****
    // ***** getter and setter *****

}
