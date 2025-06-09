package com.example.demo.controller;

import com.example.demo.model.Contacto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactoController {

    @GetMapping("/contacto")
    public String mostrarFormularioContacto(Model model) {
        model.addAttribute("contacto", new Contacto());
        return "productos";
    }

    @PostMapping("/enviar-mensaje")
    public String enviarMensaje(@Valid @ModelAttribute("contacto") Contacto contacto,
                                BindingResult result,
                                Model model) {

        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Por favor, ingresa datos válidos.");
            return "productos";
        }

        model.addAttribute("name", contacto.getName());
        model.addAttribute("mensajeExito", "¡Gracias por contactarnos!");
        return "mensaje-enviado";
    }
}
