package com.ticketmaster.interview.eventmanagerservice.model;

import com.ticketmaster.interview.eventmanagerservice.exception.InvalidResultStateException;

/**
 *  An enumeration representing the different available statuses for a REST call
 *
 * @author Ganesh Shiva
 */
public enum ResultState {
    SUCCESS("SUCCESS"),

    ERROR("ERROR");

    private final String description;

    ResultState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return getDescription();
    }

    public static ResultState fromDescription(String description) {
        for (ResultState status : ResultState.values()) {
            if (status.description.equals(description)) {
                return status;
            }
        }
        throw new InvalidResultStateException(
                        String.format("Cannot match ResultState description for value %s", description));
    }
}