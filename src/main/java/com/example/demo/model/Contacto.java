package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
