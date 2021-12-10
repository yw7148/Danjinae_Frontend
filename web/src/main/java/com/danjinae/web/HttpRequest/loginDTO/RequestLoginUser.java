package com.danjinae.web.HttpRequest.loginDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class RequestLoginUser {
    private String phone;
    private String password;
}
