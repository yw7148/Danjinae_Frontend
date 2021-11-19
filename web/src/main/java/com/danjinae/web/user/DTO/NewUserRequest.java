package com.danjinae.web.user.DTO;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserRequest {
    Integer mgrid;
    String name;
    String address;
    String birth;
    String phone;
}
