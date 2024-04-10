package com.muglog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping(value =  {"/login", "/edit", "/google/callback", "/naver/callback", "/login/success"})
    public String forward() {
        return "forward:/index.html";
    }
}
