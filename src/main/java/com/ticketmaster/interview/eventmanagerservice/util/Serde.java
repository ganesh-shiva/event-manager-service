package com.ticketmaster.interview.eventmanagerservice.util;

import java.io.IOException;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ticketmaster.interview.eventmanagerservice.exception.SerdeException;

/**
 * Utility class for serialising/de-serialising objects
 *
 * @author Ganesh Shiva
 */
@Component
public class Serde {

    private final ObjectMapper OBJECT_MAPPER ;

    public Serde() {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    public String serialise(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (final JsonProcessingException e) {
            throw new SerdeException(
                String.format("Error serializing: %s", obj.getClass()), e);
        }
    }

    public <T> T deserialiseToCollection(byte[] json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json,  OBJECT_MAPPER.getTypeFactory().constructCollectionType(Collection.class, clazz));
        } catch (final IOException e) {
            String payload = (ArrayUtils.isEmpty(json)) ? "no payload detected!" : new String(json);
            throw new SerdeException(String.format("Error deserializing payload: %s", payload), e);
        }
    }
}
