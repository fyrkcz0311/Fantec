package com.example.demo.controller;

import com.example.demo.model.Contacto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ContactoController {

    @PostMapping("/enviar-mensaje")
    public String enviarMensaje(@Valid Contacto contacto, BindingResult result, Model model) {

        // Validación de los campos
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Por favor, ingresa datos válidos.");
            return "contacto";
        }

        model.addAttribute("email", contacto.getEmail());
        model.addAttribute("name", contacto.getName());
        model.addAttribute("phone", contacto.getPhone());
        model.addAttribute("message", contacto.getMessage());

        return "mensaje-enviado";
    }
}

