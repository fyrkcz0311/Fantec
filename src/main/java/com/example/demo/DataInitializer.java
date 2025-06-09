package com.example.demo;

import com.example.demo.model.Categoria;
import com.example.demo.model.Producto;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        return args -> {
            if (productoRepository.count() == 0) {
                // Crear categorías
                Categoria audifonos = new Categoria(null, "Audífonos", null);
                Categoria cargadores = new Categoria(null, "Cargadores", null);

                categoriaRepository.saveAll(List.of(audifonos, cargadores));


                productoRepository.saveAll(List.of(
                        new Producto(null, "Audífonos A9 Plus", new BigDecimal("45.00"),
                                "/img/A9Plus_01/A9Plus_01.avif,/img/A9Plus_01/A9Plus_02.avif",
                                "Bluetooth 5.0, Cancelación activa de ruido (ANC), Batería de 20h, Resistencia al agua IPX5",
                                audifonos),

                        new Producto(null, "Cargador Smartilike 67W", new BigDecimal("25.00"),
                                "/img/Carg. Smartilike_67W/Carga.Smartilike_67W_01.avif,/img/Carg. Smartilike_67W/Carg. Smartilike_67W_02.avif,/img/Carg. Smartilike_67W/Carg. Smartilike_67W_03.avif",
                                "Carga rápida 67W, USB-C, Protección sobrecalentamiento, Compacto",
                                cargadores),

                        new Producto(null, "Airpods Pro", new BigDecimal("99.00"),
                                "/img/Airpods_Pro2/Airpods_Pro2_01.jpg,/img/Airpods_Pro2/Airpods_Pro2_02.jpg,/img/Airpods_Pro2/Airpods_Pro2_03.jpg,/img/Airpods_Pro2/Airpods_Pro2_04.jpg",
                                "Cancelación activa de ruido, Sonido envolvente, Carga rápida, Resistente al agua",
                                audifonos),

                        new Producto(null, "Cargador Smartilike 35W", new BigDecimal("40.00"),
                                "/img/Carg. Smartilike_35W/Carg. Smartilike_35W_01.avif,/img/Carg. Smartilike_35W/Carg. Smartilike_35W_02.avif,/img/Carg. Smartilike_35W/Carg. Smartilike_35W_03.avif",
                                "Carga rápida 35W, USB-C y USB-A, Protección sobrecarga, Eficiente",
                                cargadores),

                        new Producto(null, "Cargador Smartilike 120W", new BigDecimal("60.00"),
                                "/img/Carg. Smartilike_120W/Carg. Smartilike_120W_01.avif,/img/Carg. Smartilike_120W/Carg. Smartilike_120W_02.avif",
                                "Carga rápida 120W, USB-C y USB-A, Protección avanzada, Compacto",
                                cargadores),

                        new Producto(null, "Cargador Smartilike 3mAh", new BigDecimal("85.00"),
                                "/img/Carg. Smartilike_3mAh/Carg. Smartilike_3mAh_01.avif,/img/Carg. Smartilike_3mAh/Carg. Smartilike_3mAh_02.avif,/img/Carg. Smartilike_3mAh/Carg. Smartilike_3mAh_03.avif",
                                "3mAh, Carga rápida, Protección térmica, Portátil",
                                cargadores),

                        new Producto(null, "Audífonos Zero SE60", new BigDecimal("45.00"),
                                "/img/Zero_SE60/Zero_SE60_01.avif,/img/Zero_SE60/Zero_SE60_02.avif",
                                "Bluetooth 5.0, Cancelación activa de ruido, 25h batería, IPX6",
                                audifonos),

                        new Producto(null, "Audífonos KZ EDX Pro", new BigDecimal("25.00"),
                                "/img/KZ_EDX_Pro/KZ_EDX-Pro_01.avif,/img/KZ_EDX_Pro/KZ_EDX-Pro_02.avif,/img/KZ_EDX_Pro/KZ_EDX-Pro_03.avif",
                                "Conector 3.5mm, Sonido HD, Graves potentes, Diseño ergonómico",
                                audifonos)
                ));
            }
        };
    }
}
