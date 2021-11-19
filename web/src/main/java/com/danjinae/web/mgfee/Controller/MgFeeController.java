package com.danjinae.web.mgfee.Controller;

import com.danjinae.web.HttpRequest.HttpSender;
import com.danjinae.web.mgfee.RequestDTO.ListRequest;
import com.danjinae.web.mgfee.RequestDTO.MgFee;
import com.danjinae.web.mgfee.RequestDTO.NewMgFeeRequest;

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

    private Integer aptId = 1;

    @GetMapping(path = "")
    public String MgFeeIndex(Model model) {
        return "cost";
    }

    @GetMapping(path = "/getlist")
    public String GetMgFeeList(Model model, ListRequest ids) {
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/mgfee/getmgfee?userId=" + ids.getUserId(), null,
                HttpMethod.GET);
        model.addAttribute("result", result.getData());
        return "helloWorld";
    }

    @GetMapping(path = "/newMgFee")
    public String NewMgFeePage(Model model) {
        return "cost1";
    }

    @PostMapping(path = "/newMgFeeResult")
    public String SetNewMgFee(Model model, MgFee newMgFee) {
        NewMgFeeRequest request = new NewMgFeeRequest();
        {
            request.setAddress(newMgFee.getDong() + "동" + newMgFee.getHo() + "호");
            request.setAptId(aptId);
            request.setCatId(newMgFee.getCatId());
            request.setContent(newMgFee.getContent());
            request.setFee(newMgFee.getFee());
            request.setDate(newMgFee.getDate());
        }
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/mgfee/setManagerMgFee", request,
                HttpMethod.POST);
        model.addAttribute("result", result.getData());

        if (!(Boolean) result.getData())
            return "redirect:/err/report?message=" + result.getMessage();
        else
            return "costcp";
    }

}
