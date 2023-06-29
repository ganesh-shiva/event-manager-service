package com.ticketmaster.interview.eventmanagerservice.exception;

/**
 * @author Ganesh Shiva
 */
public class EventManagerException extends RuntimeException {

    public EventManagerException(String message) {
        super(message);
    }

    public EventManagerException(Throwable throwable) {
        super(throwable);
    }

    public EventManagerException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public EventManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}