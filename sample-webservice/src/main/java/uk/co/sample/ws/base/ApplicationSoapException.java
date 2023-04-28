package uk.co.sample.ws.base;

public class ApplicationSoapException extends AbstractSoapException {

    private static final long serialVersionUID = 2662192854087432774L;

    /**
     * Constructor.
     * <p>
     * Construct blank AbstractWsException.
     * <p>
     */
    public ApplicationSoapException() {
        super();
    }

    /**
     * Constructor with Throwable.
     * @param t Throwable
     */
    public ApplicationSoapException(Throwable t) {
        super(t);
    }

    /**
     * Constructor with errorCode.
     * @param errorCode Strring
     */
    public ApplicationSoapException(String errorCode) {
        super();
        addErrorCode(errorCode);
    }

    /**
     * Constructor with errorCode.
     * @param errorCode String
     * @param detailMessage String
     */
    public ApplicationSoapException(String errorCode, String detailMessage) {
        super();
        addErrorCode(errorCode, detailMessage);
    }

    /**
     * Constructor with errorCode.
     * @param errorCode String
     * @param detailMessages String[]
     */
    public ApplicationSoapException(String errorCode, String[] detailMessages) {
        super();
        addErrorCode(errorCode, detailMessages);
    }

    /**
     * Constructor with errorCode and Throwable.
     * @param errorCode String
     * @param t Throwable
     */
    public ApplicationSoapException(String errorCode, Throwable t) {
        super(t);
        addErrorCode(errorCode);
    }

    /**
     * Constructor with errorCode and Throwable.
     * @param errorCode String
     * @param detailMessage String
     * @param t Throwable
     */
    public ApplicationSoapException(String errorCode, String detailMessage, Throwable t) {
        super(t);
        addErrorCode(errorCode, detailMessage);
    }

    /**
     * Constructor with errorCode and Throwable.
     * @param errorCode String
     * @param detailMessages String[]
     * @param t Throwable
     */
    public ApplicationSoapException(String errorCode, String[] detailMessages, Throwable t) {
        super(t);
        addErrorCode(errorCode, detailMessages);
    }

}
