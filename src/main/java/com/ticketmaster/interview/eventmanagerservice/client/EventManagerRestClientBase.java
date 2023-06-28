package com.ticketmaster.interview.eventmanagerservice.client;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Super class defining the common headers to be used in the inherited client
 *
 * @author Ganesh Shiva
 */
@SuperBuilder
@ToString
@Getter
@Setter
public class EventManagerRestClientBase {
    private static final Header[] headers = {
        new BasicHeader(HttpHeaders.ACCEPT, APPLICATION_JSON.getMimeType()),
        new BasicHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON.getMimeType())};

    protected Header[] getHeaders() {
        return headers;
    }
    protected static final String ENCODING = "UTF-8";
}
