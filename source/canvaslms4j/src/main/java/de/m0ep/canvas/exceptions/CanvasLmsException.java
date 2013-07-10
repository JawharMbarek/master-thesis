package de.m0ep.canvas.exceptions;

public class CanvasLmsException extends Exception {
    private static final long serialVersionUID = 4645632678406315450L;

    public CanvasLmsException() {
    }

    public CanvasLmsException(String message) {
	super(message);
    }

    public CanvasLmsException(Throwable cause) {
	super(cause);
    }

    public CanvasLmsException(String message, Throwable cause) {
	super(message, cause);
    }

    public CanvasLmsException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
