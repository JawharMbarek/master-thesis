package de.m0ep.uni.ma;

public class MoodleWSException extends RuntimeException {

    public MoodleWSException() {
    }

    public MoodleWSException(String message) {
	super(message);
    }

    public MoodleWSException(Throwable cause) {
	super(cause);
    }

    public MoodleWSException(String message, Throwable cause) {
	super(message, cause);
    }

    public MoodleWSException(String message, Throwable cause,
	    boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
