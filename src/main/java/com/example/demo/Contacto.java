package com.example.demo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class Contacto {

    @NotNull(message = "El correo es obligatorio.")
    @Email(message = "Por favor ingresa un correo válido.")
    private String email;

    @NotNull(message = "El nombre es obligatorio.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "El nombre solo puede contener letras y espacios.")
    private String name;

    @NotNull(message = "El teléfono es obligatorio.")
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono solo puede contener números.")
    private String phone;

    @NotNull(message = "El mensaje es obligatorio.")
    @Size(min = 5, message = "El mensaje debe tener al menos 5 caracteres.")
    private String message;

    // Getters y Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
