package com.example.demo.controller;

import com.example.demo.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactoAdminController {

    @Autowired
    private ContactoRepository contactoRepository;

    @GetMapping("/admin/contactos")
    public String listarContactos(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        model.addAttribute("contactos", contactoRepository.findAll());
        return "admin-contactos";
    }
}
