package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // apunta a login.html
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // apunta a register.html
    }
}
