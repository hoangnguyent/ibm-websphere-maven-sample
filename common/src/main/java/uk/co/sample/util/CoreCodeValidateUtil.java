package uk.co.sample.util;

import static uk.co.sample.constant.SystemErrorCodeConstant.ERROR_LOGIC;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import uk.co.sample.constant.CodeValidateConstant;
import uk.co.sample.constant.DBFields;
import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.exception.ApplicationDataException;
import uk.co.sample.exception.ApplicationLogicException;

public class CoreCodeValidateUtil {

    private static final Pattern UPPER_ALPHA_NUMERIC                 = Pattern.compile("^[\\p{Upper}\\p{Digit}]*$");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                // for ALPHA_NUM
    private static final Pattern ALPHA_NUMERIC                       = Pattern.compile("^\\p{Alnum}*$");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            // for ALPHA_NUM

    /** ALPHA_NUM_HYPHEN validation. */
    private static final Pattern ALPHA_NUM_HYPHEN                    = Pattern.compile("^[\\p{Upper}\\p{Digit}\\-]*$");

    /** ASCII_TOKEN_KANA validation for Japanese. */
    private static final Pattern UPPER_ASCII_TOKEN_KANA              = Pattern.compile("^[[\\p{ASCII}\\uFF61-\uFF9F]&&[^|\\p{Lower}]]*$");

    private static final Pattern LOWER_ASCII                         = Pattern.compile("^[\\p{ASCII}&&[^\\p{Lower}]]*$");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           // ASCII
    private static final Pattern ASCII                               = Pattern.compile("^\\p{ASCII}*$");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            // ASCII

    private static final Pattern LOWER_ASCII_TOKEN                   = Pattern.compile("^[\\p{ASCII}&&[^|\\p{Lower}]]*$");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          // ASCII_TOKEN
    private static final Pattern ASCII_TOKEN                         = Pattern.compile("^[\\p{ASCII}&&[^|]]*$");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    // ASCII_TOKEN

    private static final Pattern ALPHA_NUMERIC_SUFFIXED_SPACE        = Pattern.compile("^[\\p{Upper}\\p{Digit}]+ *$");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              // ALPHA_NUMERIC suffixed space.
    private static final Pattern ALPHA_NUMERIC_HYPHEN_SUFFIXED_SPACE = Pattern.compile("^[\\p{Upper}\\p{Digit}\\-]+ *$");

    private static final Pattern MAIL                                = Pattern.compile(
        "(?:[^(\\040)<>@,;:\".\\\\\\[\\]\\000-\\037\\x80-\\xff]+(?![^(\\040)<>@,;:\".\\\\\\[\\]\\000-\\037\\x80-\\xff])|\"[^\\\\\\x80-\\xff\\n\\015\"]*(?:\\\\[^\\x80-\\xff][^\\\\\\x80-\\xff\\n\\015\"]*)*\")(?:\\.(?:[^(\\040)<>@,;:\".\\\\\\[\\]\\000-\\037\\x80-\\xff]+(?![^(\\040)<>@,;:\".\\\\\\[\\]\\000-\\037\\x80-\\xff])|\"[^\\\\\\x80-\\xff\\n\\015\"]*(?:\\\\[^\\x80-\\xff][^\\\\\\x80-\\xff\\n\\015\"]*)*\"))*@(?:[^(\\040)<>@,;:\".\\\\\\[\\]\\000-\\037\\x80-\\xff]+(?![^(\\040)<>@,;:\".\\\\\\[\\]\\000-\\037\\x80-\\xff])|\\[(?:[^\\\\\\x80-\\xff\\n\\015\\[\\]]|\\\\[^\\x80-\\xff])*\\])(?:\\.(?:[^(\\040)<>@,;:\".\\\\\\[\\]\\000-\\037\\x80-\\xff]+(?![^(\\040)<>@,;:\".\\\\\\[\\]\\000-\\037\\x80-\\xff])|\\[(?:[^\\\\\\x80-\\xff\\n\\015\\[\\]]|\\\\[^\\x80-\\xff])*\\]))*"); // E_MAIL see : http://www.din.or.jp/~ohzaki/perl.htm
    private static final Pattern EASY_MAIL                           = Pattern.compile("^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+([\\.][a-zA-Z0-9-]+)+$");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  // easy E_MAIL
    /** STRING validation. */
    private static final Pattern STRING                              = Pattern.compile("^[^|~\t\r\n]*$");

    /** STRING_BR validation. */
    private static final Pattern STRING_BR                           = Pattern.compile("^[^|~\t]*$");

    private static final Pattern NUMERIC                             = Pattern.compile("^-?\\p{Digit}+$");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          // NUMERIC

    private static final Pattern HTTP_URL                            = Pattern
        .compile(
            "\\b(?:https?|shttp)://(?:(?:[-_.!~*'()a-zA-Z0-9;:&=+$,]|%[0-9A-Fa-f][0-9A-Fa-f])*@)?(?:(?:[a-zA-Z0-9](?:[-a-zA-Z0-9]*[a-zA-Z0-9])?\\.)*[a-zA-Z](?:[-a-zA-Z0-9]*[a-zA-Z0-9])?\\.?|[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+)(?::[0-9]*)?(?:/(?:[-_.!~*'()a-zA-Z0-9:@&=+$,]|%[0-9A-Fa-f][0-9A-Fa-f])*(?:;(?:[-_.!~*'()a-zA-Z0-9:@&=+$,]|%[0-9A-Fa-f][0-9A-Fa-f])*)*(?:/(?:[-_.!~*'()a-zA-Z0-9:@&=+$,]|%[0-9A-Fa-f][0-9A-Fa-f])*(?:;(?:[-_.!~*'()a-zA-Z0-9:@&=+$,]|%[0-9A-Fa-f][0-9A-Fa-f])*)*)*)?(?:\\?(?:[-_.!~*'()a-zA-Z0-9;/?:@&=+$,]|%[0-9A-Fa-f][0-9A-Fa-f])*)?(?:#(?:[-_.!~*'()a-zA-Z0-9;/?:@&=+$,]|%[0-9A-Fa-f][0-9A-Fa-f])*)?");                                                                                                                                                         // for URL. http://www.din.or.jp/~ohzaki/perl.htm

    private static final Pattern IP_ADDRESS                          = Pattern
        .compile("^(25[0-5]|(2[0-4]|1[0-9]|[1-9])?[0-9])\\." + "(25[0-5]|(2[0-4]|1[0-9]|[1-9])?[0-9])\\." + "(25[0-5]|(2[0-4]|1[0-9]|[1-9])?[0-9])\\." + "(25[0-5]|(2[0-4]|1[0-9]|[1-9])?[0-9])$");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 // IP address

    private static final int     UNICODE_0XFF61                      = 0xFF61;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      // Unicode 0xFF61
    private static final int     UNICODE_0XFF9F                      = 0xFF9F;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      // Unicode 0xFF9F

    //***** constructor *****
    //***** public method *****
    /**
     * Check if the given value is valid.<br>
     *
     * @param key String Key for validate.properties.
     * @param value String The value for validation.
     * @return boolean true : if value is valid. / false : if not.
     */
    public static boolean isValid(String key, String value) {

        if (null == value) {
            return false;
        }

        if (hasTab(value)) {
            return false;
        }

        // Get property from properties.
        AbstractValidateBean bean = ValidateUtilHelper.obtainProperty(key, false);

        List<String> textClasses = Arrays.asList(CodeValidateConstant.UPPER_ALPHA_NUM, CodeValidateConstant.ALPHA_NUM,
            CodeValidateConstant.ALPHA_NUM_SPACE, CodeValidateConstant.ALPHA_NUMERIC_SUFFIXED_SPACE,
            CodeValidateConstant.ALPHA_NUMERIC_HYPHEN_SUFFIXED_SPACE, CodeValidateConstant.UPPER_ASCII,
            CodeValidateConstant.ASCII, CodeValidateConstant.UPPER_ASCII_TOKEN,
            CodeValidateConstant.ASCII_TOKEN, CodeValidateConstant.UPPER_ASCII_NOT_WHITESPACES,
            CodeValidateConstant.ASCII_NOT_WHITESPACES, CodeValidateConstant.UPPER_ASCII_TOKEN_KANA,
            CodeValidateConstant.ALPHA_NUM_HYPHEN, CodeValidateConstant.E_MAIL,
            CodeValidateConstant.FILE_NAME, CodeValidateConstant.IP_ADDRESS,
            CodeValidateConstant.STRING, CodeValidateConstant.URL,
            CodeValidateConstant.STRING_BR, CodeValidateConstant.STRING_LENB_W31J);

        List<String> numberClasses = Arrays.asList(CodeValidateConstant.POSITIVE_INTEGER, CodeValidateConstant.LONG, CodeValidateConstant.BIGDECIMAL);

        if (StringUtils.isBlank(bean.getType())) {

            return true;

        } else if ((textClasses.contains(bean.getType()) && !(bean instanceof TextValidateBean))
            ||
            (numberClasses.contains(bean.getType()) && !(bean instanceof NumericValidateBean))) {
            throw new ApplicationLogicException(ERROR_LOGIC, new IllegalStateException(key + "=" + bean.getType()));

        } else if (CodeValidateConstant.UPPER_ALPHA_NUM.equals(bean.getType())) {
            return isValidAlphanumericUpper((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.ALPHA_NUM.equals(bean.getType())) {
            return isValidAlphaNumeric((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.ALPHA_NUM_SPACE.equals(bean.getType())) {
            return isValidAlphaNumericSpace((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.ALPHA_NUMERIC_SUFFIXED_SPACE.equals(bean.getType())) {
            return isValidAlphaNumericSuffixedSpace((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.ALPHA_NUMERIC_HYPHEN_SUFFIXED_SPACE.equals(bean.getType())) {
            return isValidAlphaNumericHyphenSuffixedSpace((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.UPPER_ASCII.equals(bean.getType())) {
            return isValidAsciiUpper((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.ASCII.equals(bean.getType())) {
            return isValidAscii((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.UPPER_ASCII_TOKEN.equals(bean.getType())) {
            return isValidAsciiUpperToken((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.ASCII_TOKEN.equals(bean.getType())) {
            return isValidAsciiToken((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.UPPER_ASCII_NOT_WHITESPACES.equals(bean.getType())) {
            return isValidAsciiUpperNotWhitespaces((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.ASCII_NOT_WHITESPACES.equals(bean.getType())) {
            return isValidAsciiNotWhitespaces((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.POSITIVE_INTEGER.equals(bean.getType())) {
            return isPositiveInteger(value);
        } else if (CodeValidateConstant.LONG.equals(bean.getType())) {
            return isValidLong((NumericValidateBean) bean, value);
        } else if (CodeValidateConstant.BIGDECIMAL.equals(bean.getType())) {
            return NumberUtils.isCreatable(value);
        } else if (CodeValidateConstant.BOOLEAN.equals(bean.getType())) {
            return isValidBoolean(value);
        } else if (CodeValidateConstant.UPPER_ASCII_TOKEN_KANA.equals(bean.getType())) {
            return isValidAsciiTokenUpperKana((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.ALPHA_NUM_HYPHEN.equals(bean.getType())) {
            return isValidAlphaNumHyphen((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.DATE_FULL.equals(bean.getType())) {
            return isValidDateFull(value);
        } else if (CodeValidateConstant.DATE_YYYYMM.equals(bean.getType())) {
            return isValidDateYYYYMM(value);
        } else if (CodeValidateConstant.DATE_MMYYYY.equals(bean.getType())) {
            return isValidMMYYYY(value);
        } else if (CodeValidateConstant.E_MAIL.equals(bean.getType())) {
            return isValidEMail((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.FILE_NAME.equals(bean.getType())) {
            return isValidFilename((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.IP_ADDRESS.equals(bean.getType())) {
            return isValidIpAddress(value);
        } else if (CodeValidateConstant.STRING.equals(bean.getType())) {
            return isValidString((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.TIMESTAMP.equals(bean.getType())) {
            return isValidTimestamp(value);
        } else if (CodeValidateConstant.URL.equals(bean.getType())) {
            return isValidUrl((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.STRING_BR.equals(bean.getType())) {
            return isValidStringBr((TextValidateBean) bean, value);
        } else if (CodeValidateConstant.STRING_LENB_W31J.equals(bean.getType())) {
            return isValidStringLenBWind31J((TextValidateBean) bean, value);
        }
        return false;
    }

    /**
     * Check if the given value is valid in SQL-like statement.<br>
     *
     * @param key String
     * @param value String
     * @return boolean true : if value is valid in SQL-like statement. / false : if not.
     */
    public static boolean isValidAsLike(String key, String value) {

        if (StringUtils.isBlank(value)) {
            return true;
        }

        if (hasTab(value)) {
            return false;
        }

        // Get property from properties.
        List<Object> validateArray = ValidateUtilHelper.getProperty(key);
        String validateType = (String) validateArray.get(0);

        if (0 == validateType.length()) {
            return true;
        }

        return switchValidateAsLike(value, validateArray);
    }

    //***** protected method *****
    /**
     * Check ALLOC.<br>
     *
     * @param bean TextValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidAlloc(TextValidateBean bean, String value) {
        return isAlloc(value, bean.getMinlen(), bean.getMaxlen());
    }

    /**
     * Check ALPHA_NUM.<br>
     *
     * @param bean TextValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidAlphanumericUpper(TextValidateBean bean, String value) {
        return isAlphanumericUpper(value) && isLengthBetween(value, bean.getMinlen(), bean.getMaxlen());
    }

    /**
     * Check Alpha_NUM.<br>
     *
     * @param bean TextValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidAlphaNumeric(TextValidateBean bean, String value) {
        return isAlphaNumeric(value) && isLengthBetween(value, bean.getMinlen(), bean.getMaxlen());
    }

    /**
     * Check ALPHA_NUM_SPACE.<br>
     *
     * @param bean TextValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidAlphaNumericSpace(TextValidateBean bean, String value) {

        if (0 == value.trim().length()) { // Do not allow all space.
            return false;
        }

        return isAlphaNumericOrWhiteSpace(value) && isLengthBetween(value, bean.getMinlen(), bean.getMaxlen());
    }

    /**
     * Check ALPHA_NUMERIC_SUFFIXED_SPACE.<br>
     *
     * @param bean TextValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidAlphaNumericSuffixedSpace(TextValidateBean bean, String value) {
        return isAlphaNumericSuffixedSpace(value, bean.getMinlen(), bean.getMaxlen());
    }

    /**
     * Checks if is valid alpha numeric hyphen suffixed space.
     *
     * @param bean the bean
     * @param value the value
     * @return true, if is valid alpha numeric hyphen suffixed space
     */
    protected static boolean isValidAlphaNumericHyphenSuffixedSpace(TextValidateBean bean, String value) {
        return isAlphaNumericHyphenSuffixedSpace(value, bean.getMinlen(), bean.getMaxlen());
    }

    /**
     * Check ASCII.<br>
     *
     * @param bean TextValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidAsciiUpper(TextValidateBean bean, String value) {
        return isAsciiUpper(value) && isLengthBetween(value, bean.getMinlen(), bean.getMaxlen());
    }

    /**
     * Check Ascii.<br>
     *
     * @param bean TextValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidAscii(TextValidateBean bean, String value) {
        return isAscii(value) && isLengthBetween(value, bean.getMinlen(), bean.getMaxlen());
    }

    /**
     * Check ASCII_TOKEN.<br>
     *
     * @param bean TextValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidAsciiUpperToken(TextValidateBean bean, String value) {
        return isAsciiUpperToken(value) && isLengthBetween(value, bean.getMinlen(), bean.getMaxlen());
    }

    /**
     * Check Ascii_TOKEN.<br>
     *
     * @param bean TextValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidAsciiToken(TextValidateBean bean, String value) {
        return isAsciiToken(value) && isLengthBetween(value, bean.getMinlen(), bean.getMaxlen());
    }

    /**
     * Check ASCII_NOT_WHITESPACES.<br>
     *
     * @param bean TextValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidAsciiUpperNotWhitespaces(TextValidateBean bean, String value) {
        return isAsciiUpper(value) && isLengthBetween(value, bean.getMinlen(), bean.getMaxlen()) && !StringUtils.isWhitespace(value);
    }

    /**
     * Check Ascii_NOT_WHITESPACES.<br>
     *
     * @param bean TextValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidAsciiNotWhitespaces(TextValidateBean bean, String value) {
        return isAscii(value) && isLengthBetween(value, bean.getMinlen(), bean.getMaxlen()) && !StringUtils.isWhitespace(value);
    }

    /**
     * Check BOOLEAN.<br>
     *
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidBoolean(String value) {
        return isBoolean(value);
    }

    /**
     * Check DATE_FULL.<br>
     *
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidDateFull(String value) {
        return isDate(value);
    }

    /**
     * Check DATE_YYYYMM.<br>
     *
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidDateYYYYMM(String value) {
        return isDateYYYYMM(value);
    }

    protected static boolean isDateYYYYMMAsLike(String dateStr) {
        if (dateStr.length() > "YYYYMM".length()) {
            return false;
        }
        if (!ValidateUtil.isNumeric(dateStr)) {
            return false;
        }

        if (dateStr.length() > "YYYY".length()) {
            int value5 = Integer.parseInt(dateStr.substring("YYYY".length(), "YYYYM".length()));
            return 0 == value5 || 1 == value5;
        }
        return true;
    }

    /**
     * Check E_MAIL.<br>
     *
     * @param bean TextValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidEMail(TextValidateBean bean, String value) {
        return isEasyMailAddr(value) && isLengthBetween(value, bean.getMinlen(), bean.getMaxlen());
    }

    /**
     * Check FILE_NAME.<br>
     *
     * @param bean TextValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidFilename(TextValidateBean bean, String value) {
        return isFileName(value, bean.getMinlen(), bean.getMaxlen());
    }

    /**
     * Check IP_ADDRESS.<br>
     *
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidIpAddress(String value) {
        return isIPAddress(value);
    }

    /**
     * Check Long.<br>
     *
     * @param bean LongValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidLong(NumericValidateBean bean, String value) {

        BigDecimal minLongValueInBigDecimal = new BigDecimal(Long.MIN_VALUE);
        BigDecimal maxLongValueInBigDecimal = new BigDecimal(Long.MAX_VALUE);
        if (minLongValueInBigDecimal.compareTo(bean.getMinvalue()) > 0 || minLongValueInBigDecimal.compareTo(bean.getMaxvalue()) > 0 ||
            maxLongValueInBigDecimal.compareTo(bean.getMinvalue()) < 0 || maxLongValueInBigDecimal.compareTo(bean.getMaxvalue()) < 0) {
            throw new ApplicationDataException(SystemErrorCodeConstant.ERROR_DEFINE_PROPERTY, new String[] { bean.getType() });
        }

        if (!isNumeric(value)) {
            return false;
        }

        if (maxLongValueInBigDecimal.compareTo(new BigDecimal(value)) < 0) {
            return false;
        }

        return bean.getMinvalue().longValue() <= Long.valueOf(value) && Long.valueOf(value) <= bean.getMaxvalue().longValue();
    }

    /**
     * Check STRING.<br>
     *
     * @param bean TextValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidString(TextValidateBean bean, String value) {
        return STRING.matcher(value).matches() && isLengthBetween(value, bean.getMinlen(), bean.getMaxlen()) && isWindows31J(value);
    }

    /**
     * Check TIMESTAMP.<br>
     *
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidTimestamp(String value) {
        return isTimestamp(value);
    }

    /**
     * Check URL.<br>
     *
     * @param bean TextValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidUrl(TextValidateBean bean, String value) {
        return isURL(value) && isLengthBetween(value, bean.getMinlen(), bean.getMaxlen());
    }

    /**
     * Switch validateAsLike.<br>
     *
     * @param value String
     * @param validateArray List
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean switchValidateAsLike(String value, List<Object> validateArray) {

        String validateType = (String) validateArray.get(0);
        Object operand = validateArray.get(1);

        // Switching validate.
        if (CodeValidateConstant.DATE_FULL.equals(validateType)) {

            return isDate(value);

        } else if (CodeValidateConstant.DATE_YYYYMM.equals(validateType)) {

            return isDateYYYYMMAsLike(value);

        } else if (CodeValidateConstant.TIMESTAMP.equals(validateType)) {

            return isTimestamp(value);

        } else if (CodeValidateConstant.BIGDECIMAL.equals(validateType)) {

            if (null == operand) {
                throw new NullPointerException(validateType + " Validate : operand is not specified.");
            } else if (operand instanceof BigDecimal) {

                BigDecimal bigDecimalValue = new BigDecimal(value);
                BigDecimal minval = (BigDecimal) validateArray.get(1);
                BigDecimal maxval = (BigDecimal) validateArray.get(2);

                return 0 <= bigDecimalValue.compareTo(minval) && 0 >= bigDecimalValue.compareTo(maxval);

            } else {
                throw new NullPointerException(validateType + " Validate : operand is not specified.");
            }

        } else if (CodeValidateConstant.LONG.equals(validateType)) {

            if (null == operand) {
                return isNumeric(value);
            } else if (operand instanceof Long) {
                if (isNumeric(value)) {
                    if (((Long) validateArray.get(1)).longValue() <= Long.parseLong(value) && Long.parseLong(value) <= ((Long) validateArray.get(2)).longValue()) {
                        return true;
                    }
                } else {
                    return false;
                }
            } else if (operand instanceof Long[]) {
                throw new NullPointerException(validateType + " Validate : operand is not specified.");
            }

        } else if (CodeValidateConstant.UPPER_ASCII.equals(validateType)) {

            if (null == operand) {
                return isAsciiUpper(value);
            } else if (operand instanceof Integer) {
                return isAsciiUpper(value) && isLengthBetween(value, 1, ((Integer) operand).intValue());
            } else if (operand instanceof Integer[]) {
                return isAsciiUpper(value) && isLengthBetween(value, 0, ((Integer[]) operand)[1].intValue());
            }

        } else if (CodeValidateConstant.ASCII.equals(validateType)) {

            if (null == operand) {
                return isAscii(value);
            } else if (operand instanceof Integer) {
                return isAscii(value) && isLengthBetween(value, 1, ((Integer) operand).intValue());
            } else if (operand instanceof Integer[]) {
                return isAscii(value) && isLengthBetween(value, 0, ((Integer[]) operand)[1].intValue());
            }

        } else if (CodeValidateConstant.UPPER_ASCII_NOT_WHITESPACES.equals(validateType)) {

            if (null == operand) {
                return isAsciiUpper(value);
            } else if (operand instanceof Integer) {
                return isAsciiUpper(value) && isLengthBetween(value, 1, ((Integer) operand).intValue());
            } else if (operand instanceof Integer[]) {
                return isAsciiUpper(value) && isLengthBetween(value, 0, ((Integer[]) operand)[1].intValue());
            }

        } else if (CodeValidateConstant.ASCII_NOT_WHITESPACES.equals(validateType)) {

            if (null == operand) {
                return isAscii(value);
            } else if (operand instanceof Integer) {
                return isAscii(value) && isLengthBetween(value, 1, ((Integer) operand).intValue());
            } else if (operand instanceof Integer[]) {
                return isAscii(value) && isLengthBetween(value, 0, ((Integer[]) operand)[1].intValue());
            }

        } else if (CodeValidateConstant.STRING.equals(validateType)) {

            if (null == operand) {
                throw new NullPointerException(validateType + " Validate : operand is not specified.");
            } else if (operand instanceof Integer) {
                return isLengthBetween(value, 1, ((Integer) operand).intValue());
            } else if (operand instanceof Integer[]) {
                return isLengthBetween(value, 0, ((Integer[]) operand)[1].intValue());
            }

        } else if (CodeValidateConstant.UPPER_ALPHA_NUM.equals(validateType)) {

            if (null == operand) {
                return isAlphanumericUpper(value);
            } else if (operand instanceof Integer) {
                return isAlphanumericUpper(value) && isLengthBetween(value, 1, ((Integer) operand).intValue());
            } else if (operand instanceof Integer[]) {
                return isAlphanumericUpper(value) && isLengthBetween(value, 0, ((Integer[]) operand)[1].intValue());
            }

        } else if (CodeValidateConstant.ALPHA_NUM.equals(validateType)) {

            if (null == operand) {
                return isAlphaNumeric(value);
            } else if (operand instanceof Integer) {
                return isAlphaNumeric(value) && isLengthBetween(value, 1, ((Integer) operand).intValue());
            } else if (operand instanceof Integer[]) {
                return isAlphaNumeric(value) && isLengthBetween(value, 0, ((Integer[]) operand)[1].intValue());
            }

        } else if (CodeValidateConstant.ALPHA_NUM_SPACE.equals(validateType)) {

            if (null == operand) {
                return isAlphaNumericOrWhiteSpace(value);
            } else if (operand instanceof Integer) {
                return isAlphaNumericOrWhiteSpace(value) && isLengthBetween(value, 1, ((Integer) operand).intValue());
            } else if (operand instanceof Integer[]) {
                return isAlphaNumericOrWhiteSpace(value) && isLengthBetween(value, 0, ((Integer[]) operand)[1].intValue());
            }

        } else if (CodeValidateConstant.UPPER_ASCII_TOKEN.equals(validateType)) {

            if (null == operand) {
                return isAsciiUpperToken(value);
            } else if (operand instanceof Integer) {
                return isAsciiUpperToken(value) && isLengthBetween(value, 1, ((Integer) operand).intValue());
            } else if (operand instanceof Integer[]) {
                return isAsciiUpperToken(value) && isLengthBetween(value, 0, ((Integer[]) operand)[1].intValue());
            }

        } else if (CodeValidateConstant.ASCII_TOKEN.equals(validateType)) {

            if (null == operand) {
                return isAsciiToken(value);
            } else if (operand instanceof Integer) {
                return isAsciiToken(value) && isLengthBetween(value, 1, ((Integer) operand).intValue());
            } else if (operand instanceof Integer[]) {
                return isAsciiToken(value) && isLengthBetween(value, 0, ((Integer[]) operand)[1].intValue());
            }

        } else if (CodeValidateConstant.E_MAIL.equals(validateType)) {

            if (null == operand) {
                return isEasyMailAddr(value);
            } else if (operand instanceof Integer) {
                return isEasyMailAddr(value) && isLengthBetween(value, 1, ((Integer) operand).intValue());
            } else if (operand instanceof Integer[]) {
                return isEasyMailAddr(value) && isLengthBetween(value, 0, ((Integer[]) operand)[1].intValue());
            }

        } else if (CodeValidateConstant.URL.equals(validateType)) {

            if (null == operand) {
                return isURL(value);
            } else if (operand instanceof Integer) {
                return isURL(value) && isLengthBetween(value, 1, ((Integer) operand).intValue());
            } else if (operand instanceof Integer[]) {
                return isURL(value) && isLengthBetween(value, 0, ((Integer[]) operand)[1].intValue());
            }

        } else if (CodeValidateConstant.BOOLEAN.equals(validateType)) {

            if (null == operand) {
                return isBoolean(value);
            } else if (operand instanceof Integer) {
                return isBoolean(value) && isLengthBetween(value, 1, ((Integer) operand).intValue());
            } else if (operand instanceof Integer[]) {
                return isBoolean(value) && isLengthBetween(value, 0, ((Integer[]) operand)[1].intValue());
            }

        } else if (CodeValidateConstant.FILE_NAME.equals(validateType)) {

            if (null == operand) {
                throw new NullPointerException(validateType + " Validate : operand is not specified.");
            } else if (operand instanceof Integer) {
                return isFileNameAsLike(value, 1, ((Integer) operand).intValue());
            } else if (operand instanceof Integer[]) {
                return isFileNameAsLike(value, 0, ((Integer[]) operand)[1].intValue());
            }

        } else if (CodeValidateConstant.IP_ADDRESS.equals(validateType)) {

            return true;
        } else if (CodeValidateConstant.ALPHA_NUMERIC_SUFFIXED_SPACE.equals(validateType)) {
            if (null == operand) {
                throw new NullPointerException(validateType + " Validate : operand is not specified.");
            } else if (operand instanceof Integer) {
                return isAlphaNumericSuffixedSpace(value, 1, ((Integer) operand).intValue());
            } else if (operand instanceof Integer[]) {
                return isAlphaNumericSuffixedSpace(value, 0, ((Integer[]) operand)[1].intValue());
            }

        }

        return false;
    }

    /**
     * Check if the given string is a valid URL.<br>
     *
     * @param url String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if url is a valid URL, not checking if it exists, just as a URL string. / false : if not.
     * @see "http://www.din.or.jp/~ohzaki/perl.htm"
     */
    protected static boolean isURL(String url) {
        return HTTP_URL.matcher(url).matches();
    }

    /**
     * Check if given string is a valid e-mail address.<br>
     *
     * Saved for the case that EASY_MAIL does not satisfy
     * the requirements of mail address validating.
     * <br>
     *
     * @param mailAddr String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if mailAddr is a valid e-mail address. / false : if not.
     * @see "http://www.din.or.jp/~ohzaki/perl.htm"
     */
    protected static boolean isMailAddr(String mailAddr) {
        return MAIL.matcher(mailAddr).matches();
    }

    /**
     * Check if given string is a valid e-mail address easily.<br>
     *
     * @param mailAddr String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if mailAddr is a valid e-mail address. (easy check) / false : if not.
     */
    protected static boolean isEasyMailAddr(String mailAddr) {
        return EASY_MAIL.matcher(mailAddr).matches();
    }

    /**
     * Check if given string is yyyymmdd or yyyy/mm/dd or yyyy-mm-dd.<br>
     *
     * @param dateStr String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if dateStr exists. / false : if not.
     */
    protected static boolean isDate(String dateStr) {

        String source = dateStr;

        if (source.matches("[0-9][0-9][0-9][0-9][0-1][0-9][0-3][0-9]")) {

            String year = source.substring(0, "YYYY".length());
            String month = source.substring("YYYY".length(), "YYYY".length() + "MM".length());
            String day = source.substring("YYYY".length() + "MM".length(), "YYYY".length() + "MM".length() + "DD".length());

            source = year + "/" + month + "/" + day;
        }

        source = source.replace('-', '/');

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
        sdf.setLenient(false);
        try {
            sdf.parse(source);
        } catch (ParseException ex) {
            return false;
        }
        return true;
    }

    /**
     * Check if given string is yyyymm.<br>
     *
     * @param dateStr String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if dateStr exists. / false : if not.
     */
    protected static boolean isDateYYYYMM(String dateStr) {
        return isDate(dateStr + "01");
    }

    /**
     * Check if given string is matches timestamp.<br>
     *
     * @param value String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if str is timestamp. / false : if not.
     */
    protected static boolean isTimestamp(String value) {

        try {
            DateUtil.convertToDate(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if the given string consists of only white spaces.<br>
     *
     * @param value String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if value is white spaces. / false : if not.
     */
    protected static boolean isWhiteSpace(String value) {

        boolean isWhiteSpace = true;

        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            // if (!Character.isWhitespace(_c)) {
            if (c != ' ') {
                isWhiteSpace = false;
                break;
            }
        }
        return isWhiteSpace;
    }

    /**
     * validate value is an numeric.<br>
     *
     * @param value String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if value are only number. / false : if not.
     */
    protected static boolean isNumeric(String value) {
        return NUMERIC.matcher(value).matches();
    }

    /**
     * validate value is a bigDecimal.<br>
     *
     * @param value String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if value are only bigDecimal. / false : if not.
     */
    protected static boolean isBigDecimal(String value) {

        try {
            new BigDecimal(value);
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    /**
     * isAlphaNumeric.<br>
     *
     * @param value String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if value are only alphabet or number. / false : if value is null.
     */
    protected static boolean isAlphanumericUpper(String value) {

        return UPPER_ALPHA_NUMERIC.matcher(value).matches();
    }

    /**
     * isAlphaNumeric.<br>
     *
     * @param value String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if value are only alphabet or number. / false : if value is null.
     */
    protected static boolean isAlphaNumeric(String value) {
        return ALPHA_NUMERIC.matcher(value).matches();
    }

    /**
     * isAlphaNumericOrWhiteSpace.<br>
     *
     * @param value String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if value are only alphabet or number or white space. / false : if not.
     */
    protected static boolean isAlphaNumericOrWhiteSpace(String value) {

        boolean isAlphaNumericOrWhiteSpace = true;

        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);

            if (!(isAlphanumericUpper(String.valueOf(c)) || isWhiteSpace(String.valueOf(c)))) {

                isAlphaNumericOrWhiteSpace = false;
                break;
            }
        }
        return isAlphaNumericOrWhiteSpace;
    }

    /**
     * Check if value are only ascii character without lower character.<br>
     *
     * @param value String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if value are only ascii character without lower character. / false : if not.
     */
    protected static boolean isAsciiUpper(String value) {
        return LOWER_ASCII.matcher(value).matches();
    }

    /**
     * Check if value are only ascii character.<br>
     *
     * @param value String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if value are only ascii character. / false : if not.
     */
    protected static boolean isAscii(String value) {
        return ASCII.matcher(value).matches();
    }

    /**
     * Check if the given string is an ascii token without lower character.<br>
     *
     * @param value String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if the given string is an ascii token without lower character. / false : if not.
     */
    protected static boolean isAsciiUpperToken(String value) {
        return LOWER_ASCII_TOKEN.matcher(value).matches();
    }

    /**
     * Check if the given string is an ascii token.<br>
     *
     * @param value String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if the given string is an ascii token. / false : if not.
     */
    protected static boolean isAsciiToken(String value) {
        return ASCII_TOKEN.matcher(value).matches();
    }

    /**
     * isAlphaNumericSuffixedSpace.<br>
     *
     * @param value String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @param from int
     * @param to int
     * @return boolean
     */
    protected static boolean isAlphaNumericSuffixedSpace(String value, int from, int to) {

        return ALPHA_NUMERIC_SUFFIXED_SPACE.matcher(value).matches() && isLengthBetween(value, from, to);
    }

    /**
     * Checks if is alpha numeric hyphen suffixed space.
     *
     * @param value the value
     * @param from the from
     * @param to the to
     * @return true, if is alpha numeric hyphen suffixed space
     */
    protected static boolean isAlphaNumericHyphenSuffixedSpace(String value, int from, int to) {

        return ALPHA_NUMERIC_HYPHEN_SUFFIXED_SPACE.matcher(value).matches() && isLengthBetween(value, from, to);
    }

    /**
     * Check if the given string is a boolean value ("0" or "1").<br>
     *
     * @param value String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if value is a boolean value ("0" or "1"). / false : if not.
     */
    protected static boolean isBoolean(String value) {

        return "0".equals(value) || "1".equals(value);
    }

    /**
     * Check if the given string is a alloc code starting string with length between from and to.<br>
     *
     * @param value String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @param from int
     * @param to int
     * @return boolean true : check if value is a alloc code starting string with length between from and to. / false : if not.
     */
    protected static boolean isAlloc(String value, int from, int to) {

        if (0 == value.trim().length()) {
            // Do not allow all spaces.
            return false;
        } else {
            return isAlphaNumericOrWhiteSpace(value) && isLengthBetween(value, from, to);
        }
    }

    /**
     * Check if the given string is a FILE_NAME starting string with length between from and to.<br>
     *
     * @param value String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @param from int
     * @param to int
     * @return boolean true : check if value is a file name starting string with length between from and to. / false : if not.
     */
    protected static boolean isFileName(String value, int from, int to) {

        if (0 == value.trim().length()) {
            // Do not allow all spaces.
            return false;
        } else {
            //return FILE_NAME.matcher(value).matches() && isLengthBetween(value, from, to);
            return isLengthBetween(value, from, to);
        }
    }

    /**
     * Check if the given string is a FILE_NAME starting string with length between from and to.<br>
     *
     * @param value String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @param from int
     * @param to int
     * @return boolean true : check if value is a file name starting string with length between from and to. / false : if not.
     */
    protected static boolean isFileNameAsLike(String value, int from, int to) {

        if (0 == value.trim().length()) {
            // Do not allow all spaces.
            return false;
        } else {
            return isFileName(value, from, to);
        }
    }

    /**
     * isContainsHalfKana.<br>
     *
     * @param str String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean
     */
    protected static boolean isContainsHalfKana(String str) {

        if (0 == str.length()) {
            return false;
        } else {

            StringBuilder bld = new StringBuilder(str);
            for (int i = 0; i < bld.length(); i++) {
                // Confirm (Unicode 0xFF61 - 0xFF9F)
                if (UNICODE_0XFF61 <= bld.charAt(i) && bld.charAt(i) <= UNICODE_0XFF9F) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if given value is IP address.<br>
     *
     * @param value String This parameter does not become NULL. Checked in 'isValid()' or 'isValidAsLike()'.
     * @return boolean true : if value is ip address. / false : if not.
     */
    protected static boolean isIPAddress(String value) {
        return IP_ADDRESS.matcher(value).matches();
    }

    /**
     * Check if given value has tab.
     * <br>
     * @param value String
     * @return boolean true : if value has tab. / false : does not have.
     */
    protected static boolean hasTab(String value) {
        return value.matches("^(.*)\\t(.*)$");
    }

    /**
     * Validate length between specified length.<br>
     *
     * @param value String
     * @param min int
     * @param max int
     * @return true if value.length() is between min and max. if value is null, it treats as a length zero string.
     */
    public static boolean isLengthBetween(String value, int min, int max) {

        int length = (null == value) ? 0 : value.length();

        return (min <= length) && (length <= max);
    }

    /**
     * Check STRING_LENB_WIN31J.<br>
     *
     * @param bean TextValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidStringLenBWind31J(TextValidateBean bean, String value) {
        int length = (null == value) ? 0 : getLengthWindows31J(value);
        return STRING.matcher(value).matches() && (bean.getMinlen() <= length) && (length <= bean.getMaxlen()) && isWindows31J(value);
    }

    /**
     * Check STRING_BR.<br>
     *
     * @param bean TextValidateBean
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidStringBr(TextValidateBean bean, String value) {
        return STRING_BR.matcher(value).matches() && isLengthBetween(value, bean.getMinlen(), bean.getMaxlen()) && isWindows31J(value);
    }

    private static int getLengthWindows31J(String text) {
        try {
            return text.getBytes(CodeValidateConstant.CHARSET_WINDOWS31J.name()).length;
        } catch (UnsupportedEncodingException e) {
            throw new ApplicationLogicException(SystemErrorCodeConstant.ERROR_LOGIC);
        }
    }

    /**
     * Windows-31J charset.
     * <br>
     * @param text
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isWindows31J(String text) {
        if (null == text) {
            return true;
        }
        return text.contentEquals(CodeValidateConstant.CHARSET_WINDOWS31J.decode(CodeValidateConstant.CHARSET_WINDOWS31J.encode(text)));
    }

    /**
     * ALPHA_NUM_HYPHEN <br>
     *
     * @param bean TextValidateBean
     * @param value String
     * @return boolean
     */
    protected static boolean isValidAlphaNumHyphen(TextValidateBean bean, String value) {

        return ALPHA_NUM_HYPHEN.matcher(value).matches() && isLengthBetween(value, bean.getMinlen(), bean.getMaxlen());
    }

    /**
     * ASCII_TOKEN_KANA <br>
     *
     * @param bean TextValidateBean
     * @param value String
     * @return boolean
     */
    protected static boolean isValidAsciiTokenUpperKana(TextValidateBean bean, String value) {

        return UPPER_ASCII_TOKEN_KANA.matcher(value).matches() && isLengthBetween(value, bean.getMinlen(), bean.getMaxlen());
    }

    /**
     * Check DATE_MMYYYY.<br>
     *
     * @param value String Specified check.
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isValidMMYYYY(String value) {

        String source = "01" + value;

        if (source.matches("[0-3][0-9][0-1][0-9][0-9][0-9][0-9][0-9]")) {

            String day = source.substring(0, "DD".length());
            String month = source.substring("DD".length(), "DD".length() + "MM".length());
            String year = source.substring("DD".length() + "MM".length(), "DD".length() + "MM".length() + "YYYY".length());

            source = day + "/" + month + "/" + year;
        }

        source = source.replace('-', '/');

        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(source);
        } catch (ParseException ex) {
            return false;
        }
        return true;
    }

    /**
     * <br>
     * Validate number
     *
     * @param value
     * @return boolean
     */
    public static boolean isPositiveInteger(String value) {
        return Pattern.compile("^[\\p{Digit}&&[^0]]+[\\p{Digit}]*$").matcher(value).matches();
    }

    /**
     * Hangul-Korean charset.
     * <br>
     * @param text
     * @return boolean true : if value is valid. / false : if value is invalid.
     */
    protected static boolean isHangulKorean(String text) {
        if (null == text) {
            return true;
        }
        return text.contentEquals(CodeValidateConstant.CHARSET_EUC_KR.decode(CodeValidateConstant.CHARSET_EUC_KR.encode(text)));
    }

    //***** Private method *****
    //***** call back methods *****
    //***** getter and Setter *****
    public static void main(String[] args) {
        System.out.println("ANNOUNCEMENT_SAMPLE.STATUS = longer_than_definition_in_properties valid:" + isValid("ANNOUNCEMENT_SAMPLE.STATUS", "longer_than_definition_in_properties"));
        System.out.println("ANNOUNCEMENT_SAMPLE.VERSION = 12345678 is valid:" + isValid(DBFields.ANNOUNCEMENT_SAMPLE.VERSION, "12345678"));
        System.out.println("ANNOUNCEMENT_SAMPLE.VERSION = ① is valid:" + isValid(DBFields.ANNOUNCEMENT_SAMPLE.VERSION, "①"));
        System.out.println("DEMO_ANNOUNCE_TEXT.ANNOUNCE_LANG = 2 is valid:" + isValid(DBFields.DEMO_ANNOUNCE_TEXT.ANNOUNCE_LANG, "2"));
    }
}
