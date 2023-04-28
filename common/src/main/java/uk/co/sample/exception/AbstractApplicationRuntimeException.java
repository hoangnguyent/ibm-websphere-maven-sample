package uk.co.sample.exception;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import uk.co.sample.constant.CommonConstant;

public abstract class AbstractApplicationRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -587775883040874607L;
    private final String      code;
    private final String[]    params;

    //***** injection field *****
    //***** constructor *****

    public AbstractApplicationRuntimeException() {
        super();
        this.code = null;
        this.params = null;
    }

    public AbstractApplicationRuntimeException(Throwable t) {
        super(t);
        this.code = null;
        this.params = null;
    }

    public AbstractApplicationRuntimeException(String code) {
        super(code);
        this.code = code;
        this.params = null;
    }

    public AbstractApplicationRuntimeException(String code, Throwable cause) {
        super(code, cause);
        this.code = code;
        this.params = null;
    }

    public AbstractApplicationRuntimeException(String code, String[] params) {
        super(code + "[" + StringUtils.join(params, CommonConstant.COMMA) + "]");
        this.code = code;
        this.params = params;
    }

    public AbstractApplicationRuntimeException(String code, String[] params, Throwable cause) {
        super(code + "[" + StringUtils.join(Arrays.asList(params), CommonConstant.COMMA) + "]", cause);
        this.code = code;
        this.params = params;
    }

    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** getter and setter *****

    public String getCode() {
        return this.code;
    }

    public String[] getParams() {
        return params;
    }

}
