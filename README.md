# FANTEC - Sistema Web de Venta de Tecnología

Este proyecto es una aplicación web desarrollada con **Spring Boot**, **Thymeleaf**, **MySQL**, y **Spring Security** para gestionar productos tecnológicos y usuarios con distintos roles de acceso.

## 🔐 Seguridad del Sistema

El sistema utiliza **Spring Security** para la autenticación y autorización de usuarios:

- Acceso público a rutas como `/productos`, `/contacto`, `/enviar-mensaje`.
- Rutas protegidas bajo `/admin/**` accesibles solo para usuarios con rol `ADMIN`.
- Formulario de login y logout seguro.
- Protección contra ataques CSRF habilitada.

### 🛡️ Configuración de Seguridad

Se define en `SecurityConfig.java` usando un `SecurityFilterChain`:

```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/", "/productos", "/contacto", "/enviar-mensaje", "/css/**", "/img/**").permitAll()
    .requestMatchers("/admin/**").hasRole("ADMIN")
    .anyRequest().authenticated()
)
.formLogin(form -> form
    .loginPage("/login").permitAll()
)
.logout(logout -> logout
    .logoutUrl("/logout")
    .logoutSuccessUrl("/productos")
)
.csrf(csrf -> csrf.enable());
```

## 📁 Estructura del Proyecto

La estructura del proyecto sigue un diseño modular por capas, agrupando componentes por responsabilidades:

```
src/main/java/com/example/demo/
├── controller/              # Controladores para manejar las rutas y vistas
│   ├── AdminController
│   ├── CategoriaController
│   ├── ContactoAdminController
│   ├── ContactoController
│   ├── FormAuthController
│   ├── LoginController
│   ├── ProductoController
│   ├── TestController
│   └── UsuarioController
│
├── dto/                     # Objetos de transferencia de datos
│   └── AuthRequest.java
│
├── model/                   # Entidades del sistema (mapeadas a la base de datos)
│   ├── Categoria.java
│   ├── Contacto.java
│   ├── Producto.java
│   ├── Rol.java
│   ├── RolNombre.java
│   └── Usuario.java
│
├── repository/              # Interfaces para acceso a datos con Spring Data JPA
│   ├── CategoriaRepository.java
│   ├── ContactoRepository.java
│   ├── ProductoRepository.java
│   ├── RolRepository.java
│   └── UsuarioRepository.java
│
├── security/                # Configuración de seguridad
│   ├── CustomAuthenticationEntryPoint.java
│   ├── CustomSuccessHandler.java
│   ├── NoCacheFilter.java
│   ├── SecurityConfig.java
│   └── UsuarioService.java
│
├── DataInitializer.java     # Carga inicial de datos al iniciar el proyecto
└── DemoApplication.java     # Clase principal del proyecto (Spring Boot)

├── templates/
│   ├── productos.html
│   ├── mensaje-enviado.html
│   └── admin/...
└── static/
    ├── css/
    ├── js/
    └── img/
```

## 📦 Base de Datos

- MySQL
- Entidades están anotadas con JPA y validadas con `jakarta.validation`.

## 🛠️ Cómo ejecutar

1. Clonar el Proyecto
    ```bash
    https://github.com/fyrkcz0311/Fantec.git
    ```
2. Crear la base de datos `fantec` en MySQL
3. Configurar el `application.properties`:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/fantec
    spring.datasource.username=root
    spring.datasource.password=tu_clave
    ```
4. Ejecutar el proyecto desde tu IDE o con:
    ```bash
    ./mvnw spring-boot:run
    ```
5. Acceder desde el navegador a:
    ```bash
    http://localhost:8080
    ```
   ## 🔒 Explicación Adicional de Seguridad

- `SecurityConfig.java`: Configura las rutas públicas (`/login`, `/form-register`, `/css/**`, etc.) y restringe las rutas privadas por roles (`/admin/**` requiere ADMIN).
- `UsuarioService.java`: Carga usuarios desde la base de datos implementando `UserDetailsService`.
- `CustomSuccessHandler.java`: Redirige dinámicamente a los usuarios según su rol.
- `NoCacheFilter.java`: Previene el almacenamiento en caché de páginas tras cerrar sesión.
- `CustomAuthenticationEntryPoint.java`: Personaliza las respuestas de error para accesos no autorizados.

## ✅ Validaciones de Seguridad Implementadas

- Protección contra CSRF.
- Validación en todos los Formularios usando `@Valid`
- Contraseñas encriptadas con BCrypt en `UsuarioController`.

---



## 💡 Funcionalidades

- Registro y login de usuarios
- Panel de administrador (gestión de productos, usuarios, categorias y leer mensajes de contacto)
- Contacto con validación y persistencia
- Logout seguro
- Interfaz responsiva con Bootstrap

## 📬 Contacto

Formulario accesible en `/productos#seccion-contacto`. Los datos ingresados se validan y se almacenan en la base de datos.

## 🧾 Créditos

Desarrollado por: **[Fernando Castro]**  
Frameworks usados: Spring Boot, Spring Security, Thymeleaf, Bootstrap 5

---



