package com.ticketmaster.interview.eventmanagerservice.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ticketmaster.interview.eventmanagerservice.client.EventManagerRestClient;
import com.ticketmaster.interview.eventmanagerservice.model.Artist;
import com.ticketmaster.interview.eventmanagerservice.model.ArtistInformation;
import com.ticketmaster.interview.eventmanagerservice.model.Event;
import com.ticketmaster.interview.eventmanagerservice.model.EventInfo;
import com.ticketmaster.interview.eventmanagerservice.model.Venue;
import com.ticketmaster.interview.eventmanagerservice.service.ArtistService;

import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for handling event-management operations
 *
 * @author Ganesh Shiva
 */
@Slf4j
@Component
public class ArtistServiceImpl implements ArtistService {

    private final EventManagerRestClient eventManagerRestClient;

    @Autowired
    public ArtistServiceImpl(EventManagerRestClient eventManagerRestClient) {
        this.eventManagerRestClient = eventManagerRestClient;
    }

    @Override
    public ArtistInformation getArtistInfo(String artistId) {
        try {
            // fetch all the Artists from remote endpoint
            Collection<Artist> artists = eventManagerRestClient.getArtists();

            Optional<Artist> artistOptional = artists.stream()
                .filter(a -> a.getId().equals(artistId))
                .findFirst();

            Artist artist = artistOptional.get();

            Collection<Event> events = eventManagerRestClient.getEvents();

            List<EventInfo> eventsInArtistPerforming = events.stream()
                .filter(Objects::nonNull)
                .filter(event -> isArtistPresent(event, artist.getId()))
                .map(this::toEventInfo)
                .collect(Collectors.toList());

            ArtistInformation artistInformation = ArtistInformation.builder()
                .id(artist.getId())
                .imgSrc(artist.getImgSrc())
                .name(artist.getName())
                .rank(artist.getRank())
                .url(artist.getUrl())
                .events(eventsInArtistPerforming)
                .build();

            return artistInformation;
        } catch (Exception e) {
            log.error("Exception occurred while fetching Details {}", e.getMessage(), e);
        }
        return null;
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
            Collection<Venue> venues = eventManagerRestClient.getVenues();
            Optional<Venue> venueOptional = venues.stream().filter(Objects::nonNull).filter(v -> v.getId().equals(venue.getId())).findFirst();
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
