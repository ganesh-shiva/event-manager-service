package com.ticketmaster.interview.eventmanagerservice.service;

import com.ticketmaster.interview.eventmanagerservice.model.ArtistInformation;

/**
 * Contract defining event-management operations to handle
 *
 * @author Ganesh Shiva
 */
public interface ArtistService {

    ArtistInformation getArtistInfo(String artistId);
}
