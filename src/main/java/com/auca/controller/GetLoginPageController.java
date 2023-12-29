package com.auca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GetLoginPageController {
    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }
}
