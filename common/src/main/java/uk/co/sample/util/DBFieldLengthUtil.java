package uk.co.sample.util;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.regex.Pattern;

import uk.co.sample.constant.CommonConstant;
import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.exception.ApplicationCheckException;

public class DBFieldLengthUtil {
    private static final String  MINUS_SIGN             = "-";
    private static final String  MAXVAL_OF_SINGLE_DIGIT = "9";
    private static final String  FORMAT_ARITHMETIC      = CommonConstant.DOT;
    private static final int     RADIX                  = 10;
    /** Regex for decimal point. */
    private static final Pattern REGEX_DECIMAL_POINT    = Pattern.compile("\\.");

    //***** constructor *****
    private DBFieldLengthUtil() {
        // Do nothing
    }
    //***** public method *****
    public static int length(String fieldName) {
        String value = getDBFieldLengthString(fieldName);
        if (null == value) {
            return 0;
        }

        String[] length = REGEX_DECIMAL_POINT.split(value);
        if (1 == length.length) {

            return Integer.parseInt(length[0], RADIX);

        } else {

            if (0 == Integer.parseInt(length[1])) {
                return MINUS_SIGN.length() + Integer.parseInt(length[0], RADIX);
            } else {
                return MINUS_SIGN.length() + Integer.parseInt(length[0], RADIX) + FORMAT_ARITHMETIC.length();
            }
        }
    }

    public static int length(String tableName, String fieldName) {
        return length(getTableSpecifiedFieldName(tableName, fieldName));
    }

    /**
     * Get length of DB field.<br>
     *
     * @param fieldName String
     * @return String length of DB field.
     */
    public static String getDBFieldLengthString(String fieldName) {

        String value = obtainSingleton().readProperty(fieldName);
        return obtainFieldLength(value);
    }

    public static int lengthFractional(String fieldName) {
        String value = getString(fieldName);
        if (isRealNumber(value)) {
            return Integer.parseInt(fractionalPortion(value));
        } else {
            return 0;
        }
    }

    public static int lengthFractional(String tableName, String fieldName) {
        return lengthFractional(getTableSpecifiedFieldName(tableName, fieldName));
    }

    public static boolean isWithinLength(String fieldName, String value) {
        if (value == null) {
            return true;
        }
        return value.length() <= length(fieldName);
    }

    public static boolean isWithinLength(String tableName, String fieldName, String value) {
        return isWithinLength(getTableSpecifiedFieldName(tableName, fieldName), value);
    }

    public static boolean isWithinPrecision(String fieldName, BigDecimal value) {
        if (value == null) {
            return true;
        }
        int length = length(fieldName);
        int lengthFractional = lengthFractional(fieldName);

        return value.precision() <= length + lengthFractional && Math.abs(value.scale()) <= lengthFractional;
    }

    public static boolean isWithinPrecision(String tableName, String fieldName, BigDecimal value) {
        return isWithinPrecision(getTableSpecifiedFieldName(tableName, fieldName), value);
    }

    //***** protected method *****
    protected static final String getString(String key) {
        return obtainSingleton().readProperty(key);
    }

    //***** private method *****
    private static String getTableSpecifiedFieldName(String tableName, String fieldName) {
        String concat = tableName + CommonConstant.DOT + fieldName;

        Enumeration<String> keys = obtainSingleton().getBundle().getKeys();
        while (keys.hasMoreElements()) { // returns table specified field name if found
            if (keys.nextElement().equals(concat)) {
                return concat;
            }
        }

        return fieldName; // otherwise return just field name
    }

    /**
     * Get DBFieldMinValue of String.<br>
     *
     * @param propItem String
     * @return String of min value.
     */
    public static String getDBFieldMinValueString(String propItem) {
        return MINUS_SIGN + getDBFieldMaxValueString(propItem);
    }

    /**
     * Obtain max value of string.<br>
     *
     * @param fieldName String of FIELD_NAME.
     * @return String of max value.
     */
    public static String getDBFieldMaxValueString(final String fieldName) {

        int integral;
        String maxvalue;
        String[] length = REGEX_DECIMAL_POINT.split(getString(fieldName));

        if (1 == length.length) {

            // Field length do not contains decimal point.
            maxvalue = CommonConstant.EMPTY;
            integral = Integer.parseInt(length[0]);

        } else if (2 == length.length) {

            // Field length contains decimal point.
            maxvalue = FORMAT_ARITHMETIC;
            integral = Integer.parseInt(length[0]) - Integer.parseInt(length[1]);

            // Decimal part.
            for (int i = 0; i < Integer.parseInt(length[1]); i++) {
                maxvalue = maxvalue + MAXVAL_OF_SINGLE_DIGIT;
            }

        } else {

            // Field length contains over 1 decimal point.
            throw new ApplicationCheckException(SystemErrorCodeConstant.ERROR_LOGIC);
        }

        // Integral part.
        for (int i = 0; i < integral; i++) {
            maxvalue = MAXVAL_OF_SINGLE_DIGIT + maxvalue;
        }

        return maxvalue;
    }

    private static boolean isRealNumber(String value) {
        return value.contains(CommonConstant.DOT);
    }

    private static String fractionalPortion(String value) {
        return value.substring(value.indexOf(CommonConstant.DOT) + 1);
    }

    /**
     * obtainFieldAttr
     * <br>
     * @param value
     * @return String
     */
    private static String obtainFieldAttr(String value) {
        String[] values = value.split(CommonConstant.COMMA);
        return values[0].trim();
    }

    /**
     * obtainFieldLength
     * <br>
     * @param value
     * @return String
     */
    private static String obtainFieldLength(String value) {
        String[] values = value.split(CommonConstant.COMMA);

        if (1 < values.length) {
            return values[1].trim();
        } else if (1 == values.length) {
            return values[0].trim();
        } else {
            return null;
        }
    }

    /**
     * Get attribute of DB field.<br>
     *
     * @param fieldName String
     * @param defaultValue
     * @return String DB field Attribute.
     */
    public static String getDBFieldAttribute(String fieldName, String defaultValue) {

        String value = readProperty(fieldName, defaultValue);

        if (null == defaultValue) {
            if (null == value) {
                return defaultValue;
            } else {
                return obtainFieldAttr(value);
            }
        } else {
            if (defaultValue.equals(value)) {
                return defaultValue;
            } else {
                return obtainFieldAttr(value);
            }
        }
    }

    /**
     * read property.
     * <br>
     * @param key property key
     * @param defaultName default name for return.
     * @return if exist property value then property value else defalutName.
     */
    public static String readProperty(final String key, final String defaultName) {
        try {
            final String readName = obtainSingleton().readProperty(key);
            if (readName == null || readName.length() == 0) {
                return defaultName;
            }
            return readName;
        } catch (RuntimeException e) {
            return defaultName;
        }
    }

    private static PropertyUtil obtainSingleton() {
        return Holder.propertyUtil;
    }

    private static final class Holder {
        private static final String PROPERTIES_FILE = "uk.co.sample.db.DBFieldLength";
        private static PropertyUtil propertyUtil    = new PropertyUtil(PROPERTIES_FILE);
    }
    //***** call back method *****
    //***** getter and setter *****

}
