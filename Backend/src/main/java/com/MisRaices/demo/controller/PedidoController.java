package com.MisRaices.demo.controller;

import com.MisRaices.demo.PDF.PdfGenerator;
import com.MisRaices.demo.entity.Pedido;
import com.MisRaices.demo.entity.PedidoDetalle;
import com.MisRaices.demo.entity.Producto;
import com.MisRaices.demo.entity.TarjetaCredito;
import com.MisRaices.demo.entity.Usuario;
import com.MisRaices.demo.service.EmailService;
import com.MisRaices.demo.service.PedidoService;
import com.MisRaices.demo.service.ProductoService;
import com.MisRaices.demo.service.TarjetaCreditoService;
import com.MisRaices.demo.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TarjetaCreditoService tarjetaCreditoService;

    @Autowired
    private EmailService emailService;

    private Map<String, Object> response;

    @Operation(summary = "Listar todos los pedidos", description = "Retorna una lista de todos los pedidos realizados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            response = new HashMap<>();
            return new ResponseEntity<>(pedidoService.listar(), HttpStatus.OK);
        } catch (Exception e) {
            response.put("error al listar los pedidos", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtener un pedido", description = "Devuelve un pedido específico por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public ResponseEntity<?> obtener(@Parameter(description = "ID del pedido a obtener", required = true) @PathVariable Integer id) {
        try {
            response = new HashMap<>();
            Pedido pedido = pedidoService.obtener(id).orElse(null);
            if (pedido != null) {
                return new ResponseEntity<>(pedido, HttpStatus.OK);
            } else {
                response.put("error", "pedido no existe");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Crear un nuevo pedido", description = "Crea un nuevo pedido con detalles de productos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o stock insuficiente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Pedido pedido) {
        try {
            response = new HashMap<>();

            Usuario cliente = usuarioService.obtener(pedido.getUsuario().getId()).orElse(null);
            if (cliente == null) {
                response.put("error", "Cliente no válido.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            pedido.setUsuario(cliente);
            List<PedidoDetalle> detalles = new ArrayList<>();
            double total = 0.0;

            for (PedidoDetalle detalle : pedido.getDetalle()) {
                Optional<Producto> productoOpt = productoService.obtener(detalle.getProducto().getId());
                if (!productoOpt.isPresent()) {
                    response.put("error", "Producto con ID " + detalle.getProducto().getId() + " no encontrado.");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }

                Producto producto = productoOpt.get();
                int cantidad = detalle.getCantidad();
                int nuevoStock = producto.getStock() - cantidad;

                if (nuevoStock < 0) {
                    response.put("error", "Stock insuficiente para el producto: " + producto.getNombre());
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

                PedidoDetalle nuevoDetalle = new PedidoDetalle();
                nuevoDetalle.setCantidad(cantidad);
                nuevoDetalle.setProducto(producto);
                nuevoDetalle.setPedido(pedido);

                detalles.add(nuevoDetalle);
                total += producto.getPrecio() * cantidad;
            }

            pedido.setDetalle(detalles);
            pedido.setTotal(total);
            pedido.setFechaPedido(LocalDateTime.now());
            pedido.setEstado("PENDIENTE");

            Pedido pedidoGuardado = pedidoService.guardar(pedido);

            response.put("mensaje", "Pedido creado exitosamente.");
            response.put("pedido", pedidoGuardado);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Actualizar un pedido", description = "Actualiza los detalles de un pedido existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("{id}")
    public ResponseEntity<?> actualizar(@RequestBody Pedido pedido,
            @PathVariable(required = true) @Parameter(description = "ID del pedido a actualizar", required = true) Integer id) {
        try {
            response = new HashMap<>();
            Pedido pedidoBD = pedidoService.obtener(id).orElse(null);
            if (pedidoBD == null) {
                response.put("error", "no se encontro pedido");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            actualizar(pedidoBD, pedido);
            return new ResponseEntity<>(pedidoService.guardar(pedidoBD), HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar un pedido", description = "Elimina un pedido por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido eliminado"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminar(@Parameter(description = "ID del pedido a eliminar", required = true) @PathVariable Integer id) {
        try {
            response = new HashMap<>();
            Pedido pe = pedidoService.obtener(id).orElse(null);
            if (pe != null) {
                pedidoService.eliminar(id);
                response.put("pedido", "elimindo");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("pedido no encontrado", pe);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "Finalizar una compra", description = "Finaliza el pedido, descuenta el saldo de la tarjeta, actualiza stock y envía factura por correo.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Compra finalizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Stock o saldo insuficiente"),
        @ApiResponse(responseCode = "404", description = "Pedido o tarjeta no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @Transactional
    @PostMapping("/finalizarCompra/{pedidoId}/{tarjetaId}")
    public ResponseEntity<?> finalizarCompra(@Parameter(description = "ID del pedido a finalizar", required = true)
            @PathVariable Integer pedidoId,
            @Parameter(description = "ID de la tarjeta a utilizar", required = true)
            @PathVariable Integer tarjetaId) {
        try {
            response = new HashMap<>();

            Pedido pedido = pedidoService.obtener(pedidoId).orElse(null);
            if (pedido == null) {
                response.put("error", "Pedido con ID " + pedidoId + " no encontrado.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            double total = 0.0;

            for (PedidoDetalle detalle : pedido.getDetalle()) {
                Optional<Producto> productoOpt = productoService.obtener(detalle.getProducto().getId());
                if (!productoOpt.isPresent()) {
                    response.put("error", "Producto con ID " + detalle.getProducto().getId() + " no encontrado.");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }

                Producto producto = productoOpt.get();
                int cantidad = detalle.getCantidad();
                int nuevoStock = producto.getStock() - cantidad;

                if (nuevoStock < 0) {
                    response.put("error", "Stock insuficiente para el producto: " + producto.getNombre());
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

                producto.setStock(nuevoStock);
                productoService.guardar(producto);

                total += producto.getPrecio() * cantidad;
            }

            TarjetaCredito tarjeta = tarjetaCreditoService.obtener(tarjetaId).orElse(null);
            if (tarjeta == null) {
                response.put("error", "Tarjeta con ID " + tarjetaId + " no encontrada.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            if (tarjeta.getSaldo() < pedido.getTotal()) {
                response.put("error", "Saldo insuficiente en la tarjeta.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            tarjeta.setSaldo(tarjeta.getSaldo() - pedido.getTotal());
            tarjetaCreditoService.guardar(tarjeta);

            pedido.setFechaPedido(LocalDateTime.now());
            pedido.setEstado("EN PREPARACIÓN");
            pedidoService.guardar(pedido);

            String rutaPDF = PdfGenerator.generarFacturaPDF(pedido);
            emailService.enviarFacturaConAdjunto(pedido.getUsuario().getCorreo(), rutaPDF);

            response.put("mensaje", "Compra finalizada con éxito y factura enviada al correo.");
            response.put("pedido", pedido);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void actualizar(Pedido viejo, Pedido nuevo) {
        if (nuevo.getDetalle() != null) {
            viejo.setDetalle(nuevo.getDetalle());
        }
        if (nuevo.getTotal() != null) {
            viejo.setTotal(nuevo.getTotal());
        }
    }
}
