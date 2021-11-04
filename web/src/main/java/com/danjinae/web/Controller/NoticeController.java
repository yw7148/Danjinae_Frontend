package com.danjinae.web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NoticeController {
    
    @RequestMapping(path="/test")
    public String Index()
    {
        return "helloWorld";
    }
}
