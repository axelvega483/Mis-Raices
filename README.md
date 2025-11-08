<h1 align="center">
  🌱 Vivero Mis Raíces
</h1>

<p align="center">
  <b>Aplicación móvil para compra de plantas con experiencia offline</b>
  <br>
  <em>Desarrollado con Java • Android SDK • Spring Boot</em>
</p>

<p align="center">
  <a href="https://www.figma.com/design/c97t5LpRcyRU7N51djfRCW/Mis-Ra%C3%ADces">
    <img src="https://img.shields.io/badge/Diseño-Figma-purple?style=for-the-badge&logo=figma" alt="Figma Design">
  </a>
  <a href="https://docs.google.com/document/d/1TEudDQLZoD6d95UCrFeWbPsadMVfd9mF5-Cw99NLrps/edit">
    <img src="https://img.shields.io/badge/Documentación-Completa-blue?style=for-the-badge&logo=googledocs" alt="Documentación">
  </a>
  <img src="https://img.shields.io/badge/Java-Android-blue?style=for-the-badge&logo=java" alt="Java">
  <img src="https://img.shields.io/badge/Spring_Boot-3.4.5-brightgreen?style=for-the-badge&logo=springboot" alt="Spring Boot">
</p>

---

## 🌟 Características de la Aplicación

<div align="center">

| Característica | Icono | Descripción |
|----------------|-------|-------------|
| **Registro Seguro** | ✅ | Activación por correo electrónico con código de 24h |
| **Recuperación de Contraseña** | 🔐 | Token seguro válido por 1 hora |
| **Catálogo Inteligente** | 🔍 | Búsqueda por categoría y nombre |
| **Compra Directa** | 🛒 | Proceso de compra optimizado desde el catálogo |
| **Facturación Automática** | 🧾 | Generación y envío de PDF por correo |
| **Sesión Persistente** | 💾 | Almacenamiento seguro con SharedPreferences |
| **Modo Offline** | 📱 | Plantas compradas disponibles sin conexión (SQLite) |
| **Geolocalización** | 🗺️ | Selección de direcciones desde mapa integrado |
| **Guías de Cuidados** | 🌿 | Información completa por planta: cuidados, curiosidades y videos |

</div>

---

## 🛠️ Tecnologías Utilizadas

<div align="center">

### Front-end (Aplicación Móvil)

| Tecnología | Icono | Uso |
|------------|-------|-----|
| **Java** | <img src="https://img.shields.io/badge/Java-Android-blue?style=flat&logo=java" alt="Java"> | Lenguaje principal para desarrollo Android |
| **XML** | <img src="https://img.shields.io/badge/XML-Layouts-orange?style=flat&logo=xml" alt="XML"> | Interfaces gráficas y layouts |
| **Android SDK** | <img src="https://img.shields.io/badge/Android_SDK-Native-brightgreen?style=flat&logo=android" alt="Android SDK"> | Librerías nativas de Android |
| **Material Design** | <img src="https://img.shields.io/badge/Material_Design-UI/UX-purple?style=flat&logo=material-design" alt="Material Design"> | Diseño moderno y accesible |
| **SQLite** | <img src="https://img.shields.io/badge/SQLite-Offline-lightgrey?style=flat&logo=sqlite" alt="SQLite"> | Base de datos local para modo offline |
| **SharedPreferences** | <img src="https://img.shields.io/badge/SharedPreferences-Sesión-yellow?style=flat" alt="SharedPreferences"> | Persistencia de sesión y datos ligeros |
| **Google Maps API** | <img src="https://img.shields.io/badge/Google_Maps-Geolocalización-red?style=flat&logo=google-maps" alt="Google Maps"> | Selección de direcciones desde mapa |

### Back-end (Servidor y API)

| Tecnología | Icono | Uso |
|------------|-------|-----|
| **Java** | <img src="https://img.shields.io/badge/Java-Spring_Boot-blue?style=flat&logo=java" alt="Java"> | Lenguaje del backend |
| **Spring Boot** | <img src="https://img.shields.io/badge/Spring_Boot-REST_API-brightgreen?style=flat&logo=springboot" alt="Spring Boot"> | Framework REST robusto y escalable |
| **MySQL** | <img src="https://img.shields.io/badge/MySQL-Database-blue?style=flat&logo=mysql" alt="MySQL"> | Base de datos relacional para usuarios y transacciones |

</div>

---

## 📱 Módulos de la Aplicación

<div align="center">

| Módulo | Icono | Descripción | Funcionalidades |
|--------|-------|-------------|-----------------|
| **Autenticación** | 🔐 | Gestión segura de usuarios | Registro • Activación • Login • Recuperación |
| **Catálogo** | 🛍️ | Exploración de productos | Búsqueda • Filtros • Detalles • Stock |
| **Carrito** | 🛒 | Gestión de compras | Agregar • Modificar • Tarjetas • Validación |
| **Pedidos** | 📦 | Historial y seguimiento | Compras previas • Estados • Facturas PDF |
| **Mis Plantas** | 🌿 | Gestión offline | Cuidados • Curiosidades • Videos • Sin conexión |
| **Perfil** | 👤 | Gestión de usuario | Foto • Datos • Tarjetas • Direcciones |
| **Geolocalización** | 🗺️ | Direcciones inteligentes | Mapas • GPS • Guardado local |

</div>

---

## 📝 Requerimientos Funcionales

<div align="center">

| Módulo | Funcionalidades | Estado |
|--------|-----------------|--------|
| **🔐 Autenticación** | Registro con email • Activación 24h • Recuperación 1h • Sesión persistente | ✅ Implementado |
| **🛍️ Catálogo** | Búsqueda avanzada • Filtros por categoría • Detalles completos • Stock en tiempo real | ✅ Implementado |
| **🛒 Compra** | Carrito dinámico • Múltiples tarjetas • Validación • Confirmación • PDF automático | ✅ Implementado |
| **🌿 Mis Plantas** | Almacenamiento offline • Guías de cuidados • Contenido multimedia • Acceso sin internet | ✅ Implementado |
| **🗺️ Geolocalización** | Selección en mapa • Coordenadas GPS • Almacenamiento local • Direcciones inteligentes | ✅ Implementado |
| **👤 Perfil** | Gestión de foto • Edición de datos • Tarjetas de pago • Historial completo | ✅ Implementado |

</div>

---

## ⚙️ Requerimientos No Funcionales

<div align="center">

| Categoría | Especificación | Estado |
|-----------|----------------|--------|
| **🛡️ Seguridad** | Tokens temporales • Validación de tarjetas • Sesiones seguras | ✅ Implementado |
| **📱 Compatibilidad** | Android 7.0+ (API 24) • Optimizado para Android 12 (API 35) | ✅ Implementado |
| **⚡ Performance** | Interfaz fluida • Carga optimizada • Consultas eficientes | ✅ Implementado |
| **📶 Offline** | SQLite robusto • Sincronización inteligente • Datos esenciales sin conexión | ✅ Implementado |
| **🎨 UX/UI** | Material Design • Navegación intuitiva • Experiencia de usuario premium | ✅ Implementado |

</div>

---

## 📄 Documentación Técnica

<div align="center">

| Recurso | Enlace | Descripción |
|---------|--------|-------------|
| **🎨 Diseño UI/UX** | [Figma](https://www.figma.com/design/c97t5LpRcyRU7N51djfRCW/Mis-Ra%C3%ADces) | Diseño completo de interfaces y experiencia de usuario |
| **📋 Especificaciones** | [Documento Funcional](https://docs.google.com/document/d/1TEudDQLZoD6d95UCrFeWbPsadMVfd9mF5-Cw99NLrps/edit) | Documentación técnica detallada y requerimientos |
| **🔌 API Documentation** | [Swagger UI](http://localhost:8080/swagger-ui/index.html) | Documentación interactiva de endpoints API |

</div>

---

<div align="center">

## 🌱 ¿Listo para Cultivar tu Jardín?

[**🎨 Ver Diseño en Figma**](https://www.figma.com/design/c97t5LpRcyRU7N51djfRCW/Mis-Ra%C3%ADces) • 
[**📖 Documentación Completa**](https://docs.google.com/document/d/1TEudDQLZoD6d95UCrFeWbPsadMVfd9mF5-Cw99NLrps/edit)

**⭐ ¡Si te gusta nuestro proyecto, dale una estrella al repositorio!**

---
*Desarrollado con ❤️ y Java para amantes de las plantas*

</div>
