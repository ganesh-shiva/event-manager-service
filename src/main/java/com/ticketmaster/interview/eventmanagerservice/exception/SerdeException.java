package com.ticketmaster.interview.eventmanagerservice.exception;

/**
 * @author Ganesh Shiva
 */
public class SerdeException extends RuntimeException {

    public SerdeException() {
    }

    public SerdeException(String message) {
        super(message);
    }

    public SerdeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerdeException(Throwable cause) {
        super(cause);
    }
}
