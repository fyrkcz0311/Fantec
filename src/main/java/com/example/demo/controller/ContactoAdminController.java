package com.example.demo.controller;

import com.example.demo.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactoAdminController {

    @Autowired
    private ContactoRepository contactoRepository;

    @GetMapping("/admin/contactos")
    public String listarContactos(Model model) {
        model.addAttribute("contactos", contactoRepository.findAll());
        return "admin-contactos";
    }
}
