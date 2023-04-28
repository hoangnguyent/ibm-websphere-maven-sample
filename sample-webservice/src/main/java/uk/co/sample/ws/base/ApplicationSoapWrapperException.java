package uk.co.sample.ws.base;

public class ApplicationSoapWrapperException extends Exception {

    private static final long serialVersionUID = -47496287285177841L;

    private String            errorMessage;
    private String            exceptionClassName;
    private String            errorDataMessage;

    //***** constructor *****
    public ApplicationSoapWrapperException() {
    }

    public ApplicationSoapWrapperException(String message) {
        super(message);
    }

    /**
     * Constructor
     * @param errorMessage
     * @param className
     * @param errorDataMessage
     */
    public ApplicationSoapWrapperException(String errorMessage, String className, String errorDataMessage) {
        this.errorMessage = errorMessage;
        this.exceptionClassName = className;
        this.errorDataMessage = errorDataMessage;
    }

    //***** public method *****
    @Override
    public String toString() {
        return "ApplicationWSWrapperException [errorMessage=" + errorMessage + ", exceptionClassName=" + exceptionClassName + ", errorDataMessage=" + errorDataMessage + "]";
    }

    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    //***** getter and setter *****
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getExceptionClassName() {
        return exceptionClassName;
    }

    public void setExceptionClassName(String exceptionClassName) {
        this.exceptionClassName = exceptionClassName;
    }

    public String getErrorDataMessage() {
        return errorDataMessage;
    }

    public void setErrorDataMessage(String errorDataMessage) {
        this.errorDataMessage = errorDataMessage;
    }

}
