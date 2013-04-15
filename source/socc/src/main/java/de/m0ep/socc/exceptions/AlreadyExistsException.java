package de.m0ep.socc.exceptions;

public class AlreadyExistsException extends ConnectorException {
    private static final long serialVersionUID = 3092698722173485316L;

    public AlreadyExistsException() {
    }

    public AlreadyExistsException(String message) {
	super(message);
    }

    public AlreadyExistsException(Throwable cause) {
	super(cause);
    }

    public AlreadyExistsException(String message, Throwable cause) {
	super(message, cause);
    }

    public AlreadyExistsException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
