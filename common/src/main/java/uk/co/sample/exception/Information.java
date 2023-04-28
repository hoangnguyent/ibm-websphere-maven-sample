package uk.co.sample.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Information implements Serializable {

    private static final long            serialVersionUID = 360875216025851549L;
    private final String                 key;
    private final transient List<Object> parameters       = new ArrayList<>();

    //***** injection field *****
    //***** constructor *****

    public Information(String key) {
        this.key = key;
    }

    public Information(String key, Object... parameters) {
        this.key = key;
        this.parameters.addAll(Arrays.asList(parameters));
    }

    //***** public method *****

    public void addParameters(Object... parameters) {
        this.parameters.addAll(Arrays.asList(parameters));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(key);
        if (!parameters.isEmpty()) {
            builder.append(" [").append(StringUtils.join(parameters, ", ")).append("]");
        }
        return builder.toString();
    }

    //***** protected method *****
    //***** private method *****
    //***** getter and setter *****

    public String getKey() {
        return key;
    }

    public List<Object> getParameters() {
        return parameters;
    }

}
