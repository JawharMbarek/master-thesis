package de.m0ep.canvaslms.exceptions;

public class CanvasLMSException extends Exception {
    private static final long serialVersionUID = 4645632678406315450L;

    public CanvasLMSException() {
    }

    public CanvasLMSException(String message) {
	super(message);
    }

    public CanvasLMSException(Throwable cause) {
	super(cause);
    }

    public CanvasLMSException(String message, Throwable cause) {
	super(message, cause);
    }

    public CanvasLMSException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
