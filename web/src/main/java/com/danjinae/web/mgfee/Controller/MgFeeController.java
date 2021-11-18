package com.danjinae.web.mgfee.Controller;

import com.danjinae.web.HttpRequest.HttpSender;
import com.danjinae.web.mgfee.RequestDTO.ListRequest;
import com.danjinae.web.mgfee.RequestDTO.MgFee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/mgfee")
public class MgFeeController {
    @Autowired
    HttpSender hSender;

    @GetMapping(path = "/")
    public String MgFeeIndex(Model model) {
        return "mgfee_temp";
    }

    @GetMapping(path = "/getlist")
    public String GetMgFeeList(Model model, ListRequest ids) {
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/mgfee/getmgfee", ids, HttpMethod.POST);
        model.addAttribute("result", result);
        return "helloWorld";
    }

    @GetMapping(path = "/newMgFee")
    public String NewMgFeePage(Model model) {
        return "cost1";
    }

    @PostMapping(path = "/newMgFeeResult")
    public String SetNewMgFee(Model model, MgFee newMgFee) {
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/mgfee/setManagerMgFee", newMgFee,
                HttpMethod.POST);
        model.addAttribute("result", result);

        if(!(Boolean)result.getData())
            return "redirect:/err/report?message=" +result.getMessage();
        else
            return "costcp";
    }

}
