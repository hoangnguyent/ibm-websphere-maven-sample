package uk.co.sample.ws.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class RestErrorData implements Serializable {

    private static final long  serialVersionUID = -8861695433598660786L;

    private List<String>       codes            = new ArrayList<>();
    private List<ErrorDetails> details          = new ArrayList<>();
    private String             className;

    // ***** injection field *****
    // ***** constructor *****
    // ***** public method *****
    // ***** protected method *****
    // ***** private method *****
    // ***** getter and setter *****
    public List<String> getCodes() {
        return codes;
    }

    public List<ErrorDetails> getDetails() {
        return details;
    }

    public void setDetails(List<ErrorDetails> details) {
        this.details = details;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

}
