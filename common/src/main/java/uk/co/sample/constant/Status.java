package uk.co.sample.constant;

import uk.co.sample.exception.ApplicationCheckException;

public enum Status {
    PENDING,
    INPROGRESS,
    SUCCEEDED,
    RETRY,
    FAILED,
    DELETED;

    public static Status valueOfIgnoreCase(String str) {

        for (Status status : Status.values()) {
            if (status.name().equalsIgnoreCase(str)) {
                return status;
            }
        }

        throw new ApplicationCheckException(SystemErrorCodeConstant.ERROR_VIOLATION_CHECK_CONSTRAINT);
    }
}
