package org.fleetopgroup.web.error;

public final class EmployeeExistException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public EmployeeExistException() {
        super();
    }

    public EmployeeExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EmployeeExistException(final String message) {
        super(message);
    }

    public EmployeeExistException(final Throwable cause) {
        super(cause);
    }

}
