package com.example.demo.controller;

import com.example.demo.model.RolNombre;
import com.example.demo.model.Usuario;
import com.example.demo.repository.RolRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String registerForm(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            Model model
    ) {
        boolean hasError = false;

        if (usuarioRepo.findByUsername(username).isPresent()) {
            model.addAttribute("usernameDuplicado", true);
            hasError = true;
        }

        if (usuarioRepo.findByEmail(email).isPresent()) {
            model.addAttribute("emailDuplicado", true);
            hasError = true;
        }

        if (hasError) {
            model.addAttribute("nombre", nombre);
            model.addAttribute("apellido", apellido);
            model.addAttribute("email", email);
            model.addAttribute("username", username);
            return "register"; // NO REDIRECT, retorna el formulario
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre(nombre);
        nuevo.setApellido(apellido);
        nuevo.setEmail(email);
        nuevo.setUsername(username);
        nuevo.setPassword(encoder.encode(password));
        nuevo.setRoles(Set.of(rolRepo.findByNombre(RolNombre.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("ROL_USER no encontrado"))));
        usuarioRepo.save(nuevo);

        return "redirect:/login?registered";
    }
}
