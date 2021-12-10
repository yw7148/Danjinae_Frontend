package com.danjinae.web.vehicle.Controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.danjinae.web.SETTING;
import com.danjinae.web.HttpRequest.HttpSender;
import com.danjinae.web.vehicle.DTO.NewVehicle;
import com.danjinae.web.vehicle.DTO.NewVehicleRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String VehicleList(HttpServletRequest req, HttpServletResponse res, Model model, @RequestParam("number") Integer carNumber, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/vehicle/select/list?number=" + carNumber + "&page=" + page, null,
                req, res ,HttpMethod.GET);
        model.addAttribute("result", result.getData());
        model.addAttribute("number", carNumber);
        return "car-check2";
    }

    @GetMapping(path = "/selectvehicle")
    public String VehicleInfo(HttpServletRequest req, HttpServletResponse res, Model model, @RequestParam(value = "vehicleId") Integer vehicleId) {
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/vehicle/select/info?id=" + vehicleId, null,
                req, res ,HttpMethod.GET);
        model.addAttribute("result", result.getData());
        return "car-check3";
    }

    @GetMapping(path = "/register")
    public String VehicleRegister(Model model) {
        return "car-registration";
    }

    @PostMapping(path = "/registerResult")
    public String AddNewNotice(HttpServletRequest req, HttpServletResponse res, Model model, NewVehicle newVehicle) {
        NewVehicleRequest request = new NewVehicleRequest();
        {
            request.setMgrid(SETTING.MGR_ID);
            request.setNumber(newVehicle.getCarnumber());
            request.setPhone(newVehicle.getCarphone());
        }
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/vehicle/resident", request, req, res ,HttpMethod.POST);
        model.addAttribute("result", result.getData());
        if (!(Boolean) result.getData())
            return "redirect:/err/report?message=" + result.getMessage();
        else
            return "car-registrationcp";

    }
}
