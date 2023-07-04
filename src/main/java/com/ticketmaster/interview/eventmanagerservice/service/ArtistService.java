package com.ticketmaster.interview.eventmanagerservice.service;

import com.ticketmaster.interview.eventmanagerservice.model.ArtistInformation;

import reactor.core.publisher.Mono;

/**
 * Contract defining event-management operations to handle
 *
 * @author Ganesh Shiva
 */
public interface ArtistService {

    Mono<ArtistInformation> getArtistInfo(String artistId);
}
