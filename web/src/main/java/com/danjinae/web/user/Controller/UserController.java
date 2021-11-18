package com.danjinae.web.user.Controller;

import java.sql.Timestamp;

import com.danjinae.web.HttpRequest.HttpSender;
import com.danjinae.web.notice.RequestDTO.Notice;
import com.danjinae.web.user.DTO.NewUser;
import com.danjinae.web.user.DTO.NewUserRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    HttpSender hSender;

    @GetMapping(path = "/register")
    public String NoticeIndex(Model model) {
        return "registration1";
    }

    @PostMapping(path = "/registerResult")
    public String AddNewNotice(Model model, NewUser newUser) {
        NewUserRequest request = new NewUserRequest();
        {
            
        }
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/user/add", request, HttpMethod.POST);
        model.addAttribute("result", result);
        if(!(Boolean)result.getData())
            return "redirect:/err/report?message=" +result.getMessage();
        else
            return "registrationcp";

    }
}
