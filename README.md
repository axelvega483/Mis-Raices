# Vivero Mis Raíces 🌱

**Vivero Mis Raíces** es una aplicación móvil diseñada para facilitar la compra de plantas de manera rápida, cómoda y desde cualquier lugar. A través de una interfaz amigable, los usuarios pueden explorar una amplia variedad de especies, conocer sus cuidados, agregar sus favoritas al carrito y realizar sus pedidos directamente desde la app.

> Esta nueva versión incorpora soporte **offline** con SQLite, **geolocalización para direcciones de envío**, y una sección personalizada de **Mis Plantas**.

---

## 📱 Características de la Aplicación

- ✅ Registro de usuario con activación por correo electrónico.
- ✅ Recuperación de contraseña con token seguro.
- ✅ Visualización y búsqueda de productos por categoría o nombre.
- ✅ Compra directa desde el catálogo.
- ✅ Generación automática de facturas en PDF.
- ✅ Sesión persistente usando `SharedPreferences`.
- ✅ Visualización de plantas compradas incluso sin conexión (`SQLite`).
- ✅ Geolocalización para establecer direcciones desde el mapa.
- ✅ Cuidados, curiosidades y video por cada planta comprada.

---

## 🛠️ Tecnologías Utilizadas

### Front-end (Aplicación Móvil)
- **Java** – Lenguaje principal para el desarrollo Android.
- **XML** – Para interfaces gráficas.
- **Android SDK** – Librerías nativas de Android.
- **Material Design** – Diseño UI moderno y accesible.
- **SQLite** – Base de datos local para uso offline.
- **SharedPreferences** – Para almacenamiento ligero y persistencia de sesión.
- **Google Maps API** – Para selección de direcciones.

### Back-end (Servidor y API)
- **Java** – Lenguaje del backend.
- **Spring Boot** – Framework REST robusto y escalable.
- **MySQL** – Base de datos relacional para usuarios, productos y transacciones.

---

## 📝 Requerimientos Funcionales

### 🔐 Autenticación y Sesión
1. **Creación de Usuario**
   - Registro mediante formulario.
2. **Activación de Cuenta**
   - Envío de código por correo (válido 24 horas).
3. **Recuperación de Contraseña**
   - Envío de enlace por correo con token válido 1 hora.
4. **Inicio de Sesión**
   - Inicio de sesión con credenciales válidas.
   - Guardado del `userId` y ruta de foto en `SharedPreferences`.
5. **Cierre de Sesión**
   - Eliminación segura de datos en `SharedPreferences`.

### 🛍️ Funcionalidad de Compra
6. **Visualización de Productos**
   - Catálogo general, filtros por categoría y búsqueda por palabra clave.
7. **Detalle de Producto**
   - Imagen, descripción, precio y stock disponible.
8. **Carrito de Compras**
   - Agregar productos, modificar cantidades y elegir tarjeta de pago.
9. **Realización de Compra**
   - Validación de tarjeta y confirmación de pedido.
10. **Factura de Compra**
    - Envío automático por correo en formato PDF.
11. **Historial de Pedidos**
    - Visualización de compras previas realizadas por el usuario.

### 🌿 Funcionalidades Extendidas
12. **Mis Plantas (Offline)**
   - Visualización local de plantas compradas con:
     - Nombre, imagen, cuidados, curiosidades y video.
   - Guardadas en **SQLite**, disponibles sin conexión.
13. **Geolocalización de Dirección**
   - Selección de dirección desde mapa.
   - Guardado de latitud, longitud y descripción en SQLite.

### 🧾 Gestión de Usuario
14. **Gestión de Foto de Perfil**
   - Subida de imagen local (`nombreIDusuario.jpg`).
   - Recuperación usando `SharedPreferences`.
15. **Gestión de Tarjetas de Pago**
   - Alta, edición y eliminación de tarjetas.
16. **Edición de Perfil**
   - Modificación de nombre, correo y foto de perfil.

---

## ⚙️ Requerimientos No Funcionales

- **Seguridad**:
  - Token de recuperación válido por 1 hora.
  - Código de activación válido por 24 horas.
- **Usabilidad**:
  - Interfaz fluida y accesible, basada en **Material Design**.
- **Compatibilidad**:
  - Compatible con **Android 7.0 (API 24)** en adelante.
  - Optimizado para **Android 12 (API 35)**.
- **Offline Ready**:
  - Plantas compradas y direcciones disponibles sin internet.

---

## 🎨 Vistas - Figma

- [Figma – Diseño UI](https://www.figma.com/design/c97t5LpRcyRU7N51djfRCW/Mis-Ra%C3%ADces?node-id=0-1&p=f&t=DWdrgxAe8x2a3Abx-0)

---

## 📄 Documentación Técnica

- [Documento funcional](https://docs.google.com/document/d/1TEudDQLZoD6d95UCrFeWbPsadMVfd9mF5-Cw99NLrps/edit?usp=sharing)
- [Swagger UI](http://localhost:8080/swagger-ui/index.html)

---

> 🌱 Vivero Mis Raíces apuesta por una experiencia de compra moderna, intuitiva y conectada con la naturaleza. Ahora con soporte offline y dirección geolocalizada para un servicio aún más personalizado.


