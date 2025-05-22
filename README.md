# Vivero Mis Raíces 🌱

Vivero Mis Raíces es una aplicación móvil diseñada para facilitar la compra de plantas de manera rápida, cómoda y desde cualquier lugar. A través de una interfaz amigable, los usuarios pueden explorar una amplia variedad de especies disponibles en el vivero, obtener información sobre cada planta y realizar sus pedidos directamente desde la app. La aplicación busca brindar una experiencia de compra moderna para los amantes de las plantas.

## Características de la Aplicación

- **Registro con activación por correo**: al crear una cuenta, se envía un código de activación al correo electrónico del usuario para validar el registro.
- **Recuperación de contraseña**: si el usuario olvida su contraseña, puede solicitar un enlace para restaurarla. Se le envía un token válido por una hora a su correo.
- **Búsqueda de plantas**: se puede buscar plantas por nombre o por categoría, permitiendo encontrar fácilmente lo que el usuario necesita.
- **Factura en PDF**: al realizar una compra, el usuario recibe un correo con la factura en formato PDF.


## 🛠️ Tecnologías Utilizadas

### Front-end (Aplicación Móvil)
- **Java**: Lenguaje principal para el desarrollo de la app Android.
- **XML**: Utilizado para definir las interfaces gráficas (layouts).
- **Android SDK**: Conjunto de herramientas para desarrollar aplicaciones en Android.
- **Material Design**: Sistema de diseño de Google utilizado para crear interfaces modernas, limpias e intuitivas.

### Back-end (Servidor y API)
- **Java**: También utilizado para desarrollar el servidor.
- **Spring Boot**: Framework que permite construir APIs REST de forma rápida y estructurada.
- **MySQL**: Base de datos relacional para almacenar la información de usuarios, productos y transacciones.

## 📝 Requerimientos Funcionales

1. **Creación de Usuario**:
   - El sistema debe permitir crear un nuevo usuario mediante un formulario de registro.

2. **Activación de Cuenta**:
   - El sistema debe permitir enviar un correo electrónico de activación de cuenta con un código único (con una duración de 24 horas).
   - El sistema debe permitir activar la cuenta si el código ingresado es correcto.

3. **Recuperación de Contraseña**:
   - El sistema debe permitir enviar un correo para la recuperación de la contraseña.
   - El sistema debe permitir restaurar la contraseña si el token enviado es válido (con una duración de 1 hora).

4. **Inicio de Sesión**:
   - El sistema debe permitir iniciar sesión con las credenciales del usuario.
   - El sistema debe guardar el ID del usuario y la ruta de la foto de perfil en **SharedPreferences** para facilitar la persistencia de la sesión.

5. **Visualización de Productos**:
   - El sistema debe permitir ver todos los productos disponibles en el catálogo.
   - El sistema debe permitir ver productos filtrados por categoría.
   - El sistema debe permitir buscar productos por nombre o coincidencia de palabra clave.

6. **Detalle de Producto**:
   - El sistema debe permitir ver el detalle completo de cada producto, incluyendo imagen, descripción, precio y disponibilidad.

7. **Carrito de Compras**:
   - El sistema debe permitir agregar productos al carrito de compras.
   - El sistema debe permitir incrementar o disminuir la cantidad de un producto dentro del carrito.
   - El sistema debe permitir elegir una tarjeta de pago ya guardada.

8. **Realización de Compra**:
   - El sistema debe permitir realizar una compra si se ha elegido una tarjeta válida.

9. **Historial de Pedidos**:
   - El sistema debe permitir ver los pedidos realizados por el usuario.

10. **Factura de Compra**:
    - El sistema debe enviar un correo electrónico con la factura en formato PDF de la compra realizada.

11. **Gestión de Foto de Perfil**:
    - El sistema debe permitir agregar una imagen de perfil en la cuenta del usuario.
    - La foto de perfil debe ser guardada en el almacenamiento del dispositivo con un nombre que combine el ID del usuario (ejemplo: `nombreIDusuario.jpg`).
    - El sistema debe recuperar la foto de perfil del usuario según su ID almacenado en **SharedPreferences** al iniciar sesión.

12. **Gestión de Tarjetas de Pago**:
    - El sistema debe permitir cargar una nueva tarjeta de pago.
    - El sistema debe permitir editar o eliminar tarjetas de pago guardadas.

13. **Edición de Perfil**:
    - El sistema debe permitir editar los datos del perfil del usuario, como nombre, correo electrónico, y foto de perfil.

14. **Cerrar Sesión**:
    - El sistema debe permitir cerrar sesión de manera segura y eliminar la sesión activa de **SharedPreferences**.

## ⚙️ Requerimientos No Funcionales

- **Seguridad**:
  - El token de recuperación de contraseña tendrá una validez de una hora.
  - El código de activación tendrá una validez de 24 horas.

- **Usabilidad**:
  - La interfaz debe ser intuitiva y seguir los lineamientos de **Material Design**.

- **Compatibilidad**:
  - La aplicación es compatible con dispositivos que ejecuten **Android 7.0 (Nougat)** (API 24) o versiones superiores.
  - Se optimiza para dispositivos con **Android 12 (API 35)** y versiones superiores.
  - La aplicación debe ser capaz de funcionar correctamente en dispositivos con pantalla táctil y capacidades estándar de Android.
 
  ## Vistas-Figman
  [Ver diseño](https://www.figma.com/design/c97t5LpRcyRU7N51djfRCW/Mis-Ra%C3%ADces?node-id=0-1&p=f&t=DWdrgxAe8x2a3Abx-0)

  ## Documentación
  [Ver documento](https://docs.google.com/document/d/1TEudDQLZoD6d95UCrFeWbPsadMVfd9mF5-Cw99NLrps/edit?usp=sharing)

  ## Swagger
  [Ver documento](http://localhost:8080/swagger-ui/index.html)
