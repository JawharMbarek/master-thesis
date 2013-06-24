package de.m0ep.canvaslms.exceptions;

public class AccessControlException extends CanvasLMSException {
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
