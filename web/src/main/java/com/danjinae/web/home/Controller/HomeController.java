package com.danjinae.web.home.Controller;

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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @GetMapping(path = "/err/report")
    public String ErrorPage(Model model, @RequestParam(value = "message") String message)
    {
      model.addAttribute("message", message);
		  model.addAttribute("redirectUri", "javascript:history.back();");
		  return "alert";
    }

    @GetMapping(path = "/changeApt")
    public String ChangeAptId(Model model)
    {
      if(SETTING.APT_ID == 1)
      {
        SETTING.APT_ID = 2;
        SETTING.MGR_ID = 2;
      }
      else if(SETTING.APT_ID == 2)
      {
        SETTING.APT_ID = 1;
        SETTING.MGR_ID = 1;
      }
      model.addAttribute("message", "매니저 및 아파트 ID: " + SETTING.APT_ID);
		  model.addAttribute("redirectUri", "javascript:history.back();");
      return "alert";
    }



    @GetMapping(path = "/")
    public String Index(Model model)
    {
		  return "index";
    }
}
