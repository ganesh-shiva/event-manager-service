package com.ticketmaster.interview.eventmanagerservice;

import java.util.List;

import com.ticketmaster.interview.eventmanagerservice.model.Artist;

/**
 * Holds the test data
 *
 * @author Ganesh Shiva
 */
public class PayloadFixture {

    public static List<Artist> getArtists() {
        Artist artist1 = Artist.builder()
            .id("21")
            .name("HRH Prog")
            .rank(1)
            .url("/hrh-prog-tickets/artist/21")
            .imgSrc("//some-base-url/hrh-prog.jpg")
            .build();
        Artist artist2 = Artist.builder()
            .id("22")
            .name("Colosseum")
            .rank(2)
            .url("/colosseum-tickets/artist/22")
            .imgSrc("//some-base-url/colosseum.jpg")
            .build();
        return List.of(artist1, artist2);
    }
 }
