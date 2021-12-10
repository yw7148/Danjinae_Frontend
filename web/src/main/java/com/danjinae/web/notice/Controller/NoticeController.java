package com.danjinae.web.notice.Controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.danjinae.web.SETTING;
import com.danjinae.web.HttpRequest.HttpSender;
import com.danjinae.web.notice.RequestDTO.Notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/notice")
public class NoticeController {
    @Autowired
    HttpSender hSender;

    @GetMapping(path = "")
    public String NoticeIndex(Model model) {
        return "notice";
    }

    @PostMapping(path = "/newnotice")
    public String AddNewNotice(HttpServletRequest req, HttpServletResponse res, Model model, Notice newNotice) {
        newNotice.setAptId(SETTING.APT_ID);
        var result = hSender.defHttpRequest("http://101.101.219.69:8080/notice/add", newNotice, req, res ,HttpMethod.POST);
        model.addAttribute("result", result.getData());
        if (!(Boolean) result.getData())
            return "redirect:/err/report?message=" + result.getMessage();
        else
            return "noticecp";

    }
}
