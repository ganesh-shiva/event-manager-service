package com.ticketmaster.interview.eventmanagerservice.model;

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
public class Artist {

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

    public Artist(){
    }
}
