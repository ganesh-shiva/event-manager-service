package com.ticketmaster.interview.eventmanagerservice.client;

import java.util.Collection;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.web.util.UriUtils;

import com.ticketmaster.interview.eventmanagerservice.exception.PublicAPIException;
import com.ticketmaster.interview.eventmanagerservice.model.Artist;
import com.ticketmaster.interview.eventmanagerservice.model.Event;
import com.ticketmaster.interview.eventmanagerservice.model.Venue;
import com.ticketmaster.interview.eventmanagerservice.util.Serde;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * Client used by event-manager-service to invoke publicly exposed endpoints via REST
 *
 * @author Ganesh Shiva
 */
@ToString
@Getter
@Setter
@SuperBuilder
@Slf4j
public class EventManagerRestClient extends EventManagerRestClientBase {

    private static final String EVENTS_JSON_ENDPOINT = UriUtils.encodePath("https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/events.json", ENCODING);
    private static final String ARTISTS_JSON_ENDPOINT = UriUtils.encodePath("https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/artists.json", ENCODING);
    private static final String VENUES_JSON_ENDPOINT = UriUtils.encodePath("https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/venues.json", ENCODING);

    private CloseableHttpClient httpClient;
    private Serde serde;

    /**
     * Invokes remote public REST endpoint and gets List of Artists
     *
     * @return List of Artists
     * @throws PublicAPIException in case of any exceptions
     */
    public Collection<Artist> getArtists() throws Exception {
        log.info("Invoking publicly exposed endpoint for artists.json");
        var get = new HttpGet(ARTISTS_JSON_ENDPOINT);
        get.setHeaders(getHeaders());
        get.setURI(new URIBuilder(get.getURI()).build());
        try (var response= httpClient.execute(get)) {
            var statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                var payload = response.getEntity().getContent().readAllBytes();
                var artists =  (Collection<Artist>) serde.deserialiseToCollection(payload, Artist.class);
                log.debug("artists.json API invoked successfully. Event details returned is {}", artists);
                return artists;
            } else {
                var message = String.format("GET request for getArtists API at: %s returned unexpected response status: %d",
                    ARTISTS_JSON_ENDPOINT, statusCode);
                log.error(message);
                throw new PublicAPIException(message);
            }
        } catch (Exception e) {
            var errorMessage = "Error occurred while invoking getArtists API";
            log.error(errorMessage, e);
            throw new PublicAPIException(errorMessage, e);
        }
    }

    /**
     * Invokes remote public REST endpoint and gets List of Venues
     *
     * @return List of Venues
     * @throws PublicAPIException in case of any exceptions
     */
    public Collection<Venue> getVenues() throws Exception {
        log.info("Invoking publicly exposed endpoint for venues.json");
        var get = new HttpGet(VENUES_JSON_ENDPOINT);
        get.setHeaders(getHeaders());
        get.setURI(new URIBuilder(get.getURI()).build());
        try (var response= httpClient.execute(get)) {
            var statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                var payload = response.getEntity().getContent().readAllBytes();
                var venues =  (Collection<Venue>) serde.deserialiseToCollection(payload, Venue.class);
                log.debug("venues.json API invoked successfully. Event details returned is {}", venues);
                return venues;
            } else {
                var message = String.format("GET request for getVenues API at: %s returned unexpected response status: %d",
                    VENUES_JSON_ENDPOINT, statusCode);
                log.error(message);
                throw new PublicAPIException(message);
            }
        } catch (Exception e) {
            var errorMessage = "Error occurred while invoking getVenues API";
            log.error(errorMessage, e);
            throw new PublicAPIException(errorMessage, e);
        }
    }

    /**
     * Invokes remote public REST endpoint and gets List of events
     *
     * @return List of events
     * @throws PublicAPIException in case of any exceptions
     */
    public Collection<Event> getEvents() throws Exception {
        log.info("Invoking publicly exposed endpoint for events.json");
        var get = new HttpGet(EVENTS_JSON_ENDPOINT);
        get.setHeaders(getHeaders());
        get.setURI(new URIBuilder(get.getURI()).build());
        try (var response= httpClient.execute(get)) {
            var statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                var payload = response.getEntity().getContent().readAllBytes();
                var events =  (Collection<Event>) serde.deserialiseToCollection(payload, Event.class);
                log.debug("events.json API invoked successfully. Event details returned is {}", events);
                return events;
            } else {
                var message = String.format("GET request for getEvents API at: %s returned unexpected response status: %d",
                    EVENTS_JSON_ENDPOINT, statusCode);
                log.error(message);
                throw new PublicAPIException(message);
            }
        } catch (Exception e) {
            var errorMessage = "Error occurred while invoking getEvents API";
            log.error(errorMessage, e);
            throw new PublicAPIException(errorMessage, e);
        }
    }
}
