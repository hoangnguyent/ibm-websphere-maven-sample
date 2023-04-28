package uk.co.sample.util;

import static uk.co.sample.constant.BaseConstant.PATTERN_TIMESTAMP_A;
import static uk.co.sample.constant.BaseConstant.PATTERN_TIMESTAMP_A_HYPHEN;
import static uk.co.sample.constant.BaseConstant.PATTERN_TIMESTAMP_A_SLASH;
import static uk.co.sample.constant.BaseConstant.PATTERN_TIMESTAMP_B;
import static uk.co.sample.constant.BaseConstant.PATTERN_TIMESTAMP_B_HYPHEN;
import static uk.co.sample.constant.BaseConstant.PATTERN_TIMESTAMP_B_SLASH;
import static uk.co.sample.constant.BaseConstant.PATTERN_TIMESTAMP_C;
import static uk.co.sample.constant.BaseConstant.PATTERN_TIMESTAMP_C_HYPHEN;
import static uk.co.sample.constant.BaseConstant.PATTERN_TIMESTAMP_C_SLASH;
import static uk.co.sample.constant.BaseConstant.PATTERN_TIMESTAMP_D;
import static uk.co.sample.constant.BaseConstant.PATTERN_TIMESTAMP_D_HYPHEN;
import static uk.co.sample.constant.BaseConstant.PATTERN_TIMESTAMP_D_SLASH;
import static uk.co.sample.constant.CommonConstant.COLON;
import static uk.co.sample.constant.CommonConstant.DOT;
import static uk.co.sample.constant.CommonConstant.EMPTY;
import static uk.co.sample.constant.CommonConstant.WHITE_SPACE;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;

import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.exception.ApplicationLogicException;

public class DateUtil {

    private static final int     MONTH_COUNT_OF_YEAR = 12;
    private static final int     NOON                = 12;
    private static final Pattern PATTERN_TIMESTAMP   = Pattern.compile("^\\d{4}[/|\\-]?\\d{1,2}[/|\\-]?\\d{1,2}(| \\d{1,2}:\\d{1,2}(|:\\d{1,2}(|\\.\\d{1,3})))$");

    private static final int     FIRST_HOUR          = 0;
    private static final int     FIRST_MINUTE        = 0;
    private static final int     FIRST_SECOND        = 0;
    private static final int     FIRST_MILLISECOND   = 0;

    // ***** injection field *****
    // ***** constructor *****
    private DateUtil() {
        // Do nothing
    }
    // ***** public method *****
    public static boolean isValidYearMonth(String date) {
        try {
            new SimpleDateFormat(PATTERN_TIMESTAMP_D_SLASH).parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidDate(String date) {

        if (date == null) {
            return false;
        }

        String[] ymd = date.split("/", -1);
        if (ymd.length != 3) {
            ymd = date.split("-", -1);
            if (ymd.length != 3) {
                return false;
            }
        }

        int year;
        int month;
        int day;
        try {
            year = Integer.parseInt(ymd[0]);
            month = Integer.parseInt(ymd[1]);
            day = Integer.parseInt(ymd[2]);
        } catch (NumberFormatException e) {
            return false;
        }

        return isValidDate(year, month, day);
    }

    public static boolean isValidDate(int year, int month, int day) {

        Calendar calendar = new GregorianCalendar(year, month - 1, day);

        return calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == (month - 1) && calendar.get(Calendar.DAY_OF_MONTH) == day;
    }

    public static boolean isValidTimestamp(String date, String hour, String minute, String second, String millisec) {
        try {
            convertToDate(concat(date, hour, minute, second, millisec));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Date convertToDate(String str) {

        if (StringUtils.isBlank(str)) {
            return null;
        }

        if (PATTERN_TIMESTAMP.matcher(str).matches()) {

            Date date;

            // TIMESTAMP format. (yyyymmdd hh:mm:ss.sss)
            date = convertDateSub(str, PATTERN_TIMESTAMP_A);
            if (null != date) {
                return date;
            }

            // TIMESTAMP format. (yyyymmdd hh:mm:ss)
            date = convertDateSub(str, PATTERN_TIMESTAMP_B);
            if (null != date) {
                return date;
            }

            // TIMESTAMP format. (yyyymmdd hh:mm)
            date = convertDateSub(str, PATTERN_TIMESTAMP_C);
            if (null != date) {
                return date;
            }

            // TIMESTAMP format. (yyyymmdd)
            date = convertDateSub(str, PATTERN_TIMESTAMP_D);
            if (null != date) {
                return date;
            }

            // TIMESTAMP format. (yy-mm-dd hh:mm:ss.sss)
            date = convertDateSub(str, PATTERN_TIMESTAMP_A_HYPHEN);
            if (null != date) {
                return date;
            }

            // TIMESTAMP format. (yy-mm-dd hh:mm:ss)
            date = convertDateSub(str, PATTERN_TIMESTAMP_B_HYPHEN);
            if (null != date) {
                return date;
            }

            // TIMESTAMP format. (yy-mm-dd hh:mm)
            date = convertDateSub(str, PATTERN_TIMESTAMP_C_HYPHEN);
            if (null != date) {
                return date;
            }

            // TIMESTAMP format. (yy-mm-dd)
            date = convertDateSub(str, PATTERN_TIMESTAMP_D_HYPHEN);
            if (null != date) {
                return date;
            }

            // TIMESTAMP format. (yy/mm/dd hh:mm:ss.sss)
            date = convertDateSub(str, PATTERN_TIMESTAMP_A_SLASH);
            if (null != date) {
                return date;
            }

            // TIMESTAMP format. (yy/mm/dd hh:mm:ss)
            date = convertDateSub(str, PATTERN_TIMESTAMP_B_SLASH);
            if (null != date) {
                return date;
            }

            // TIMESTAMP format. (yy/mm/dd hh:mm)
            date = convertDateSub(str, PATTERN_TIMESTAMP_C_SLASH);
            if (null != date) {
                return date;
            }

            // DATE format. (yy/mm/dd)
            date = convertDateSub(str, PATTERN_TIMESTAMP_D_SLASH);
            if (null != date) {
                return date;
            }

            throw new ApplicationLogicException(SystemErrorCodeConstant.ERROR_LOGIC);

        } else {
            try {
                return convertStringWithOffsetDateTimeToDate(str);
            } catch (Exception e) {
                return convertStringWithXMLGregorianFormatToDate(str);
            }
        }

    }

    public static Date parseTimestamp(String timestamp) {
        try {
            return new SimpleDateFormat(PATTERN_TIMESTAMP_A_SLASH).parse(timestamp);
        } catch (ParseException e) {
            throw new ApplicationLogicException(SystemErrorCodeConstant.ERROR_LOGIC, new IllegalArgumentException("Validate " + timestamp + " before parse."));
        }
    }

    public static String concat(String date, String hour, String minute, String second, String millisec) {
        return new StringBuilder(date).append(WHITE_SPACE).append(hour).append(COLON).append(minute).append(COLON).append(second).append(DOT).append(millisec).toString();
    }

    public static String format_yyyyMMdd(Date date) {
        return format(date, new SimpleDateFormat(PATTERN_TIMESTAMP_D_SLASH));
    }

    public static String format_yyyyMMddHHMMss(Date date) {
        return format(date, new SimpleDateFormat(PATTERN_TIMESTAMP_B_SLASH));
    }

    public static String format_yyyyMMddHHMMssSSS(Date date) {
        return format(date, new SimpleDateFormat(PATTERN_TIMESTAMP_B_SLASH));
    }

    public static String format(Date date, String pattern) {
        return format(date, new SimpleDateFormat(pattern));
    }

    public static String format(Date date, DateFormat format) {
        if (date == null) {
            return EMPTY;
        }
        return format.format(date);
    }

    public static Calendar parseDateToCalendar(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * Parse date to xml gregorian calendar
     *
     * @param date The input date
     * @return XMLGregorianCalendar The xml gregorian calendar
     */
    public static XMLGregorianCalendar parseDateToXMLGregorianCalendar(Date date) {
        XMLGregorianCalendar xmlCalendar = null;
        try {
            if (date != null) {
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            }
        } catch (DatatypeConfigurationException e) {
            throw new ApplicationLogicException(SystemErrorCodeConstant.ERROR_LOGIC, e);
        }
        return xmlCalendar;
    }

    /**
     * Parse calendar to xml gregorian calendar
     *
     * @param calendar The input calendar
     * @return XMLGregorianCalendar The xml gregorian calendar
     */
    public static XMLGregorianCalendar parseCalendarToXMLGregorianCalendar(Calendar calendar) {
        XMLGregorianCalendar xmlCalendar = null;
        try {
            if (calendar != null) {
                GregorianCalendar gcalendar = new GregorianCalendar();
                gcalendar.setTime(calendar.getTime());
                xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcalendar);
            }
        } catch (DatatypeConfigurationException e) {
            throw new ApplicationLogicException(SystemErrorCodeConstant.ERROR_LOGIC, e);
        }
        return xmlCalendar;
    }

    /**
     * Parse date from xml gregorian calendar
     *
     * @param xmlCalendar The inpu xml gregorian calendar
     * @return Date The output date
     */
    public static Date parseXMLGregorianCalendarToDate(XMLGregorianCalendar xmlCalendar) {
        Date date = null;
        if (xmlCalendar != null) {
            GregorianCalendar calendar = xmlCalendar.toGregorianCalendar();
            date = calendar.getTime();
        }
        return date;
    }

    /**
     * Parse calendar from xml gregorian calendar
     *
     * @param xmlCalendar The inpu xml gregorian calendar
     * @return Calendar The output calendar
     */
    public static Calendar parseXMLGregorianCalendarToCalendar(XMLGregorianCalendar xmlCalendar) {
        Calendar calendar = null;
        if (xmlCalendar != null) {
            GregorianCalendar gcalendar = xmlCalendar.toGregorianCalendar();
            calendar = Calendar.getInstance();
            calendar.setTime(gcalendar.getTime());
        }
        return calendar;
    }

    public static XMLGregorianCalendar convertStringToXMLGregorianCalendar(String str) {

        if (StringUtils.isAllBlank(str)) {
            return null;
        }

        try {

            return DatatypeFactory.newInstance().newXMLGregorianCalendar(str);

        } catch (Exception e) {
            throw new ApplicationLogicException(SystemErrorCodeConstant.ERROR_LOGIC, e);
        }

    }

    public static Date convertStringWithXMLGregorianFormatToDate(String str) {
        XMLGregorianCalendar xgc = convertStringToXMLGregorianCalendar(str);
        return parseXMLGregorianCalendarToDate(xgc);
    }

    public static Date convertStringWithOffsetDateTimeToDate(String str) {
        try {
            OffsetDateTime odt = OffsetDateTime.parse(str);
            return new Date(odt.toInstant().toEpochMilli());
        } catch (Exception e) {
            throw new ApplicationLogicException(SystemErrorCodeConstant.ERROR_LOGIC, e);
        }
    }

    /**
     * return the first time of specified day.<br>
     * ex:
     * <pre>
     *  // 2004/1/2 12:11:10.000
     *  getDayFirstTime(new Date(2004, 0, 2, 12, 11, 10)
     *      == Date(2004, 0, 2, 0, 0 ,0)    //2004/1/2 0:0:0.000
     * </pre>
     * @param date Date
     * @return Date the first time of specified day.
     */
    public static Date getDayFirstTime(Date date) {

        if (null == date) {
            return null;
        } else {
            //set original date
            Calendar orgCalendar = Calendar.getInstance();
            orgCalendar.setTime(date);

            //set changed date
            Calendar newCalendar = Calendar.getInstance();
            newCalendar.set(orgCalendar.get(Calendar.YEAR), orgCalendar.get(Calendar.MONTH), orgCalendar.get(Calendar.DAY_OF_MONTH), FIRST_HOUR, FIRST_MINUTE, FIRST_SECOND);
            newCalendar.set(Calendar.MILLISECOND, FIRST_MILLISECOND);

            return newCalendar.getTime();
        }
    }

    /**
     * return the last time of specified day.<br>
     * ex:
     * <pre>
     *  // 2004/1/2 12:11:10
     *  getDayFirstTime(new Date(2004, 0, 2, 12, 11, 10)
     *      == Date(2004, 0, 2, 23, 59 ,59.999) //2004/1/2 23:59:59.999
     * </pre>
     * @param date Date
     * @return Date the last time of the specified day.
     */
    public static Date getDayLastTime(Date date) {

        if (null == date) {
            return null;
        } else {
            //set original date
            Calendar orgCalendar = Calendar.getInstance();
            orgCalendar.setTime(date);

            //set changed date
            Calendar newCalendar = Calendar.getInstance();
            newCalendar.set(orgCalendar.get(Calendar.YEAR), orgCalendar.get(Calendar.MONTH), orgCalendar.get(Calendar.DAY_OF_MONTH) + 1, FIRST_HOUR, FIRST_MINUTE, FIRST_SECOND);
            newCalendar.set(Calendar.MILLISECOND, FIRST_MILLISECOND);
            newCalendar.setTimeInMillis(newCalendar.getTimeInMillis() - 1);

            return newCalendar.getTime();
        }
    }

    /**
     * Calculate different month between two date
     *
     * @param firstDate The first date
     * @param secondDate The second date
     * @return {@link Integer} The month differences between two date.
     */
    public static Integer calDiffMonths(Date firstDate, Date secondDate) {

        if (null == firstDate || null == secondDate) {
            return null;
        }

        Calendar orgCalendar = Calendar.getInstance();
        orgCalendar.setTime(firstDate);
        int monthOfFirstDate = MONTH_COUNT_OF_YEAR * orgCalendar.get(Calendar.YEAR) + orgCalendar.get(Calendar.MONTH);

        orgCalendar.setTime(secondDate);
        int monthOfSecondDate = MONTH_COUNT_OF_YEAR * orgCalendar.get(Calendar.YEAR) + orgCalendar.get(Calendar.MONTH);

        return monthOfSecondDate - monthOfFirstDate;
    }

    /**
     * return date after addDays[day] from the date.<br>
     * @param date Date
     * @param addDays int
     * @return Date
     */
    public static Date addDaysTo(Date date, int addDays) {

        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.DATE, addDays);
        return c.getTime();
    }

    // ***** protected method *****
    // ***** private method *****
    private static Date convertDateSub(Object obj, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setLenient(false);
            return sdf.parse((String) obj);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getDayNoonTime(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, NOON);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static Date getDayNoonTime(String strDate) {
        Date date = convertToDate(strDate);
        return getDayNoonTime(date);
    }
    // ***** getter and setter *****
}
