package com.example.demo.controller;

import com.example.demo.model.Categoria;
import com.example.demo.model.Contacto;
import com.example.demo.model.Producto;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/admin/productos")
    public String adminProductos(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "admin-productos";
    }

    @GetMapping("/admin/productos/nuevo")
    public String nuevoProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "form-producto";
    }

    @PostMapping("/admin/productos/guardar")
    public String guardarProducto(
            @ModelAttribute Producto producto,
            @RequestParam("imagenesFiles") MultipartFile[] imagenesFiles,
            @RequestParam(value = "imagenesAEliminar", required = false) List<String> imagenesAEliminar
    ) throws IOException {

        Long categoriaId = producto.getCategoria().getId();
        Categoria categoria = categoriaRepository.findById(categoriaId).orElseThrow();
        producto.setCategoria(categoria);

        List<String> rutasFinales = new ArrayList<>();

        // Si estamos editando, filtramos imágenes previas
        if (producto.getId() != null) {
            Producto existente = productoRepository.findById(producto.getId()).orElseThrow();
            String imagenesAnteriores = existente.getImagenes();

            if (imagenesAnteriores != null && !imagenesAnteriores.isBlank()) {
                for (String ruta : imagenesAnteriores.split(",")) {
                    if (imagenesAEliminar == null || !imagenesAEliminar.contains(ruta)) {
                        rutasFinales.add(ruta);
                    } else {
                        // Eliminar archivo físico
                        Path rutaFisica = Paths.get("src/main/resources/static" + ruta);
                        Files.deleteIfExists(rutaFisica);

                        Path rutaFisicaTarget = Paths.get("target/classes/static" + ruta);
                        Files.deleteIfExists(rutaFisicaTarget);
                    }
                }
            }
        }

        // Guardar nuevas imágenes
        if (imagenesFiles != null) {
            for (MultipartFile imagen : imagenesFiles) {
                if (!imagen.isEmpty()) {
                    String nombreArchivo = UUID.randomUUID() + "_" + imagen.getOriginalFilename();

                    // Guardar en src
                    Path rutaSrc = Paths.get("src/main/resources/static/img/" + nombreArchivo);
                    Files.copy(imagen.getInputStream(), rutaSrc, StandardCopyOption.REPLACE_EXISTING);

                    // Guardar en target (para que Spring la sirva en caliente)
                    Path rutaTarget = Paths.get("target/classes/static/img/" + nombreArchivo);
                    Files.copy(imagen.getInputStream(), rutaTarget, StandardCopyOption.REPLACE_EXISTING);

                    rutasFinales.add("/img/" + nombreArchivo);
                }
            }
        }

        // Guardar rutas en el producto
        producto.setImagenes(String.join(",", rutasFinales));
        productoRepository.save(producto);

        return "redirect:/admin/productos";
    }

    @GetMapping("/admin/productos/editar/{id}")
    public String editarProducto(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoRepository.findById(id).orElseThrow());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "form-producto";
    }

    @GetMapping("/admin/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoRepository.deleteById(id);
        return "redirect:/admin/productos";
    }
}
