package com.danjinae.web.HttpRequest.loginDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtToken {
    public final static long TOKEN_LIFE_SECOND = 1000L * 60 * 30;
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 60 * 24 * 2;

    final static public String ACCESS_TOKEN_NAME = "ACCESS_TOKEN";
    final static public String REFRESH_TOKEN_NAME = "REFRESH_TOKEN";

    String accessToken;
    String refreshToken;
}

