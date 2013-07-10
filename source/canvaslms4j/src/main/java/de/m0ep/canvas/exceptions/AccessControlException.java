package de.m0ep.canvas.exceptions;

public class AccessControlException extends CanvasLmsException {
    private static final long serialVersionUID = 3003790748613812295L;

    public AccessControlException() {
    }

    public AccessControlException(String message) {
	super(message);
    }

    public AccessControlException(Throwable cause) {
	super(cause);
    }

    public AccessControlException(String message, Throwable cause) {
	super(message, cause);
    }

    public AccessControlException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
