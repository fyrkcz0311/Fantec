package com.example.demo.controller;

import com.example.demo.model.Contacto;
import com.example.demo.repository.ContactoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ContactoController {

    @Autowired
    private ContactoRepository contactoRepository;

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

        contactoRepository.save(contacto);
        model.addAttribute("name", contacto.getName());
        return "mensaje-enviado";
    }
}
