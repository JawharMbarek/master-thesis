package de.m0ep.canvas.exceptions;

public class AuthorizationException extends CanvasLmsException {
    private static final long serialVersionUID = 6627465563801750355L;

    public AuthorizationException() {
    }

    public AuthorizationException(String message) {
	super(message);
    }

    public AuthorizationException(Throwable cause) {
	super(cause);
    }

    public AuthorizationException(String message, Throwable cause) {
	super(message, cause);
    }

    public AuthorizationException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
