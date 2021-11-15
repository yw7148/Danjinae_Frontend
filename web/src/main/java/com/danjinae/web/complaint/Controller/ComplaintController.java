package com.danjinae.web.complaint.Controller;

import com.danjinae.web.HttpRequest.HttpSender;
import com.danjinae.web.complaint.RequestDTO.ComplaintProcess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/complaint")
public class ComplaintController {
    @Autowired
    HttpSender hSender;

    @GetMapping(path = "/")
    public String ComplaintIndex(Model model) {
        return "complaint_temp";
    }

    @GetMapping(path = "/select/{aptId}")
    public String ComplaintList(Model model, @PathVariable Integer aptId) {
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/complaint/select", aptId, HttpMethod.GET);
        model.addAttribute("result", result);
        return "helloWorld";
    }

    @PostMapping(path = "/newprocess")
    public String AddNewProcess(Model model, ComplaintProcess newProcess) {
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/complaint/addprocess", newProcess,
                HttpMethod.POST);
        model.addAttribute("result", result);
        return "helloWorld";
    }
}
