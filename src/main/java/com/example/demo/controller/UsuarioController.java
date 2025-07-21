package com.example.demo.controller;

import com.example.demo.model.Rol;
import com.example.demo.model.Usuario;
import com.example.demo.repository.RolRepository;
import com.example.demo.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String listarUsuarios(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "admin-usuarios";
    }

    @GetMapping("/nuevo")
    public String nuevoUsuario(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());

        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolRepository.findAll());
        return "form-usuario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(
            @Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult result,
            @RequestParam List<Long> rolesSeleccionados,
            Model model
    ) {
        // Verificar si el username ya existe (solo en nuevos usuarios o si cambia el username)
        Optional<Usuario> existente = usuarioRepository.findByUsername(usuario.getUsername());
        boolean esNuevo = usuario.getId() == null;
        if (existente.isPresent() && (esNuevo || !existente.get().getId().equals(usuario.getId()))) {
            result.rejectValue("username", "error.usuario", "El nombre de usuario ya existe");
        }

        if (result.hasErrors()) {
            model.addAttribute("roles", rolRepository.findAll());
            return "form-usuario";
        }

        Set<Rol> roles = new HashSet<>(rolRepository.findAllById(rolesSeleccionados));
        usuario.setRoles(roles);

        if (!esNuevo) {
            Usuario existenteDb = usuarioRepository.findById(usuario.getId()).orElseThrow();

            // Mantener contraseña si el campo está vacío
            if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                usuario.setPassword(existenteDb.getPassword());
            } else {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
        } else {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        usuarioRepository.save(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolRepository.findAll());
        return "form-usuario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/admin/usuarios";
    }
}
