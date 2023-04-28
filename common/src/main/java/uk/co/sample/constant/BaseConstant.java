package uk.co.sample.constant;

public class BaseConstant {

    /*---------------------------------------------------------------------
     *                     Symbol
     *-------------------------------------------------------------------*/
    /** TRUE */
    public static final String  TRUE                           = "1";
    /** FALSE */
    public static final String  FALSE                          = "0";
    /** TRUE - string */
    public static final String  TRUE_STR                       = "true";
    /** FALSE - string */
    public static final String  FALSE_STR                      = "false";
    /** TRUE - boolean */
    public static final boolean TRUE_BOOL                      = true;
    /** FALSE - boolean */
    public static final boolean FALSE_BOOL                     = false;

    /** ALL - string */
    public static final String  ALL                            = "all";

    /*---------------------------------------------------------------------
     *                     DB field attributes
     *-------------------------------------------------------------------*/
    /** CHAR. */
    public static final String  CHAR                           = "CHAR";
    /** NCHAR. */
    public static final String  NCHAR                          = "NCHAR";
    /** NUMBER. */
    public static final String  NUMBER                         = "NUMBER";
    /** VARCHAR2. */
    public static final String  VARCHAR2                       = "VARCHAR2";
    /** NVARCHAR2. */
    public static final String  NVARCHAR2                      = "NVARCHAR2";
    /** DATE. */
    public static final String  DATE                           = "DATE";
    /** TIMESTAMP. */
    public static final String  TIMESTAMP                      = "TIMESTAMP";
    /** CLOB. */
    public static final String  CLOB                           = "CLOB";

    /*---------------------------------------------------------------------
     *                     Charset
     *-------------------------------------------------------------------*/
    public static final String  EBCDIC_CHARSET                 = "CP930";

    /** UTF-8 Character Set */
    public static final String  UTF8_CHARSET                   = "UTF-8";

    /** Shift_JIS Character Set */
    public static final String  SHIFT_JIS_CHARSET              = "Shift_JIS";

    /** ISO-2022-JP Character Set */
    public static final String  ISO2202JP_CHARSET              = "ISO-2022-JP";

    /*---------------------------------------------------------------------
     *                     Subsystem name
     *-------------------------------------------------------------------*/
    /** Subsystem name "Common" */
    public static final String  SUB_SYSTEM_NAME_COMMON         = "Common";

    /*---------------------------------------------------------------------
     *                     Date format pattern
     *-------------------------------------------------------------------*/
    /** header of 10 days */
    public static final String  HEADER_TD                      = "J";
    /** DATE FORMAT ORA YMDHMS */
    public static final String  PATTERN_ORA_DATE_YMDHMS_FORMAT = "yyyy/mm/dd hh24:mi:ss";
    /** TIMESTAMP pattern for ORACLE  */
    public static final String  PATTERN_ORA_TIMESTAMP          = "yy-MM-dd HH:mm:ss.SSS";
    public static final String  PATTERN_ORA_TIMESTAMP_A_S      = "yy/MM/dd HH:mm:ss.SSS";
    public static final String  PATTERN_ORA_TIMESTAMP_B        = "yy-MM-dd HH:mm:ss";
    public static final String  PATTERN_ORA_TIMESTAMP_B_S      = "yy/MM/dd HH:mm:ss";
    public static final String  PATTERN_ORA_TIMESTAMP_C        = "yy-MM-dd HH:mm";
    public static final String  PATTERN_ORA_TIMESTAMP_C_S      = "yy/MM/dd HH:mm";
    public static final String  PATTERN_ORA_TIMESTAMP_D        = "yy-MM-dd";
    public static final String  PATTERN_ORA_TIMESTAMP_E        = "yyyy-MM-dd HH:mm:ss";
    public static final String  PATTERN_ORA_TIMESTAMP_E_S      = "yyyy/MM/dd HH:mm:ss";

    public static final String  PATTERN_TIMESTAMP_A            = "yyyyMMdd HH:mm:ss.SSS";
    public static final String  PATTERN_TIMESTAMP_A_HYPHEN     = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String  PATTERN_TIMESTAMP_A_SLASH      = "yyyy/MM/dd HH:mm:ss.SSS";
    public static final String  PATTERN_TIMESTAMP_B            = "yyyyMMdd HH:mm:ss";
    public static final String  PATTERN_TIMESTAMP_B_HYPHEN     = "yyyy-MM-dd HH:mm:ss";
    public static final String  PATTERN_TIMESTAMP_B_SLASH      = "yyyy/MM/dd HH:mm:ss";
    public static final String  PATTERN_TIMESTAMP_C            = "yyyyMMdd HH:mm";
    public static final String  PATTERN_TIMESTAMP_C_HYPHEN     = "yyyy-MM-dd HH:mm";
    public static final String  PATTERN_TIMESTAMP_C_SLASH      = "yyyy/MM/dd HH:mm";
    public static final String  PATTERN_TIMESTAMP_D            = "yyyyMMdd";
    public static final String  PATTERN_TIMESTAMP_D_HYPHEN     = "yyyy-MM-dd";
    public static final String  PATTERN_TIMESTAMP_D_SLASH      = "yyyy/MM/dd";
    public static final String  PATTERN_TIMESTAMP_HMS          = "HHmmss";
    public static final String  PATTERN_TIMESTAMP_HM           = "HH:mm";

    public static final String  PATTERN_LOG_TBL_TIMESTAMP      = PATTERN_TIMESTAMP_A_HYPHEN;

    public static final String  SYSTEM_USER                    = "SYSUSER";
    //***** constructor *****
    /**
     * Constructor.<br>
     * protect to be instance.
     */
    protected BaseConstant() {
        super();
    }

    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    //***** getter and setter *****

}
