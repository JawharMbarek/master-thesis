package de.m0ep.socc.exceptions;

public class AuthenticationException extends ConnectorException {
    private static final long serialVersionUID = -6953764319606378722L;

    public AuthenticationException() {
    }

    public AuthenticationException(String message) {
	super(message);
    }

    public AuthenticationException(Throwable cause) {
	super(cause);
    }

    public AuthenticationException(String message, Throwable cause) {
	super(message, cause);
    }

    public AuthenticationException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
