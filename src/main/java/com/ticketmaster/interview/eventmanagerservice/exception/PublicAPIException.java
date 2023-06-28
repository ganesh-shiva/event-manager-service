package com.ticketmaster.interview.eventmanagerservice.exception;

/**
 * @author Ganesh Shiva
 */
public class PublicAPIException extends RuntimeException {

    public PublicAPIException() {
    }

    public PublicAPIException(String message) {
        super(message);
    }

    public PublicAPIException(String message, Throwable cause) {
        super(message, cause);
    }

    public PublicAPIException(Throwable cause) {
        super(cause);
    }
}
