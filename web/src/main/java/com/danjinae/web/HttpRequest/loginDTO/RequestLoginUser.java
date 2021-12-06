package com.danjinae.web.HttpRequest.loginDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
public class RequestLoginUser {
    private String username;
    private String password;
}
