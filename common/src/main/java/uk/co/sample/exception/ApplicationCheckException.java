package uk.co.sample.exception;

public class ApplicationCheckException extends AbstractApplicationRuntimeException {

    private static final long serialVersionUID = 794850087714931650L;

    //***** injection field *****
    //***** constructor *****

    public ApplicationCheckException(String code) {
        super(code);
    }

    public ApplicationCheckException(String code, Throwable cause) {
        super(code, cause);
    }

    public ApplicationCheckException(String code, String[] params) {
        super(code, params);
    }

    public ApplicationCheckException(String code, String[] params, Throwable cause) {
        super(code, params, cause);
    }

    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** getter and setter *****

}
