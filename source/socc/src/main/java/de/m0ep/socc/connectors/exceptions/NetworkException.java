package de.m0ep.socc.connectors.exceptions;

public class NetworkException extends ConnectorException {

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
