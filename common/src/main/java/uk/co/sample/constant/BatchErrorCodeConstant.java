package uk.co.sample.constant;

public class BatchErrorCodeConstant {
    private BatchErrorCodeConstant() {
    }

    /** batch id not found */
    public static final String ERROR_BATCH_ID_NOT_FOUND = "E90001";
    /** not found batch common properties */
    public static final String ERROR_COM_PROP_NOT_FOUND = "E90002";
    /** not found batch class */
    public static final String ERROR_CLASS_NOT_FOUND    = "E90003";

    /* ------------------------------------------------------------*/
    /* -------------------- mail send error -----------------------*/
    /* ------------------------------------------------------------*/
    /** not found common body */
    public static final String ERROR_BODY_NOT_FOUND     = "E90004";
    /** common body read error */
    public static final String ERROR_BODY_READ          = "E90005";
    /** send mail error */
    public static final String ERROR_SEND_MAIL          = "E90006";

    /* ------------------------------------------------------------*/
    /* -------------------- scheduler error -----------------------*/
    /* ------------------------------------------------------------*/
    /** execute batch error */
    public static final String ERROR_SYSTEM_BATCH       = "E90007";
    /** execute batch not found */
    public static final String ERROR_BATCH_NOT_FOUND    = "E90008";

}
