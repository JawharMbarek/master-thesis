package de.m0ep.canvas.exceptions;

public class NetworkException extends CanvasException {
    private static final long serialVersionUID = -1438999210049126702L;

    public NetworkException() {
    }

    public NetworkException(String message) {
	super(message);
    }

    public NetworkException(Throwable cause) {
	super(cause);
    }

    public NetworkException(String message, Throwable cause) {
	super(message, cause);
    }

    public NetworkException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
