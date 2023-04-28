package uk.co.sample.util;

import java.io.Serializable;

public abstract class AbstractValidateBean implements Serializable {

    private static final long serialVersionUID = 4537930667920803416L;
    private final String      type;

    //***** constructor *****

    public AbstractValidateBean(String type) {
        this.type = type;
    }

    //***** public method *****
    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    //***** getter and setter *****

    public String getType() {
        return type;
    }

}
