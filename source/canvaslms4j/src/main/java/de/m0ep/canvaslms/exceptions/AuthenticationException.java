package de.m0ep.canvaslms.exceptions;

public class AuthenticationException extends CanvasLMSException {
    private static final long serialVersionUID = 6627465563801750355L;

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
