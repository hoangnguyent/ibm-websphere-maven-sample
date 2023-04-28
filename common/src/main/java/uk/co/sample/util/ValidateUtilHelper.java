package uk.co.sample.util;

import static uk.co.sample.constant.CommonConstant.COMMA;
import static uk.co.sample.constant.CommonConstant.DOT;
import static uk.co.sample.constant.CommonConstant.HYPHEN;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;

import org.apache.commons.lang.StringUtils;

import uk.co.sample.constant.CodeValidateConstant;
import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.exception.ApplicationDataException;

public class ValidateUtilHelper {

    /** Properties. */
    protected static final String               PROPERTIES_FILE = "uk.co.sample.db.validate";
    private static final PropertyResourceBundle prb             = (PropertyResourceBundle) PropertyResourceBundle.getBundle(PROPERTIES_FILE);

    /** Definition of "maxlen". */
    protected static final String               MAXLEN          = "maxlen";
    /** Definition of "minval". */
    protected static final String               MINVAL          = "minval";
    /** Definition of "maxval". */
    protected static final String               MAXVAL          = "maxval";

    //***** constructor *****
    private ValidateUtilHelper() {
        // Do nothing
    }

    //***** public method *****
    /**
     * Obtain property in List.<br>
     * first element is a property name
     * second element is :
     *     null     : if no operand is specified
     *     string   : if a fixed length is specified
     *     string[] : if a range is specified
     * <br>
     * @param fieldName Field name of String.
     * @param like Validate as like.
     * @return AbstractValidateBean
     * @throws NumberFormatException
     */
    public static AbstractValidateBean obtainProperty(String fieldName, boolean like) {

        // Get from validate.properties.
        String validateString = prb.getString(fieldName);
        validateString = replaceLength(validateString, fieldName);
        String[] delimit = validateString.split(COMMA);

        if (StringUtils.isBlank(delimit[0])) {

            return createTextValidateBeanForParam(fieldName, delimit);

        } else if (CodeValidateConstant.DATE_FULL.equals(delimit[0]) || CodeValidateConstant.DATE_YYYYMM.equals(delimit[0]) || CodeValidateConstant.TIMESTAMP.equals(delimit[0])) {

            return createDateValidateBean(delimit);

        } else if (CodeValidateConstant.BIGDECIMAL.equals(delimit[0]) || CodeValidateConstant.LONG.equals(delimit[0])) {

            return createNumericValidateBean(fieldName, delimit);

        } else if (CodeValidateConstant.BOOLEAN.equals(delimit[0]) || CodeValidateConstant.IP_ADDRESS.equals(delimit[0])) {

            return createTextValidateBeanForParam(fieldName, delimit);

        } else {

            return createTextValidateBean(fieldName, delimit, like);
        }
    }

    /**
     * Obtain property in List.<br>
     * first element is a property name
     * second element is :
     *     null     : if no operand is specified
     *     string   : if a fixed length is specified
     *     string[] : if a range is specified
     * <br>
     * @param propItem
     * @return property List
     */
    public static List<Object> getProperty(String propItem) {
        int from = 0;
        int to;
        int sign;
        String carve;
        String prop;
        List<Object> al = new ArrayList<>();

        // count comma.
        prop = prb.getString(propItem);

        prop = replaceLength(prop, propItem);

        do {
            // search comma.
            to = prop.indexOf(COMMA, from);
            if (-1 == to) {
                to = prop.length();
            }
            // carve.
            carve = prop.substring(from, to);

            if (0 == from) {
                // if first carving. Set as String.
                al.add(carve);

            } else if (al.get(0).equals(CodeValidateConstant.LONG)) {

                // if first carving is NUMERIC. Set as Long.
                sign = carve.indexOf(HYPHEN);

                al.add(new Long(replaceValue(carve.substring(0, sign), propItem)));
                al.add(new Long(replaceValue(carve.substring(sign + 1), propItem)));

            } else if (al.get(0).equals(CodeValidateConstant.BIGDECIMAL)) {

                // if first carving is BIGDECIMAL. Set as Long.
                sign = carve.indexOf(HYPHEN, 1);

                al.add(new BigDecimal(replaceValue(carve.substring(0, sign), propItem)));
                al.add(new BigDecimal(replaceValue(carve.substring(sign + 1), propItem)));

            } else if (-1 != carve.indexOf(DOT)) {

                // if string contains period. set as Integer[].
                sign = carve.indexOf(DOT);

                Integer[] range = new Integer[2];
                range[1] = new Integer(carve.substring(sign + 1));
                range[0] = Integer.valueOf(Integer.valueOf(carve.substring(0, sign)) - range[1].intValue());
                al.add(range);

            } else if (-1 != carve.indexOf(HYPHEN)) {

                // if string contains hyphen. set as Long[].
                sign = carve.indexOf(HYPHEN);

                Integer[] range = new Integer[2];
                range[0] = new Integer(carve.substring(0, sign));
                range[1] = new Integer(carve.substring(sign + 1));
                al.add(range);

            } else {

                // if string contains nothing. set as Integer.
                al.add(new Integer(carve));
            }

            // move point to search next.
            from = to + 1;

        } while (prop.length() > to);

        al.add(null);

        return al;
    }

    //***** protected method *****

    /**
     * Replace "maxlen".<br>
     *
     * @param src String
     * @param fieldName Field name of String.
     * @return Replaced String.
     */
    protected static String replaceLength(String src, String fieldName) {

        StringBuffer buf = new StringBuffer(src);

        // Get from DBFieldLength.properties
        String length = String.valueOf(DBFieldLengthUtil.length(fieldName));

        int found = src.indexOf(MAXLEN);
        if (-1 != found) {
            buf.replace(found, found + MAXLEN.length(), length);
        }

        return buf.toString();
    }

    /**
     * Replace "maxval" and "minval".<br>
     *
     * @param src String.
     * @param fieldName Field name of String.
     * @return Replaced String.
     */
    protected static String replaceValue(String src, String fieldName) {
        StringBuffer buf = new StringBuffer(src);
        int found;

        found = src.indexOf(MAXVAL);
        if (-1 != found) {
            buf.replace(found, found + MAXVAL.length(), DBFieldLengthUtil.getDBFieldMaxValueString(fieldName));
        }

        found = src.indexOf(MINVAL);
        if (-1 != found) {
            buf.replace(found, found + MINVAL.length(), DBFieldLengthUtil.getDBFieldMinValueString(fieldName));
        }

        return buf.toString();
    }

    /**
     * For DATE_FULL and DATE validation.<br>
     *
     * @param params String[]
     * @return DateValidateBean
     */
    protected static DateValidateBean createDateValidateBean(String[] params) {
        return new DateValidateBean(params[0]);
    }

    /**
     * For BIGDECIMAL and NUMERIC validation.<br>
     *
     * @param fieldName String field name.
     * @param params String[]
     * @return NumericValidateBean
     */
    protected static NumericValidateBean createNumericValidateBean(String fieldName, String[] params) {

        // Parameter count check.
        if (2 > params.length) {
            // Missing parameters.
            throw new ApplicationDataException(SystemErrorCodeConstant.ERROR_READ_PROPERTY, new IllegalStateException("Missing parameters."));
        }

        String length = DBFieldLengthUtil.getString(fieldName);

        // Check tolerance params.
        String[] tolerance = params[1].split(HYPHEN);
        BigDecimal minval = null;
        BigDecimal maxval = null;
        boolean hyphen = false;
        boolean minus = false;

        //"-maxval-maxval";
        //"minval-maxval";
        //"-999-maxval";
        //"-999--9";
        for (int i = 0; i < tolerance.length; i++) {
            if (!hyphen && "".equals(tolerance[i])) {
                minus = true;
                continue;
            }

            tolerance[i] = replaceValue(tolerance[i], fieldName);

            if (minus) {
                tolerance[i] = HYPHEN + tolerance[i];
                minus = false;
            }
            if (hyphen) {
                maxval = new BigDecimal(tolerance[i]);
            } else {
                minval = new BigDecimal(tolerance[i]);
            }
            hyphen = true;
        }

        return new NumericValidateBean(params[0], length, minval, maxval);
    }

    /**
     * For text validation.<br>
     *
     * @param fieldName DB field name to replace "maxlen".
     * @param params String[]
     * @return TextValidateBean
     */
    protected static TextValidateBean createTextValidateBeanForParam(String fieldName, String[] params) {

        // Parameter count check.
        if (1 < params.length) {
            // Missing parameters.
            throw new ApplicationDataException(SystemErrorCodeConstant.ERROR_READ_PROPERTY, new IllegalStateException("Too many parameters."));
        }

        int length = DBFieldLengthUtil.length(fieldName);

        return new TextValidateBean(params[0], 0, length);
    }

    /**
     * For text validation.<br>
     *
     * @param fieldName DB field name to replace "maxlen".
     * @param params String[]
     * @param like Validate as like
     * @return TextValidateBean
     */
    protected static TextValidateBean createTextValidateBean(String fieldName, String[] params, boolean like) {

        // Parameter count check.
        if (2 > params.length) {
            // Missing parameters.
            throw new ApplicationDataException(SystemErrorCodeConstant.ERROR_READ_PROPERTY, new IllegalStateException("Missing parameters."));
        }

        // Check tolerance params.
        String[] tolerance = params[1].split(HYPHEN);
        int minlen;
        int maxlen;

        if (1 == tolerance.length) {
            minlen = Integer.parseInt(replaceLength(tolerance[0], fieldName));
            maxlen = Integer.parseInt(replaceLength(tolerance[0], fieldName));
        } else if (2 == tolerance.length) {
            minlen = Integer.parseInt(replaceLength(tolerance[0], fieldName));
            maxlen = Integer.parseInt(replaceLength(tolerance[1], fieldName));
        } else {
            // Too many parameters.
            throw new ApplicationDataException(SystemErrorCodeConstant.ERROR_READ_PROPERTY, new IllegalStateException("Too many parameters."));
        }
        if (like) {
            minlen = 0;
        }

        return new TextValidateBean(params[0], minlen, maxlen);
    }

    //***** private method *****
    //***** call back method *****
    //***** getter and setter *****

}
