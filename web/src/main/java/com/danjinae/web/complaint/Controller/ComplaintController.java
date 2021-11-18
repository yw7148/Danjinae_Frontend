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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/complaint")
public class ComplaintController {
    @Autowired
    HttpSender hSender;

    @GetMapping(path = "")
    public String ComplaintIndex(Model model, @RequestParam(value = "aptId") Integer aptId) {
        
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/complaint/get?id="+aptId, null, HttpMethod.GET);
        model.addAttribute("result", result);
        return "complaint1";
    }

    @GetMapping(path = "/select/{cplId}")
    public String ComplaintList(Model model, @PathVariable Integer cplId) {
        
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/complaint/select?id="+cplId, null, HttpMethod.GET);
        model.addAttribute("result", result);
        return "complaint2";
    }

    @PostMapping(path = "/newprocess")
    public String AddNewProcess(Model model, ComplaintProcess newProcess) {
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/complaint/addprocess", newProcess,
                HttpMethod.POST);
        model.addAttribute("result", result);
        if(!(Boolean)result.getData())
            return "redirect:/err/report?message="+result.getMessage();
        else
            return "complaintcp";
    }
}
