package com.danjinae.web.mgfee.Controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danjinae.web.HttpRequest.HttpSender;
import com.danjinae.web.mgfee.RequestDTO.MgFee;
import com.danjinae.web.mgfee.RequestDTO.NewMgFeeRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/mgfee")
public class MgFeeController {
    @Autowired
    HttpSender hSender;

    @GetMapping(path = "")
    public String MgFeeIndex(Model model) {
        return "cost";
    }

    @GetMapping(path = "/getlist")
    public String GetMgFeeList(@RequestParam(value = "page", defaultValue = "0") Integer page, HttpServletRequest req, HttpServletResponse res, Model model ) {
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/mgfee/getmanagermgfee?page=" + page, null,
                req, res ,HttpMethod.GET);
        model.addAttribute("result", result.getData());
        return "cost-check";
    }

    @GetMapping(path = "/newMgFee")
    public String NewMgFeePage(Model model) {
        return "cost1";
    }

    @PostMapping(path = "/newMgFeeResult")
    public String SetNewMgFee(Model model, HttpServletRequest req, HttpServletResponse res,  MgFee newMgFee) {
        NewMgFeeRequest request = new NewMgFeeRequest();
        {
            request.setAddress(newMgFee.getDong() + "동" + newMgFee.getHo() + "호");
            request.setCatId(newMgFee.getCatId());
            request.setContent(newMgFee.getContent());
            request.setFee(newMgFee.getFee());
            request.setDate(newMgFee.getDate());
        }
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/mgfee/setManagerMgFee", request,
                req, res ,HttpMethod.POST);
        model.addAttribute("result", result.getData());

        if (!(Boolean) result.getData())
            return "redirect:/err/report?message=" + result.getMessage();
        else
            return "costcp";
    }

}
