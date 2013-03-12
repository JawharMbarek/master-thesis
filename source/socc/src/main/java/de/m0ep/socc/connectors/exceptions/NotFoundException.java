package de.m0ep.socc.connectors.exceptions;

public class NotFoundException extends ConnectorException {
    private static final long serialVersionUID = -2291761551548259500L;

    public NotFoundException() {
    }

    public NotFoundException(String message) {
	super(message);
    }

    public NotFoundException(Throwable cause) {
	super(cause);
    }

    public NotFoundException(String message, Throwable cause) {
	super(message, cause);
    }

    public NotFoundException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
