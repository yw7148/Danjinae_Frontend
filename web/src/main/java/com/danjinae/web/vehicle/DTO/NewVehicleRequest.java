package com.danjinae.web.vehicle.DTO;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewVehicleRequest {
    Integer mgrid;
    String number;
    String phone;
}
