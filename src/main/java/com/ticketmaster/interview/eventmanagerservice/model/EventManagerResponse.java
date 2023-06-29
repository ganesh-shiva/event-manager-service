package com.ticketmaster.interview.eventmanagerservice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Wrapper response class encapsulating ArtistInformation if SUCCESS, otherwise shows error message
 *
 * @author Ganesh Shiva
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Getter
@AllArgsConstructor
@Builder
public class EventManagerResponse {

    @JsonProperty(value = "result")
    private ResultState result;

    @JsonProperty("artistInfo")
    private ArtistInformation artistInfo;

    @JsonProperty(value = "messages")
    private List<String> messages;
}
