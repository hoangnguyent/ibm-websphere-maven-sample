package uk.co.sample.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class ValidateUtil {

    /** for validate alphabet / numeric */
    private static final Pattern ALPHA_NUMERIC = Pattern.compile("^\\p{Alnum}+$");
    /** for validate numeric */
    private static final Pattern NUMERIC       = Pattern.compile("^-?\\p{Digit}*$");

    //***** injection field *****
    //***** constructor *****

    private ValidateUtil() {
        // Do not instantiate.
    }

    //***** public method *****
    public static boolean isDate(String value, String pattern) {
        if (value == null) {
            return false;
        }
        DateFormat format = new SimpleDateFormat(pattern);
        format.setLenient(false);
        try {
            format.parse(value);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean isAlphaNumeric(String value) {

        if (null == value) {
            return false;
        }

        return ALPHA_NUMERIC.matcher(value).matches();
    }

    /**
     * isNumeric<br>
     *
     * @param value String
     * @return true if value is numeric format
     */
    public static boolean isNumeric(String value) {

        if (null == value) {
            return false;
        }

        return NUMERIC.matcher(value).matches();
    }

    //***** protected method *****
    //***** private method *****
    //***** getter and setter *****

}
