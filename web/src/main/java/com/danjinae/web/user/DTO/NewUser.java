package com.danjinae.web.user.DTO;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUser {
    Integer mgrId;
    String name;
    String address;
    String birth;
    String carnumber;
    String carphone;
    String phone;
}
