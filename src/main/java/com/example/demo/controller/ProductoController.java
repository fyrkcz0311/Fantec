package com.example.demo.controller;

import com.example.demo.model.Categoria;
import com.example.demo.model.Contacto;
import com.example.demo.model.Producto;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/productos")
    public String productos(Model model) {
        List<Producto> productos = productoRepository.findAll()
                .stream()
                .filter(p -> p.getNombre().equalsIgnoreCase("Audífonos A9 Plus") ||
                        p.getNombre().equalsIgnoreCase("Cargador Smartilike 67W") ||
                        p.getNombre().equalsIgnoreCase("Airpods Pro"))
                .toList();

        List<Categoria> categorias = categoriaRepository.findAll();

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);
        model.addAttribute("contacto", new Contacto());

        return "productos";
    }

    @GetMapping("/productos/categoria/{id}")
    public String productosPorCategoria(@PathVariable Long id, Model model) {
        List<Producto> productos = productoRepository.findByCategoriaId(id);
        List<Categoria> categorias = categoriaRepository.findAll();

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);
        model.addAttribute("contacto", new Contacto());

        return "productos";
    }

    @GetMapping("/todos-los-productos")
    public String verTodosLosProductos(Model model) {
        List<Producto> productos = productoRepository.findAll();
        List<Categoria> categorias = categoriaRepository.findAll();

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);

        return "todos-los-productos";
    }

    @GetMapping("/todos-los-productos/categoria/{id}")
    public String verProductosPorCategoria(@PathVariable Long id, Model model) {
        List<Producto> productos = productoRepository.findByCategoriaId(id);
        List<Categoria> categorias = categoriaRepository.findAll();

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);

        return "todos-los-productos";
    }
}
