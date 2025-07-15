package com.example.demo.security;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UsuarioService usuarioService;
    private final JwtFilter jwtFilter;
    private final CustomSuccessHandler successHandler;

    public SecurityConfig(UsuarioService usuarioService, JwtFilter jwtFilter, CustomSuccessHandler successHandler) {
        this.usuarioService = usuarioService;
        this.jwtFilter = jwtFilter;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/register", "/login", "/form-register", "/css/**", "/js/**", "/img/**").permitAll()
                        .requestMatchers("/admin/productos").hasRole("ADMIN")
                        .requestMatchers("/", "/productos", "/todos-los-productos", "/enviar-mensaje", "/mensaje-enviado").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(successHandler)  // ✅ REDIRECCIÓN POR ROL
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .userDetailsService(usuarioService)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
