package com.danjinae.web.user.DTO;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUser {
    Integer mgrid;
    String name;
    String address;
    Date birth;
    String carnumber;
    String carphone;
    String phone;
}
