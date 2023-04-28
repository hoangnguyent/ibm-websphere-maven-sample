package uk.co.sample.security;

import java.io.Serializable;

public class Operator implements Serializable {

    private static final long serialVersionUID = -8823720607506137648L;
    private String            tracingId;
    private String            timeZone;

    // ***** constructor *****
    // ***** public method *****
    // ***** getter and setter *****
    public String getTracingId() {
        return tracingId;
    }

    public void setTracingId(String tracingId) {
        this.tracingId = tracingId;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getTimeZone() {
        return timeZone;
    }

    // ***** injection field *****
    // ***** protected method *****
    // ***** private method *****

}
