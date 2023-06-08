package com.davydovskyi.spotify.domain;

import lombok.Data;

@Data
public class AccessTokenRequest {
    private String grantType;
    private String code;
    private String redirectUri;

    public AccessTokenRequest(String code, String redirectUri) {
        this.code = code;
        this.redirectUri = redirectUri; 
        this.grantType = "authorization_code";
    }
}
