package uk.co.sample.ws.base;

public class ApplicationRestException extends AbstractRestException {

    private static final long serialVersionUID = 3263097400504357238L;

    public ApplicationRestException() {
        super();
    }

    public ApplicationRestException(String errorCode) {
        super();
        addErrorCode(errorCode);
    }

    public ApplicationRestException(String errorCode, String detailMessage) {
        super();
        addErrorCode(errorCode, detailMessage);
    }

    public ApplicationRestException(String errorCode, String[] detailMessages) {
        super();
        addErrorCode(errorCode, detailMessages);
    }

    public ApplicationRestException(String errorCode, Throwable t) {
        super(t);
        addErrorCode(errorCode);
    }

    public ApplicationRestException(String errorCode, String detailMessage, Throwable t) {
        super(t);
        addErrorCode(errorCode, detailMessage);
    }

    public ApplicationRestException(String errorCode, String[] detailMessages, Throwable t) {
        super(t);
        addErrorCode(errorCode, detailMessages);
    }

}
