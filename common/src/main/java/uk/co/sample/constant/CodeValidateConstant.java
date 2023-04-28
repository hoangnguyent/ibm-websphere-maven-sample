package uk.co.sample.constant;

import java.nio.charset.Charset;

public class CodeValidateConstant {

    private CodeValidateConstant() {
        // Do nothing
    }

    /** ALPHA_NUM (Alphabet and Numeric. Do not allow lower characters.) */
    public static final String  UPPER_ALPHA_NUM                     = "ALPHA_NUM";

    /** Alpha_NUM (Alphabet and Numeric.) */
    public static final String  ALPHA_NUM                           = "Alpha_NUM";

    /** Alphabet and Numeric and Space */
    public static final String  ALPHA_NUM_SPACE                     = "ALPHA_NUM_SPACE";

    /** ALPHA_NUMERIC_SUFFIXED_SPACE. */
    public static final String  ALPHA_NUMERIC_SUFFIXED_SPACE        = "ALPHA_NUMERIC_SUFFIXED_SPACE";

    /** ALPHA_NUMERIC_HYPHEN_SUFFIXED_SPACE. */
    public static final String  ALPHA_NUMERIC_HYPHEN_SUFFIXED_SPACE = "ALPHA_NUMERIC_HYPHEN_SUFFIXED_SPACE";

    /** ASCII (Do not allow lower characters.) */
    public static final String  UPPER_ASCII                         = "ASCII";

    /** Ascii (Allow lower characters.) */
    public static final String  ASCII                               = "Ascii";

    /** ASCII (Do not allow lower characters.) */
    public static final String  UPPER_ASCII_NOT_WHITESPACES         = "ASCII_NOT_WHITESPACES";

    /** Ascii (Allow lower characters.) */
    public static final String  ASCII_NOT_WHITESPACES               = "Ascii_NOT_WHITESPACES";

    /** ASCII_TOKEN (Do not allow lower characters.) */
    public static final String  UPPER_ASCII_TOKEN                   = "ASCII_TOKEN";

    /** Ascii_TOKEN (ORDER_HEADER_ID : Allow lower characters.) */
    public static final String  ASCII_TOKEN                         = "Ascii_TOKEN";

    /** BIGDECIMAL */
    public static final String  BIGDECIMAL                          = "BIGDECIMAL";

    /** BOOLEAN */
    public static final String  BOOLEAN                             = "BOOLEAN";

    /** COMPARISON (ALPHA, =, <, >, !) */
    public static final String  COMPARISON                          = "COMPARISON";

    /** Date(yyyymmdd or yyyy/mm/dd or yyyy-mm-dd) */
    public static final String  DATE_FULL                           = "DATE_FULL";

    /** Date(yyyymm) */
    public static final String  DATE_YYYYMM                         = "DATE_YYYYMM";

    /** E_MAIL */
    public static final String  E_MAIL                              = "E_MAIL";

    /** FILE_NAME */
    public static final String  FILE_NAME                           = "FILE_NAME";

    /** exist in property file */
    public static final String  IN_PROP_FILE                        = "IN_PROP_FILE";

    /** LONG */
    public static final String  LONG                                = "NUMERIC";

    /** STRING */
    public static final String  STRING                              = "STRING";

    /** TIMESTAMP */
    public static final String  TIMESTAMP                           = "TIMESTAMP";

    /** URL */
    public static final String  URL                                 = "URL";

    /** IP_ADDRESS */
    public static final String  IP_ADDRESS                          = "IP_ADDRESS";

    /** For ALPHA_NUM_HYPHEN . */
    public static final String  ALPHA_NUM_HYPHEN                    = "ALPHA_NUM_HYPHEN";

    /** For UPPER_ASCII_TOKEN_KANA. */
    public static final String  UPPER_ASCII_TOKEN_KANA              = "ASCII_TOKEN_KANA";

    /** For DATE_MMYYYY. */
    public static final String  DATE_MMYYYY                         = "DATE_MMYYYY";

    /** For STRING_BR. */
    public static final String  STRING_BR                           = "STRING_BR";

    public static final String  STRING_LENB_W31J                    = "STRING_LENB_W31J";

    public static final String  POSITIVE_INTEGER                    = "POSITIVE_INTEGER";

    /** For Charset Definition.**/
    public static final Charset CHARSET_WINDOWS31J                  = Charset.forName("Windows-31J");
    public static final Charset CHARSET_EUC_KR                      = Charset.forName("euc-kr");

}
