package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/admin/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "admin-usuarios";
    }
}
