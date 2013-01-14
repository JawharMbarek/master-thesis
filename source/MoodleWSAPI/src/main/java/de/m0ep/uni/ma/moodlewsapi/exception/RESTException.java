package de.m0ep.uni.ma.moodlewsapi.exception;

public class RESTException extends Exception {
    private static final long serialVersionUID = 5561932268277658985L;

    public RESTException() {
    }

    public RESTException( String message ) {
        super( message );
    }

    public RESTException( Throwable cause ) {
        super( cause );
    }

    public RESTException( String message, Throwable cause ) {
        super( message, cause );
    }

    public RESTException( String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }

}
