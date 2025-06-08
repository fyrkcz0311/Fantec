package com.example.demo.controller;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/productos")
    public String productos(Model model) {
        List<Producto> productos = productoRepository.findAll(); // desde BD
        model.addAttribute("productos", productos);
        return "productos";
    }

    @GetMapping("/todos-los-productos")
    public String verTodosLosProductos(Model model) {
        List<Producto> todosLosProductos = productoRepository.findAll(); // desde BD
        model.addAttribute("todosLosProductos", todosLosProductos);
        return "todos-los-productos";
    }
}
