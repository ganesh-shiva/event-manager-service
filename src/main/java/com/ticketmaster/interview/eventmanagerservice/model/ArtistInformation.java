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
 * Bean class represents Artist information along with events and venue details
 *
 * @author Ganesh Shiva
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Getter
@AllArgsConstructor
@Builder
public class ArtistInformation {

    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private String id;
    @JsonProperty("imgSrc")
    private String imgSrc;
    @JsonProperty("url")
    private String url;
    @JsonProperty("rank")
    private int rank;
    @JsonProperty("events")
    private List<EventInfo> events;

    public ArtistInformation() {
    }
}
