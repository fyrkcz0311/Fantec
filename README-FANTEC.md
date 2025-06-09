# Proyecto Web Informativo – Fantec

Este proyecto es una página web informativa para la tienda de tecnología **FANTEC**, desarrollada con **Spring Boot**, **Thymeleaf**, **MySQL** y **Bootstrap**. Incluye presentación de productos, filtrado por categoría y formulario de contacto funcional.

---

## 🎯 Objetivo

- Mostrar productos destacados y catálogo completo.
- Permitir filtrado por categoría.
- Registrar mensajes de contacto usando formularios con validación.
- Mantener estructura limpia basada en el patrón MVC.

---

## 🧱 Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **Thymeleaf**
- **Bootstrap 5**
- **MySQL**
- **Lombok**

---

## 🔗 Rutas de la aplicación

| Ruta                                 | Función                                               |
|--------------------------------------|--------------------------------------------------------|
| `/productos`                         | Página de inicio con 3 productos destacados           |
| `/todos-los-productos`              | Catálogo completo de productos con filtro por categoría |
| `/todos-los-productos/categoria/{id}` | Filtra productos por ID de categoría                  |
| `/enviar-mensaje`                   | Procesa el formulario de contacto                     |

---

## 🧩 Estructura de entidades y relaciones

- `Producto`:
  - Tiene atributos como `nombre`, `precio`, `imagenes`, `caracteristicas`.
  - Se relaciona con `Categoria` mediante `@ManyToOne`.

- `Categoria`:
  - Contiene un `nombre` y una lista de productos (`@OneToMany`).

- `Contacto`:
  - Se utiliza para guardar mensajes enviados por usuarios desde un formulario.
  - Usa validaciones con `@Email`, `@NotBlank`, `@Pattern`.

---

## 📷 Capturas requeridas para entrega

Incluye en el documento entregado las siguientes capturas:

1. Página `/productos` con productos destacados.
2. Página `/todos-los-productos` mostrando todos los productos.
3. Filtro de categorías funcionando.
4. Modal de detalles del producto.
5. Formulario de contacto con validación activa (error si se deja vacío).
6. Página `mensaje-enviado.html` con nombre del usuario.
7. Visualización en base de datos (`producto` y `contacto` llenos).

---

## ⚙ Cómo ejecutar el proyecto

1. Configura tu base de datos MySQL:
   ```sql
   CREATE DATABASE Fantec;
   ```

2. Asegúrate de tener en `application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/Fantec
   spring.datasource.username=root
   spring.datasource.password=TU_CONTRASEÑA
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Ejecuta el proyecto desde tu IDE o terminal:

   ```bash
   mvn spring-boot:run
   ```

4. Accede desde el navegador a:
   ```
   http://localhost:8080/productos
   ```

---

## 👨‍💻 Autor

- **[Tu nombre completo]**
- Universidad/Curso
- Fecha de entrega: Junio 2025