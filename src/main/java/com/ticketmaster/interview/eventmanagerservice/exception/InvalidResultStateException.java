package com.ticketmaster.interview.eventmanagerservice.exception;

/**
 * @author Ganesh Shiva
 */
public class InvalidResultStateException extends RuntimeException {

    public InvalidResultStateException() {
    }

    public InvalidResultStateException(String message) {
        super(message);
    }

    public InvalidResultStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidResultStateException(Throwable cause) {
        super(cause);
    }
}
