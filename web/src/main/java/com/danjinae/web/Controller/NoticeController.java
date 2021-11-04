package com.danjinae.web.Controller;

import com.danjinae.web.HttpRequest.HttpSender;
import com.danjinae.web.HttpRequest.RequestDTO.Notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NoticeController {
    @Autowired
    HttpSender hSender;

    @RequestMapping(path="/test")
    public String Index(Model model)
    {
        Notice newNotice = new Notice();
        newNotice.setContent("Test2");
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/addnotice", newNotice, HttpMethod.PUT);
        model.addAttribute("result", result);
        return "helloWorld";
    }
}
