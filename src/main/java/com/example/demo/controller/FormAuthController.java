package com.example.demo.controller;

import com.example.demo.model.RolNombre;
import com.example.demo.model.Usuario;
import com.example.demo.repository.RolRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class FormAuthController {

    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private RolRepository rolRepo;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/form-register")
    public String registerForm(@RequestParam String username, @RequestParam String password) {
        System.out.println("Intentando registrar: " + username);

        if (usuarioRepo.findByUsername(username).isPresent()) {
            return "redirect:/register?error";
        }

        Usuario nuevo = new Usuario();
        nuevo.setUsername(username);
        nuevo.setPassword(encoder.encode(password));
        nuevo.setRoles(Set.of(rolRepo.findByNombre(RolNombre.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("ROL_USER no encontrado"))));
        usuarioRepo.save(nuevo);

        return "redirect:/login?registered";
    }

}

