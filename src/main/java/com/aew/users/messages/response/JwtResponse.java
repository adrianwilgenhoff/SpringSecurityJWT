package com.aew.users.messages.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponse {
    
    private String token;
    private String type = "Bearer";
 
    public JwtResponse(String accessToken) {
        this.token = accessToken;
    }
 
}