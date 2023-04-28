package uk.co.sample.converter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

import uk.co.sample.util.DateUtil;

public class TimestampAndDateConverter {

    // ***** injection field *****
    // ***** constructor *****
    private TimestampAndDateConverter() {
        // Do nothing
    }

    // ***** public method *****
    public static Date convertStringToDate(String str) {
        try {
            return DateUtil.convertToDate(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static Timestamp convertStringToTimestamp(String str) {
        Date date = convertStringToDate(str);
        return convertDateToTimestamp(date);
    }

    public static Date convertTimestampToDate(Timestamp timestampValue) {
        if (timestampValue == null) {
            return null;
        }
        return new Date(timestampValue.getTime());
    }

    public static Timestamp convertDateToTimestamp(Date dateValue) {
        if (null == dateValue) {
            return null;
        }
        return new Timestamp(dateValue.getTime());
    }

    public static Timestamp convertInstantToTimestamp(Instant instant) {
        if (null == instant) {
            return null;
        }
        return Timestamp.from(instant);
    }

    public static Instant convertTimestampToInstant(Timestamp timestamp) {
        if (null == timestamp) {
            return null;
        }
        return timestamp.toInstant();
    }
    // ***** protected method *****
    // ***** private method *****
    // ***** getter and setter *****

}
