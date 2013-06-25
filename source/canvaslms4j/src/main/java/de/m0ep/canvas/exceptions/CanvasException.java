package de.m0ep.canvas.exceptions;

public class CanvasException extends Exception {
    private static final long serialVersionUID = 4645632678406315450L;

    public CanvasException() {
    }

    public CanvasException(String message) {
	super(message);
    }

    public CanvasException(Throwable cause) {
	super(cause);
    }

    public CanvasException(String message, Throwable cause) {
	super(message, cause);
    }

    public CanvasException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
