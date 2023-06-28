package com.ticketmaster.interview.eventmanagerservice.config;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import com.ticketmaster.interview.eventmanagerservice.client.EventManagerRestClient;
import com.ticketmaster.interview.eventmanagerservice.util.Serde;

/**
 * Config class for creating Spring beans
 *
 * @author Ganesh Shiva
 */
@Configuration
public class EventManagerConfiguration {

    @Bean
    public EventManagerRestClient eventManagerRestClient()
        throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException,
        CertificateException, IOException {
        var requestConfig = RequestConfig.custom()
            .setConnectTimeout(
                Math.toIntExact(5000))
            .setSocketTimeout(
                Math.toIntExact(5000))
            .build();

        var registryBuilder = getRegistryBuilder(true);

        var connManager = getPoolingHttpClientConnectionManager(
            registryBuilder);

        var httpClientBuilder =
            HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig);

        return EventManagerRestClient.builder()
            .serde(new Serde())
            .httpClient(httpClientBuilder.build())
            .build();
    }

    private RegistryBuilder<ConnectionSocketFactory> getRegistryBuilder(boolean isEnableClientAuth)
        throws KeyManagementException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException,
        IOException, CertificateException {
        var registryBuilder =
            RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE);

        if (isEnableClientAuth) {

            //Creating SSLContextBuilder object
            SSLContextBuilder sslBuilder = SSLContexts.custom();
            File file = ResourceUtils.getFile("classpath:cert/keystore.jks");
            sslBuilder = sslBuilder.loadTrustMaterial(file,
                "changeit".toCharArray());

            //Building the SSLContext using the build() method
            SSLContext sslcontext = sslBuilder.build();

            //Creating SSLConnectionSocketFactory object
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslcontext, new NoopHostnameVerifier());

            registryBuilder.register("https", sslConnectionSocketFactory);
        }
        return registryBuilder;
    }

    private PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager(
        RegistryBuilder<ConnectionSocketFactory> registryBuilder) {
        var connManager =
            new PoolingHttpClientConnectionManager(
                registryBuilder.build(),
                null,
                null,
                null, 5000,
                TimeUnit.MILLISECONDS);

        connManager.setDefaultMaxPerRoute(128);
        connManager.setMaxTotal(128);
        return connManager;
    }
}
