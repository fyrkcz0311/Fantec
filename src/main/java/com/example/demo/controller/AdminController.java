package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("titulo", "Panel de Administración");
        model.addAttribute("username", userDetails.getUsername());
        return "admin-dashboard";
    }

}
