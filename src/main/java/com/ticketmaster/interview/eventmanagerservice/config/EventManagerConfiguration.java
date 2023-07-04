package com.ticketmaster.interview.eventmanagerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.ticketmaster.interview.eventmanagerservice.client.EventManagerRestClient;
import com.ticketmaster.interview.eventmanagerservice.exception.PublicAPIException;

import reactor.core.publisher.Mono;

/**
 * Config class for creating Spring beans
 *
 * @author Ganesh Shiva
 */
@Configuration
public class EventManagerConfiguration {

    @Bean
    public EventManagerRestClient eventManagerRestClient() {
        return EventManagerRestClient.builder().webClient(webClient()).build();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .filter(ExchangeFilterFunction
                .ofResponseProcessor(this::exchangeFilterResponseProcessor))
            .build();
    }

    private Mono<ClientResponse> exchangeFilterResponseProcessor(ClientResponse response) {
        HttpStatus status = (HttpStatus) response.statusCode();
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            return response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new PublicAPIException(body)));
        }
        if (HttpStatus.BAD_REQUEST.equals(status)) {
            return response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new PublicAPIException(body)));
        }
        return Mono.just(response);
    }
}
