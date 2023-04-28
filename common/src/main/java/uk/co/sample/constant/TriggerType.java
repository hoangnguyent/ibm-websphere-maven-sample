package uk.co.sample.constant;

import uk.co.sample.exception.ApplicationCheckException;

public enum TriggerType {
    TIME("T"),
    LOCATION("L"),
    IMMEDIATE("I");

    private String value;

    TriggerType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TriggerType valueOfIgnoreCase(String value) {

        for (TriggerType status : TriggerType.values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }

        throw new ApplicationCheckException(SystemErrorCodeConstant.ERROR_VIOLATION_CHECK_CONSTRAINT);
    }
}
