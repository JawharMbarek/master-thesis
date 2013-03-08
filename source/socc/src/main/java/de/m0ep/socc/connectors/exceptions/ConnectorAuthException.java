package de.m0ep.socc.connectors.exceptions;

public class ConnectorAuthException extends ConnectorException {
    private static final long serialVersionUID = -6953764319606378722L;

    public ConnectorAuthException() {
    }

    public ConnectorAuthException(String message) {
	super(message);
    }

    public ConnectorAuthException(Throwable cause) {
	super(cause);
    }

    public ConnectorAuthException(String message, Throwable cause) {
	super(message, cause);
    }

    public ConnectorAuthException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
