package com.example.demo.controller;

import com.example.demo.model.Categoria;
import com.example.demo.model.Contacto;
import com.example.demo.model.Producto;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
            @Valid @ModelAttribute Producto producto,
            BindingResult bindingResult,
            @RequestParam("imagenesFiles") MultipartFile[] imagenesFiles,
            @RequestParam(value = "imagenesAEliminar", required = false) List<String> imagenesAEliminar,
            Model model
    ) throws IOException {

        List<String> imagenesOriginales = new ArrayList<>();
        List<String> imagenesParaEliminar = new ArrayList<>();
        List<String> rutasFinales = new ArrayList<>();

        // Recuperar imágenes anteriores si está editando
        if (producto.getId() != null) {
            Producto existente = productoRepository.findById(producto.getId()).orElseThrow();
            String imagenesAnteriores = existente.getImagenes();

            if (imagenesAnteriores != null && !imagenesAnteriores.isBlank()) {
                imagenesOriginales = new ArrayList<>(List.of(imagenesAnteriores.split(",")));

                for (String ruta : imagenesOriginales) {
                    if (imagenesAEliminar == null || !imagenesAEliminar.contains(ruta)) {
                        rutasFinales.add(ruta); // conservar
                    } else {
                        imagenesParaEliminar.add(ruta); // marcar para borrar después
                    }
                }
            }
        }

        boolean noHayImagenesNuevas = imagenesFiles == null || imagenesFiles.length == 0 ||
                (imagenesFiles.length == 1 && imagenesFiles[0].isEmpty());

        if (rutasFinales.isEmpty() && noHayImagenesNuevas) {
            bindingResult.rejectValue("imagenes", "error.producto", "Debe subir al menos una imagen.");
        }

        if (producto.getCaracteristicas() == null || producto.getCaracteristicas().trim().isEmpty()) {
            bindingResult.rejectValue("caracteristicas", "error.producto", "Debe ingresar al menos una característica.");
        }

        if (bindingResult.hasErrors()) {
            producto.setImagenes(String.join(",", imagenesOriginales)); // restaurar
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "form-producto";
        }

        if (bindingResult.hasErrors()) {
            producto.setImagenes(String.join(",", imagenesOriginales)); // restaurar
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "form-producto";
        }

        for (String ruta : imagenesParaEliminar) {
            Path rutaFisica = Paths.get("src/main/resources/static" + ruta);
            Files.deleteIfExists(rutaFisica);

            Path rutaFisicaTarget = Paths.get("target/classes/static" + ruta);
            Files.deleteIfExists(rutaFisicaTarget);
        }

        if (imagenesFiles != null) {
            for (MultipartFile imagen : imagenesFiles) {
                if (!imagen.isEmpty()) {
                    String nombreArchivo = UUID.randomUUID() + "_" + imagen.getOriginalFilename();

                    Path rutaSrc = Paths.get("src/main/resources/static/img/" + nombreArchivo);
                    Files.copy(imagen.getInputStream(), rutaSrc, StandardCopyOption.REPLACE_EXISTING);

                    Path rutaTarget = Paths.get("target/classes/static/img/" + nombreArchivo);
                    Files.copy(imagen.getInputStream(), rutaTarget, StandardCopyOption.REPLACE_EXISTING);

                    rutasFinales.add("/img/" + nombreArchivo);
                }
            }
        }

        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getId()).orElseThrow();
        producto.setCategoria(categoria);
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
