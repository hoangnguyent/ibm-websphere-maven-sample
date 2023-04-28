package uk.co.sample.exception;

public class ApplicationLogicException extends AbstractApplicationRuntimeException {

    private static final long serialVersionUID = -1570628205864822040L;

    //***** injection field *****
    //***** constructor *****

    public ApplicationLogicException(String code) {
        super(code);
    }

    public ApplicationLogicException(String code, Throwable cause) {
        super(code, cause);
    }

    public ApplicationLogicException(String code, String[] params) {
        super(code, params);
    }

    public ApplicationLogicException(String code, String[] params, Throwable cause) {
        super(code, params, cause);
    }

    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** getter and setter *****

}
