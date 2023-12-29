package com.auca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShowSignupPageController {
    @GetMapping("/signup")
    public String showSignupPage(Model model){
        model.addAttribute("pageTitle", "Sign Up");
        return "signup";
    }
}
