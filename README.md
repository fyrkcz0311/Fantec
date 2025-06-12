# Proyecto Web Informativo – Fantec

Este proyecto es una página web informativa y administrativa para la tienda de tecnología **FANTEC**, desarrollada con **Spring Boot**, **Thymeleaf**, **MySQL** y **Bootstrap**. Permite mostrar y gestionar productos, filtrarlos por categoría, subir imágenes múltiples por producto y gestionar mensajes de contacto, todo con validaciones completas.

---

## 🎯 Objetivo

- Mostrar productos destacados y catálogo completo.
- Permitir filtrado por categoría.
- Registrar mensajes de contacto con validación de campos.
- Administrar productos (crear, editar, eliminar) con imágenes múltiples.
- Validar campos del formulario de productos y contacto.
- Mantener una estructura clara basada en el patrón MVC.

---

## 🧱 Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **Thymeleaf**
- **Bootstrap 5**
- **MySQL**
- **Lombok**
- **Jakarta Bean Validation**

---

## 🔗 Rutas de la aplicación

| Ruta                                      | Función                                                                 |
|-------------------------------------------|-------------------------------------------------------------------------|
| `/productos`                              | Página de inicio con 3 productos destacados                            |
| `/todos-los-productos`                    | Catálogo completo con carrusel de imágenes y botón "Comprar"           |
| `/todos-los-productos/categoria/{id}`     | Filtra productos por ID de categoría                                   |
| `/admin/productos`                        | Vista de administración con tarjetas, botones "Editar" y "Eliminar"    |
| `/admin/productos/nuevo`                  | Formulario para registrar nuevos productos                             |
| `/admin/productos/editar/{id}`            | Formulario para editar un producto existente                           |
| `/admin/productos/eliminar/{id}`          | Elimina un producto y sus imágenes del sistema                         |
| `/enviar-mensaje`                         | Procesa el formulario de contacto con validaciones                     |

---

## 🧩 Estructura de entidades y relaciones

- `Producto`:
    - Atributos: `id`, `nombre`, `precio`, `imagenes`, `caracteristicas`.
    - Relación: `@ManyToOne` con `Categoria`.
    - Validaciones: `@NotBlank`, `@NotNull`, validación de imágenes y características obligatorias.

- `Categoria`:
    - Atributos: `id`, `nombre`.
    - Relación: `@OneToMany` con `Producto`.

- `Contacto`:
    - Atributos: `email`, `name`, `phone`, `message`.
    - Validaciones: `@Email`, `@NotBlank`, `@Pattern`, `@Size`.

---

## ✅ Validaciones implementadas

- Todos los formularios incluyen validaciones del lado servidor con `@Valid`.
- El formulario de productos valida:
    - Nombre y precio obligatorios.
    - Al menos una imagen subida.
    - Selección de categoría.
- El formulario de contacto valida:
    - Correo electrónico válido.
    - Nombre, teléfono y mensaje obligatorios y bien formateados.
- Imágenes marcadas para eliminar solo se borran si la validación es exitosa.

---

## 📷 Capturas requeridas para entrega

1. Página `/productos` con productos destacados.
2. Página `/todos-los-productos` mostrando todos los productos.
3. Filtro de categorías funcionando correctamente.
4. Modal o detalle del producto visible.
5. Formulario de contacto mostrando errores si está vacío.
6. Vista `mensaje-enviado.html` con nombre del remitente.
7. Visualización de la base de datos con registros en `producto` y `contacto`.
8. Vista de administración mostrando tarjetas con imagen, botones y edición funcional.

---

## ⚙ Cómo ejecutar el proyecto

1. Crea la base de datos:
   ```sql
   CREATE DATABASE Fantec;
   ```

2. Configura tu archivo `application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/Fantec
   spring.datasource.username=root
   spring.datasource.password=TU_CONTRASEÑA
   spring.jpa.hibernate.ddl-auto=update
   spring.servlet.multipart.max-file-size=5MB
   spring.servlet.multipart.max-request-size=20MB
   ```

3. Ejecuta desde tu IDE o terminal:
   ```bash
   mvn spring-boot:run
   ```

4. Abre en el navegador:
   ```
   http://localhost:8080/productos
   ```

---

## 👨‍💻 Autor

- **Fernando Daniel Castro Zelada**
