package uk.co.sample.ws.base;

import java.io.Serializable;

public class SoapBaseRequestDTO implements Serializable, IfSoapBaseRequestDTO {

    private static final long serialVersionUID = 6777467450331717084L;

    //***** constructor *****
    public SoapBaseRequestDTO() {
        // Do nothing
    }

    //***** public method *****
    //***** protected method *****
    @Override
    public ApplicationSoapException validate(ApplicationSoapException ex) {
        return ex;
    }

    //***** private method *****
    //***** call back method *****
    //***** getter and setter *****
}
