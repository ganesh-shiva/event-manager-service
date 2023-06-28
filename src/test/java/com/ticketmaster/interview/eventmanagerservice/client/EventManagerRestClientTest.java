package com.ticketmaster.interview.eventmanagerservice.client;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.ticketmaster.interview.eventmanagerservice.util.Serde;

/**
 * @author Ganesh Shiva
 */
@RunWith(MockitoJUnitRunner.class)
public class EventManagerRestClientTest {

    /*private final Serde serde = new Serde();
    private EventManagerRestClient restClient;

    @Mock
    private CloseableHttpClient mockHttpClient;
    @Mock
    CloseableHttpResponse mockResponse;
    @Mock
    InputStream mockInputStream;
    @Mock
    StatusLine mockStatusLine;

    @Before
    public void setup() throws Exception {
        restClient = EventManagerRestClient.builder()
            .httpClient(mockHttpClient)
            .serde(serde)
            .build();

        HttpEntity mockResponseEntity = mock(HttpEntity.class);
        when(mockResponse.getEntity()).thenReturn(mockResponseEntity);
        when(mockResponse.getStatusLine()).thenReturn(mockStatusLine);
        when(mockResponseEntity.getContent()).thenReturn(mockInputStream);
    }

    @Test
    public void getArtists200Status() throws Exception {
        var fixtureResponse = SesPayloadFixture.getAllActiveChangeSet();
        byte[] responseJson = serde.serialise(fixtureResponse).getBytes();
        when(mockHttpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);
        when(mockInputStream.readAllBytes()).thenReturn(responseJson);
        when(mockStatusLine.getStatusCode()).thenReturn(200);

        var actualResponse = restClient.getArtists();
        assertNotNull(actualResponse);
        actualResponse.getActiveChangeSetResponseLiteList().forEach(i -> {
            assertNotNull(i.getId());
            assertNotNull(i.getStatus());
        });
    }*/


}
