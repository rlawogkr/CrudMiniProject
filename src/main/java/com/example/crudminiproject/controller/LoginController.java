package com.example.crudminiproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginP(){
        return "login";
    }

    @PostMapping("/login")
    public String login(){
        return "redirect:/posts";
    }

}
