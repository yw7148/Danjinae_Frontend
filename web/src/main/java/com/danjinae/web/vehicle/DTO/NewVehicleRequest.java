package com.danjinae.web.vehicle.DTO;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewVehicleRequest {
    Integer mgrid;
    String name;
    String address;
    Timestamp birth;
    String phone;
}
