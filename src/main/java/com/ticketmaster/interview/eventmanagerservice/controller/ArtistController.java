package com.ticketmaster.interview.eventmanagerservice.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketmaster.interview.eventmanagerservice.model.ArtistInformation;
import com.ticketmaster.interview.eventmanagerservice.model.EventManagerResponse;
import com.ticketmaster.interview.eventmanagerservice.model.ResultState;
import com.ticketmaster.interview.eventmanagerservice.service.ArtistService;

import lombok.extern.slf4j.Slf4j;

/**
 * The key controller for event management operations exposes REST API to fetch Artist information for the given artistId.
 *
 * @author Ganesh Shiva
 */
@Slf4j
@RequestMapping(value = "eventManager/v1")
@RestController
public class ArtistController {

    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;
    private static final String UTF8 = "charset=utf-8";
    private static final String PRODUCES = JSON + "; " + UTF8;

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping(value = "/artist/{artistId}", produces = PRODUCES)
    public ResponseEntity<EventManagerResponse> getArtistInfo(@PathVariable("artistId") String artistId) {
        log.info("Get Artist Info for artistId {}", artistId);
        try {
            var artistInformation = artistService.getArtistInfo(artistId);
            EventManagerResponse eventManagerResponse = EventManagerResponse.builder()
                .result(ResultState.SUCCESS)
                .artistInfo(artistInformation)
                .build();
            return ResponseEntity.status(HttpStatus.OK).body(eventManagerResponse);
        } catch (Exception ex) {
            log.error("Exception occurred while getting Artist Information : {}", ex.getMessage(), ex);
            EventManagerResponse eventManagerResponse = EventManagerResponse.builder()
                .result(ResultState.ERROR)
                .messages(Collections.singletonList(ex.getMessage()))
                .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(eventManagerResponse);
        }
    }
}
