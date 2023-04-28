package uk.co.sample.exception;

public class ApplicationDataException extends AbstractApplicationRuntimeException {

    private static final long serialVersionUID = 7518243121281533867L;

    //***** injection field *****
    //***** constructor *****

    public ApplicationDataException(String code) {
        super(code);
    }

    public ApplicationDataException(String code, Throwable cause) {
        super(code, cause);
    }

    public ApplicationDataException(String code, String[] params) {
        super(code, params);
    }

    public ApplicationDataException(String code, String[] params, Throwable cause) {
        super(code, params, cause);
    }

    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** getter and setter *****

}
