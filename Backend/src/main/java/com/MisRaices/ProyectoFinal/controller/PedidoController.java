package com.MisRaices.ProyectoFinal.controller;

import com.MisRaices.ProyectoFinal.DTOS.PedidoDTO.PedidoGetDTO;
import com.MisRaices.ProyectoFinal.DTOS.PedidoDTO.PedidoPostDTO;
import com.MisRaices.ProyectoFinal.PDF.PdfGenerator;
import com.MisRaices.ProyectoFinal.interfaz.PedidoInterfaz;
import com.MisRaices.ProyectoFinal.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.MisRaices.ProyectoFinal.util.ApiRespo;

@CrossOrigin("*")
@RestController
@RequestMapping("pedido")
@Tag(name = "Pedidos", description = "Controlador para operaciones de pedidos")
public class PedidoController {

    @Autowired
    private PedidoInterfaz pedidoService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private PdfGenerator pdfGenerator;

    @Operation(summary = "Listar todos los pedidos", description = "Retorna una lista de todos los pedidos realizados")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping
    public ResponseEntity<ApiRespo<List<PedidoGetDTO>>> listar() {
        List<PedidoGetDTO> pedidos = pedidoService.listar();
        String message = pedidos.isEmpty() ? "No hay pedidos registrados" : "Lista obtenida exitosamente";
        return new ResponseEntity<>(new ApiRespo<>(message, pedidos, true), HttpStatus.OK);
    }

    @Operation(summary = "Obtener pedido por ID", description = "Retorna un pedido según su identificador")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Pedido encontrado"), @ApiResponse(responseCode = "404", description = "Pedido no encontrado"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping("/{id}")
    public ResponseEntity<ApiRespo<PedidoGetDTO>> obtener(@Parameter(description = "ID del pedido", example = "1", required = true) @PathVariable Integer id) {
        return pedidoService.obtener(id).map(pedido -> new ResponseEntity<>(new ApiRespo<>("Pedido encontrado", pedido, true), HttpStatus.OK)).orElse(new ResponseEntity<>(new ApiRespo<>("Pedido no encontrado", null, false), HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Crear un nuevo pedido", description = "Crea un nuevo pedido con sus detalles y calcula el total")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Pedido creado exitosamente"), @ApiResponse(responseCode = "400", description = "Datos inválidos o stock insuficiente"), @ApiResponse(responseCode = "404", description = "Producto o cliente no encontrado"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PostMapping
    public ResponseEntity<ApiRespo<PedidoGetDTO>> crear(@Parameter(description = "Datos del pedido a crear", required = true) @Valid @RequestBody PedidoPostDTO pedidoPostDTO) {
        PedidoGetDTO dto = pedidoService.crear(pedidoPostDTO);
        try {
            String numeroPedido = "PED-" + dto.getId();
            emailService.sendOrderConfirmation(dto.getUsuario().getCorreo(), numeroPedido);
        } catch (Exception e) {
            System.err.println("Error enviando confirmación de pedido: " + e.getMessage());
        }
        return new ResponseEntity<>(new ApiRespo<>("Pedido creado exitosamente", dto, true), HttpStatus.CREATED);
    }

    @Operation(summary = "Finalizar compra de un pedido", description = "Procesa el pago con una tarjeta, descuenta stock y envía factura por correo")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Compra finalizada exitosamente"), @ApiResponse(responseCode = "400", description = "Saldo insuficiente o stock insuficiente"), @ApiResponse(responseCode = "404", description = "Pedido o tarjeta no encontrado"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @Transactional
    @PostMapping("/finalizar-compra/{pedidoId}/{tarjetaId}")
    public ResponseEntity<ApiRespo<PedidoGetDTO>> finalizarCompra(@Parameter(description = "ID del pedido", example = "1", required = true) @PathVariable Integer pedidoId, @Parameter(description = "ID de la tarjeta", example = "1", required = true) @PathVariable Integer tarjetaId) throws MessagingException {
        PedidoGetDTO dto = pedidoService.finalizarCompra(pedidoId, tarjetaId);
        try {
            String rutaPDF = pdfGenerator.generarFacturaPDF(pedidoService.obtenerEntity(pedidoId).orElseThrow());
            String numeroPedido = "PED-" + dto.getId();
            emailService.enviarFacturaConAdjunto(dto.getUsuario().getCorreo(), rutaPDF, numeroPedido);
        } catch (Exception e) {
            System.err.println("Error enviando factura: " + e.getMessage());
        }
        return new ResponseEntity<>(new ApiRespo<>("Compra finalizada con éxito y factura enviada al correo", dto, true), HttpStatus.OK);
    }
}