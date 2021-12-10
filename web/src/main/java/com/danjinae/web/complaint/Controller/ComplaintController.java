package com.danjinae.web.complaint.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danjinae.web.SETTING;
import com.danjinae.web.HttpRequest.HttpSender;
import com.danjinae.web.HttpRequest.Response.MyHttpResponse;
import com.danjinae.web.complaint.RequestDTO.ComplaintProcess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/complaint")
public class ComplaintController {
    @Autowired
    HttpSender hSender;

    @GetMapping(path = "")
    public String ComplaintIndex(@RequestParam(value = "page", defaultValue = "0") Integer page, HttpServletRequest req, HttpServletResponse res, Model model) {

        var result = hSender.defHttpRequest("http://101.101.219.69:8080/complaint/get/" + SETTING.APT_ID + "?page=" + page, null, req, res ,HttpMethod.GET);
        model.addAttribute("result", result.getData());
        return "complaint1";
    }

    @GetMapping(path = "/select")
    public String ComplaintList(Model model, HttpServletRequest req, HttpServletResponse res, @RequestParam(value = "cplId") Integer cplId) {

        var result = hSender.defHttpRequest("http://101.101.219.69:8080/complaint/select/" + cplId, null,
                req, res ,HttpMethod.GET);
        model.addAttribute("result", result.getData());
        return "complaint2";
    }

    @PostMapping(path = "/newprocess")
    public String AddNewProcess(Model model, HttpServletRequest req, HttpServletResponse res, ComplaintProcess newProcess) {
        MyHttpResponse result = hSender.defHttpRequest("http://101.101.219.69:8080/complaint/addprocess", newProcess,
                req, res ,HttpMethod.POST);
        model.addAttribute("result", result.getData());
        if (!(Boolean) result.getData())
            return "redirect:/err/report?message=" + result.getMessage();
        else
            return "complaintcp";
    }
}
