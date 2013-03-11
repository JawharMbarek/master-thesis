package de.m0ep.socc.connectors.exceptions;

public class AuthException extends ConnectorException {
    private static final long serialVersionUID = -6953764319606378722L;

    public AuthException() {
    }

    public AuthException(String message) {
	super(message);
    }

    public AuthException(Throwable cause) {
	super(cause);
    }

    public AuthException(String message, Throwable cause) {
	super(message, cause);
    }

    public AuthException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
