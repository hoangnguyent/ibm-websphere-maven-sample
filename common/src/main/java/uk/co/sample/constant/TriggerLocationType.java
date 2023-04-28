package uk.co.sample.constant;

import uk.co.sample.exception.ApplicationCheckException;

public enum TriggerLocationType {
    TIPLOC("T");

    private String value;

    TriggerLocationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TriggerLocationType valueOfIgnoreCase(String value) {

        for (TriggerLocationType status : TriggerLocationType.values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }

        throw new ApplicationCheckException(SystemErrorCodeConstant.ERROR_VIOLATION_CHECK_CONSTRAINT);
    }
}
