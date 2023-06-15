package com.davydovskyi.spotify.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import se.michaelthelin.spotify.SpotifyApi;

@Configuration
public class AppConfigurations {

    @Value("${spotify.clientId}")
    private String clientId;
    @Value("${spotify.clientSecret}")
    private String clientSecret;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @SneakyThrows
    public SpotifyApi spotfyClient() {
        var api = buildSpotifyApi();
        var request = api.clientCredentials().build();
        var credentials = request.execute();
        var token = credentials.getAccessToken();
        api.setAccessToken(token);
        return api;
    }

    private SpotifyApi buildSpotifyApi() {
        return new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();
    }
}
