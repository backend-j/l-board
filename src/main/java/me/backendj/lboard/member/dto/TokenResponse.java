package me.backendj.lboard.member.dto;

import lombok.Data;

@Data
public class TokenResponse {

    private String token;
    private String tokenKey;

    public TokenResponse(String token, String key) {
        this.token = token;
        this.tokenKey = key;
    }
}
