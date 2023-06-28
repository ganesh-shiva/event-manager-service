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
 * @author Ganesh Shiva
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Getter
@AllArgsConstructor
@Builder
public class Event {

    @JsonProperty("title")
    private String title;
    @JsonProperty("id")
    private String id;
    @JsonProperty("dateStatus")
    private String dateStatus;
    @JsonProperty("timeZone")
    private String timeZone;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("artists")
    private List<Artist> artists;
    @JsonProperty("venue")
    private Venue venue;
    @JsonProperty("hiddenFromSearch")
    private boolean hiddenFromSearch;

    public Event() {
    }
}
