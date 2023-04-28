package uk.co.sample.ws.base;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class ErrorDetails implements Serializable {

    private static final long serialVersionUID = 4795643209000200048L;

    private String            code;
    private String            msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ErrorDetails() {
    }

    public ErrorDetails(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }
}
