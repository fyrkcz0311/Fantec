package com.example.demo;

public class Producto {
    private String nombre;
    private String precio;
    private String imagen;

    // Constructor, getters y setters
    public Producto(String nombre, String precio, String imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public String getImagen() {
        return imagen;
    }
}

