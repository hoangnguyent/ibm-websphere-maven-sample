package uk.co.sample.constant;

import uk.co.sample.exception.ApplicationCheckException;

public enum DeleteStatus {
    ACCEPTED("A"),
    DELETED("D");

    private String value;

    DeleteStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DeleteStatus valueOfIgnoreCase(String value) {

        for (DeleteStatus status : DeleteStatus.values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }

        throw new ApplicationCheckException(SystemErrorCodeConstant.ERROR_VIOLATION_CHECK_CONSTRAINT);
    }
}
