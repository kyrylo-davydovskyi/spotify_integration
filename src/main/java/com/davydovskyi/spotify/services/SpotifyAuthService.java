package com.davydovskyi.spotify.services;

import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.davydovskyi.spotify.client.SpotifyClient;
import com.davydovskyi.spotify.domain.AccessTokenRequest;
import com.davydovskyi.spotify.domain.LoginResponseDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SpotifyAuthService {
    @Value("${spotify.clientId}")
    private String clientId;
    @Value("${spotify.clientSecret}")
    private String clientSecret;
    @Value("${spotify.redirectUri}")
    private String redirectUri;
    
    @Autowired
    private SpotifyClient spotifyClient;

    public LoginResponseDTO buildAuthorizeUrl(String state, String scope) {
        var authUri = "https://accounts.spotify.com/authorize?client_id=" + clientId + "&response_type=code&redirect_uri=" + redirectUri + "&state=" + state + "&scope=" + scope + "&show_dialog=true";
        return new LoginResponseDTO(authUri);
    }

    public void exchangeCodeForToken(String code) {
        var response = spotifyClient.getAccessToken(buildAuthHeader(), buildBody(code));
        log.info("Response: {}", response);
    }

    private String buildAuthHeader() {
        var base64 = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
        return "Basic " + base64;
    }

    private Map<String, String> buildBody(String code) {
        var body = new ConcurrentHashMap<String, String>();
        body.put("grant_type", "authorization_code");
        body.put("code", code);
        body.put("redirect_uri", redirectUri);
        return body;
    }
}
