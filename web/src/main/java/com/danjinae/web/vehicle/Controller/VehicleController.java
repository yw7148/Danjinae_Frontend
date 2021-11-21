package com.danjinae.web.vehicle.Controller;

import java.sql.Timestamp;

import javax.websocket.server.PathParam;

import com.danjinae.web.SETTING;
import com.danjinae.web.HttpRequest.HttpSender;
import com.danjinae.web.vehicle.DTO.NewVehicle;
import com.danjinae.web.vehicle.DTO.NewVehicleRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/vehicle")
public class VehicleController {
    @Autowired
    HttpSender hSender;

    @GetMapping(path = "")
    public String VehicleIndex(Model model) {
        return "car1";
    }

    @GetMapping(path = "/check")
    public String VehicleSearch(Model model) {
        return "car-check";
    }

    @GetMapping(path = "/getlist")
    public String VehicleList(Model model, @RequestParam("number") Integer carNumber) {
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/vehicle/select/list?number=" + carNumber, null,
                HttpMethod.GET);
        model.addAttribute("result", result.getData());
        return "car-check2";
    }

    @GetMapping(path = "/selectvehicle")
    public String VehicleInfo(Model model, @RequestParam(value = "vehicleId") Integer vehicleId) {
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/vehicle/select/info?id=" + vehicleId, null,
                HttpMethod.GET);
        model.addAttribute("result", result.getData());
        return "car-check3";
    }

    @GetMapping(path = "/register")
    public String VehicleRegister(Model model) {
        return "car-registration";
    }

    @PostMapping(path = "/registerResult")
    public String AddNewNotice(Model model, NewVehicle newVehicle) {
        NewVehicleRequest request = new NewVehicleRequest();
        {
            request.setMgrid(SETTING.MGR_ID);
            request.setNumber(newVehicle.getCarnumber());
            request.setPhone(newVehicle.getCarphone());
        }
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/vehicle/resident", request, HttpMethod.POST);
        model.addAttribute("result", result.getData());
        if (!(Boolean) result.getData())
            return "redirect:/err/report?message=" + result.getMessage();
        else
            return "car-registrationcp";

    }
}
