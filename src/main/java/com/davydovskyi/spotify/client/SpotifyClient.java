package com.davydovskyi.spotify.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.davydovskyi.spotify.domain.AccessTokenRequest;
import com.davydovskyi.spotify.domain.AccessTokenResponse;

@FeignClient(name = "spotifyClient", url = "https://accounts.spotify.com")
public interface SpotifyClient {

    @PostMapping(value = "/api/token", consumes = "application/x-www-form-urlencoded")
    AccessTokenResponse getAccessToken(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody AccessTokenRequest requestBody);

    // Other methods for interacting with Spotify API endpoints
}
