package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductoController {

    @GetMapping("/productos")
    public String productos(Model model) {
        // Lista de Productos más vendidos
        List<Producto> productos = obtenerProductos();
        model.addAttribute("productos", productos);
        return "productos";
    }

    @GetMapping("/todos-los-productos")
    public String verTodosLosProductos(Model model) {
        // Lista de todos los productos
        List<Producto> todosLosProductos = obtenerProductos();
        model.addAttribute("todosLosProductos", todosLosProductos);
        return "todos-los-productos"; // Devolver la vista todos-los-productos
    }

    // Método para obtener todos los productos (simulado)
    private List<Producto> obtenerProductos() {
        List<Producto> productos = new ArrayList<>();

        productos.add(new Producto("Audífonos A9Plus", "S/. 45.00", "/img/A9Plus_01/A9Plus_01.avif"));
        productos.add(new Producto("Cargador Smartilike 67W", "S/. 25.00", "/img/Carg. Smartilike_67W/Carga.Smartilike_67W_01.avif"));
        productos.add(new Producto("Airpods Pro", "S/. 99.00", "/img/Airpods_Pro2/Airpods_Pro2_01.jpg"));
        productos.add(new Producto("Cargador Smartilike 35W", "S/. 40.00", "/img/Carg. Smartilike_35W/Carg.Smartilike_35W_01.avif"));
        productos.add(new Producto("Cargador Smartilike 3mAh", "S/. 20.00", "/img/Carg. Smartilike_3mAh/Carg.Smartilike_3mAh_01.jpg"));
        productos.add(new Producto("Cargador Smartilike 120W", "S/. 60.00", "/img/Carg. Smartilike_120W/Carg.Smartilike_120W_01.avif"));
        productos.add(new Producto("KZ EDX Pro", "S/. 85.00", "/img/KZ_EDX_Pro/KZ_EDX_Pro_01.jpg"));
        productos.add(new Producto("Zero SE60", "S/. 75.00", "/img/Zero_SE60/Zero_SE60_01.jpg"));

        return productos;
    }
}
