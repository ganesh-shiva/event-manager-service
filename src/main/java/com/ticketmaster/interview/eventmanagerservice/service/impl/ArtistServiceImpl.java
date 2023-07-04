package com.ticketmaster.interview.eventmanagerservice.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ticketmaster.interview.eventmanagerservice.client.EventManagerRestClient;
import com.ticketmaster.interview.eventmanagerservice.exception.EventManagerException;
import com.ticketmaster.interview.eventmanagerservice.model.Artist;
import com.ticketmaster.interview.eventmanagerservice.model.ArtistInformation;
import com.ticketmaster.interview.eventmanagerservice.model.Event;
import com.ticketmaster.interview.eventmanagerservice.model.EventInfo;
import com.ticketmaster.interview.eventmanagerservice.model.Venue;
import com.ticketmaster.interview.eventmanagerservice.service.ArtistService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service implementation for handling event-management operations
 *
 * @author Ganesh Shiva
 */
@Slf4j
@Component
public class ArtistServiceImpl implements ArtistService {

    private final EventManagerRestClient eventManagerRestClient;

    @Value("${events.json.endpoint}")
    private String eventsEndpoint;

    @Value("${artists.json.endpoint}")
    private String artistsEndpoint;

    @Value("${venues.json.endpoint}")
    private String venuesEndpoint;

    @Autowired
    public ArtistServiceImpl(EventManagerRestClient eventManagerRestClient) {
        this.eventManagerRestClient = eventManagerRestClient;
    }

    /*
        ToDo: Instead of invoking S3 endpoint on each request for Artist Information, we can prepare the data by invoking call only once during service start-up and keep the data in Cache.
        ToDo: By doing this, we can eliminate the remote S3 REST endpoint call which improves throughput of getArtistInfo API
     */

    @Override
    public Mono<ArtistInformation> getArtistInfo(String artistId) {
        try {
            // fetch all the Artists from remote endpoint
            Flux<Artist> artists = eventManagerRestClient.getArtists(artistsEndpoint);

            /*
                filter-out by given artistId. If found, return the Artist object OR Else throw exception
             */
            Artist artist = artists.collectList().block().stream()
                .filter(a -> a.getId().equals(artistId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Artist Id!!"));

            // fetch all the Events from remote endpoint
            Flux<Event> events = eventManagerRestClient.getEvents(eventsEndpoint);

            //  filter-out the events in which given artist is performing
            List<EventInfo> eventsInArtistPerforming = events.collectList().block().stream()
                .filter(Objects::nonNull)
                .filter(event -> isArtistPresent(event, artist.getId()))
                .map(this::toEventInfo)
                .collect(Collectors.toList());

            // construct artist info with event & venue details
            ArtistInformation artistInformation = ArtistInformation.builder()
                .id(artist.getId())
                .imgSrc(artist.getImgSrc())
                .name(artist.getName())
                .rank(artist.getRank())
                .url(artist.getUrl())
                .events(eventsInArtistPerforming)
                .build();

            return Mono.just(artistInformation);
        } catch (Exception e) {
            log.error("Exception occurred while fetching Details {}", e.getMessage(), e);
            throw new EventManagerException(e.getMessage(), e);
        }
    }

    private EventInfo toEventInfo(final Event event) {
        return EventInfo.builder()
            .id(event.getId())
            .dateStatus(event.getDateStatus())
            .hiddenFromSearch(event.isHiddenFromSearch())
            .startDate(event.getStartDate())
            .timeZone(event.getTimeZone())
            .title(event.getTitle())
            .venue(getVenueInfo(event.getVenue()))
            .build();
    }

    private Venue getVenueInfo(final Venue venue) {
        Venue result = null;
        try {
            Flux<Venue> venues = eventManagerRestClient.getVenues(venuesEndpoint);
            Optional<Venue> venueOptional = venues.collectList().block().stream()
                .filter(Objects::nonNull)
                .filter(v -> v.getId().equals(venue.getId()))
                .findFirst();
            result = venueOptional.orElse(null);
        } catch (Exception e) {
            log.error("Exception occurred while fetching Venues {}", e.getMessage(), e);
        }
        return result;
    }

    private boolean isArtistPresent(Event event, String artistId) {
        return event.getArtists().stream().anyMatch(artist -> artist.getId().equals(artistId));
    }
}
