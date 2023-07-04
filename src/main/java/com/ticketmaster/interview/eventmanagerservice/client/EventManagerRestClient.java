package com.ticketmaster.interview.eventmanagerservice.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriUtils;

import com.ticketmaster.interview.eventmanagerservice.exception.PublicAPIException;
import com.ticketmaster.interview.eventmanagerservice.model.Artist;
import com.ticketmaster.interview.eventmanagerservice.model.Event;
import com.ticketmaster.interview.eventmanagerservice.model.Venue;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
public class EventManagerRestClient {

    private static final String ENCODING = "UTF-8";

    private WebClient webClient;

    /**
     * Invokes remote public REST endpoint and gets List of Artists
     *
     * @return List of Artists
     */
    public Flux<Artist> getArtists(String artistsEndpoint) {
        log.info("Invoking publicly exposed endpoint for artists.json");
        Flux<Artist> artists;
        var endpoint = UriUtils.encodePath(artistsEndpoint, ENCODING);
        try {
            artists = webClient.get()
                .uri(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(org.springframework.http.HttpStatusCode::is5xxServerError, clientResponse -> {
                    Mono<String> exMessage = clientResponse.bodyToMono(String.class);
                    return exMessage.flatMap(msg -> {
                        var message = String.format("GET request for getArtists API at: %s returned unexpected response status: %d",
                            endpoint, clientResponse.statusCode().value());
                        log.error(message, msg);
                        return Mono.error(new PublicAPIException(msg));
                    });
                })
                .bodyToFlux(Artist.class);
        } catch (Exception e) {
            var errorMessage = "Error occurred while invoking getArtists API";
            log.error(errorMessage, e);
            throw new PublicAPIException(errorMessage, e);
        }
        return artists;
    }

    /**
     * Invokes remote public REST endpoint and gets List of Venues
     *
     * @return List of Venues
     */
    public Flux<Venue> getVenues(String venuesEndpoint) {
        log.info("Invoking publicly exposed endpoint for venues.json");
        Flux<Venue> venues;
        var endpoint = UriUtils.encodePath(venuesEndpoint, ENCODING);
        try {
            venues = webClient.get()
                .uri(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(org.springframework.http.HttpStatusCode::is5xxServerError, clientResponse -> {
                    Mono<String> exMessage = clientResponse.bodyToMono(String.class);
                    return exMessage.flatMap(msg -> {
                        var message = String.format("GET request for getVenues API at: %s returned unexpected response status: %d",
                            endpoint, clientResponse.statusCode().value());
                        log.error(message, msg);
                        return Mono.error(new PublicAPIException(msg));
                    });
                })
                .bodyToFlux(Venue.class);
        } catch (Exception e) {
            var errorMessage = "Error occurred while invoking getVenues API";
            log.error(errorMessage, e);
            throw new PublicAPIException(errorMessage, e);
        }
        return venues;
    }

    /**
     * Invokes remote public REST endpoint and gets List of events
     *
     * @return List of events
     */
    public Flux<Event> getEvents(String eventsEndpoint) {
        log.info("Invoking publicly exposed endpoint for events.json");
        Flux<Event> events;
        var endpoint = UriUtils.encodePath(eventsEndpoint, ENCODING);
        try {
            events = webClient.get()
                .uri(endpoint)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(org.springframework.http.HttpStatusCode::is5xxServerError, clientResponse -> {
                    Mono<String> exMessage = clientResponse.bodyToMono(String.class);
                    return exMessage.flatMap(msg -> {
                        var message = String.format("GET request for getEvents API at: %s returned unexpected response status: %d",
                            endpoint, clientResponse.statusCode().value());
                        log.error(message, msg);
                        return Mono.error(new PublicAPIException(msg));
                    });
                })
                .bodyToFlux(Event.class);
        } catch (Exception e) {
            var errorMessage = "Error occurred while invoking getEvents API";
            log.error(errorMessage, e);
            throw new PublicAPIException(errorMessage, e);
        }
        return events;
    }
}
