package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.model.Rol;
import com.example.demo.model.RolNombre;
import com.example.demo.model.Usuario;
import com.example.demo.repository.RolRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager,
                          UsuarioRepository usuarioRepo,
                          RolRepository rolRepo,
                          PasswordEncoder encoder,
                          JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    //LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(Map.of("jwt", token));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    //REGISTRO
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        if (usuarioRepo.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Ese usuario ya existe");
        }

        Usuario nuevo = new Usuario();
        nuevo.setUsername(request.getUsername());
        nuevo.setPassword(encoder.encode(request.getPassword()));

        Rol rolUser = rolRepo.findByNombre(RolNombre.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("ROL_USER no encontrado"));

        nuevo.setRoles(Set.of(rolUser));
        usuarioRepo.save(nuevo);

        return ResponseEntity.ok("Usuario registrado correctamente");
    }
}
