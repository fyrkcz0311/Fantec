package com.example.demo.controller;

import com.example.demo.model.Contacto;
import com.example.demo.repository.ContactoRepository;
import com.example.demo.repository.ProductoRepository;
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

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/contacto")
    public String mostrarFormularioContacto(Model model) {
        model.addAttribute("contacto", new Contacto());
        model.addAttribute("productos", productoRepository.findAll());
        return "productos";
    }

    @PostMapping("/enviar-mensaje")
    public String enviarMensaje(@Valid @ModelAttribute("contacto") Contacto contacto,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Por favor, ingresa datos válidos.");
            model.addAttribute("productos", productoRepository.findAll());
            return "productos";
        }

        contactoRepository.save(contacto);
        model.addAttribute("name", contacto.getName());
        return "mensaje-enviado";
    }
}

