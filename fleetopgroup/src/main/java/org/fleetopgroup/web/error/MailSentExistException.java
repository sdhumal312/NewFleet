package org.fleetopgroup.web.error;

public final class MailSentExistException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public MailSentExistException() {
        super();
    }

    public MailSentExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MailSentExistException(final String message) {
        super(message);
    }

    public MailSentExistException(final Throwable cause) {
        super(cause);
    }

}
