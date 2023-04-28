package uk.co.sample.exception;

public class ApplicationSystemException extends AbstractApplicationRuntimeException {

    private static final long serialVersionUID = -7213006798323253074L;

    //***** injection field *****
    //***** constructor *****

    public ApplicationSystemException(String code) {
        super(code);
    }

    public ApplicationSystemException(String code, Throwable cause) {
        super(code, cause);
    }

    public ApplicationSystemException(String code, String[] params) {
        super(code, params);
    }

    public ApplicationSystemException(String code, String[] params, Throwable cause) {
        super(code, params, cause);
    }

    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** getter and setter *****

}
