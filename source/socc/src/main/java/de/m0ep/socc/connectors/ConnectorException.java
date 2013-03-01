package de.m0ep.socc.connectors;

public class ConnectorException extends RuntimeException {
    private static final long serialVersionUID = -4779338022009814217L;

    public ConnectorException() {
    }

    public ConnectorException(String message) {
	super(message);
    }

    public ConnectorException(Throwable cause) {
	super(cause);
    }

    public ConnectorException(String message, Throwable cause) {
	super(message, cause);
    }

    public ConnectorException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
