package com.MisRaices.demo.controller;

import com.MisRaices.demo.DTOS.PedidoDTO.PedidoDetallePostDTO;
import com.MisRaices.demo.DTOS.PedidoDTO.PedidoGetDTO;
import com.MisRaices.demo.DTOS.PedidoDTO.PedidoMapper;
import com.MisRaices.demo.DTOS.PedidoDTO.PedidoPostDTO;
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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import com.MisRaices.demo.util.ApiRespo;
import java.util.stream.Collectors;

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

    @Operation(summary = "Listar todos los pedidos", description = "Retorna una lista de todos los pedidos realizados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<ApiRespo<List<PedidoGetDTO>>> listar() {
        try {
            List<PedidoGetDTO> dto = pedidoService.listar().stream()
                    .map(PedidoMapper::toDTO).collect(Collectors.toList());
            return new ResponseEntity<>(new ApiRespo<>("Lista obtenida exitosamente", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error interno del servidor: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiRespo<PedidoGetDTO>> obtener(@PathVariable Integer id) {
        try {
            Pedido pedido = pedidoService.obtener(id).orElse(null);
            if (pedido == null) {
                return new ResponseEntity<>(new ApiRespo<>("Pedido no encontrado", null, false), HttpStatus.NOT_FOUND);
            }
            PedidoGetDTO dto = PedidoMapper.toDTO(pedido);
            return new ResponseEntity<>(new ApiRespo<>("Pedido encontrado", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error interno del servidor: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ApiRespo<PedidoGetDTO>> crear(@RequestBody PedidoPostDTO pedidoPostDTO) {
        try {
            Usuario cliente = usuarioService.obtener(pedidoPostDTO.getUsuarioId()).orElse(null);
            if (cliente == null) {
                return new ResponseEntity<>(new ApiRespo<>("Cliente no válido", null, false), HttpStatus.BAD_REQUEST);
            }

            Pedido pedido = new Pedido();
            pedido.setUsuario(cliente);

            List<PedidoDetalle> detalles = new ArrayList<>();
            double total = 0;

            for (PedidoDetallePostDTO detDto : pedidoPostDTO.getDetalle()) {
                Producto producto = productoService.obtener(detDto.getProductoId()).orElse(null);
                if (producto == null) {
                    return new ResponseEntity<>(new ApiRespo<>("Producto no encontrado con ID " + detDto.getProductoId(), null, false), HttpStatus.NOT_FOUND);
                }
                if (producto.getStock() < detDto.getCantidad()) {
                    return new ResponseEntity<>(new ApiRespo<>("Stock insuficiente para producto " + producto.getNombre(), null, false), HttpStatus.BAD_REQUEST);
                }

                PedidoDetalle detalle = new PedidoDetalle();
                detalle.setProducto(producto);
                detalle.setCantidad(detDto.getCantidad());
                detalle.setPedido(pedido);

                detalles.add(detalle);
                total += producto.getPrecio() * detDto.getCantidad();
            }

            pedido.setDetalle(detalles);
            pedido.setTotal(total);
            pedido.setFechaPedido(LocalDateTime.now());
            pedido.setEstado("PENDIENTE");

            Pedido pedidoGuardado = pedidoService.guardar(pedido);
            PedidoGetDTO dto = PedidoMapper.toDTO(pedidoGuardado);

            return new ResponseEntity<>(new ApiRespo<>("Pedido creado exitosamente", dto, true), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error interno del servidor: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiRespo<PedidoGetDTO>> actualizar(@RequestBody PedidoPostDTO pedidoPostDTO,
            @PathVariable Integer id) {
        try {
            Pedido pedidoBD = pedidoService.obtener(id).orElse(null);
            if (pedidoBD == null) {
                return new ResponseEntity<>(new ApiRespo<>("Pedido no encontrado", null, false), HttpStatus.NOT_FOUND);
            }

            // Actualizar usuario si es diferente
            if (!pedidoBD.getUsuario().getId().equals(pedidoPostDTO.getUsuarioId())) {
                Usuario cliente = usuarioService.obtener(pedidoPostDTO.getUsuarioId()).orElse(null);
                if (cliente == null) {
                    return new ResponseEntity<>(new ApiRespo<>("Cliente no válido", null, false), HttpStatus.BAD_REQUEST);
                }
                pedidoBD.setUsuario(cliente);
            }

            List<PedidoDetalle> detalles = new ArrayList<>();
            double total = 0;

            for (PedidoDetallePostDTO detDto : pedidoPostDTO.getDetalle()) {
                Producto producto = productoService.obtener(detDto.getProductoId()).orElse(null);
                if (producto == null) {
                    return new ResponseEntity<>(new ApiRespo<>("Producto no encontrado con ID " + detDto.getProductoId(), null, false), HttpStatus.NOT_FOUND);
                }
                if (producto.getStock() < detDto.getCantidad()) {
                    return new ResponseEntity<>(new ApiRespo<>("Stock insuficiente para producto " + producto.getNombre(), null, false), HttpStatus.BAD_REQUEST);
                }

                PedidoDetalle detalle = new PedidoDetalle();
                detalle.setProducto(producto);
                detalle.setCantidad(detDto.getCantidad());
                detalle.setPedido(pedidoBD);

                detalles.add(detalle);
                total += producto.getPrecio() * detDto.getCantidad();
            }

            pedidoBD.setDetalle(detalles);
            pedidoBD.setTotal(total);

            Pedido pedidoActualizado = pedidoService.guardar(pedidoBD);
            PedidoGetDTO dto = PedidoMapper.toDTO(pedidoActualizado);

            return ResponseEntity.ok(new ApiRespo<>("Pedido actualizado exitosamente", dto, true));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error interno del servidor: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiRespo<String>> eliminar(@PathVariable Integer id) {
        try {
            Pedido pedido = pedidoService.obtener(id).orElse(null);
            if (pedido == null) {
                return new ResponseEntity<>(new ApiRespo<>("Pedido no encontrado", null, false), HttpStatus.NOT_FOUND);
            }
            pedidoService.eliminar(id);
            return ResponseEntity.ok(new ApiRespo<>("Pedido eliminado exitosamente", "Eliminado", true));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error interno del servidor: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @PostMapping("/finalizarCompra/{pedidoId}/{tarjetaId}")
    public ResponseEntity<ApiRespo<PedidoGetDTO>> finalizarCompra(@PathVariable Integer pedidoId,
            @PathVariable Integer tarjetaId) {
        try {
            Pedido pedido = pedidoService.obtener(pedidoId).orElse(null);
            if (pedido == null) {
                return new ResponseEntity<>(new ApiRespo<>("Pedido no encontrado con ID " + pedidoId, null, false), HttpStatus.NOT_FOUND);
            }

            double total = 0.0;

            for (PedidoDetalle detalle : pedido.getDetalle()) {
                Producto producto = productoService.obtener(detalle.getProducto().getId()).orElse(null);
                if (producto == null) {
                    return new ResponseEntity<>(new ApiRespo<>("Producto no encontrado con ID " + detalle.getProducto().getId(), null, false), HttpStatus.NOT_FOUND);
                }
                int nuevoStock = producto.getStock() - detalle.getCantidad();
                if (nuevoStock < 0) {
                    return new ResponseEntity<>(new ApiRespo<>("Stock insuficiente para producto: " + producto.getNombre(), null, false), HttpStatus.BAD_REQUEST);
                }

                producto.setStock(nuevoStock);
                productoService.guardar(producto);

                total += producto.getPrecio() * detalle.getCantidad();
            }

            TarjetaCredito tarjeta = tarjetaCreditoService.obtener(tarjetaId).orElse(null);
            if (tarjeta == null) {
                return new ResponseEntity<>(new ApiRespo<>("Tarjeta no encontrada con ID " + tarjetaId, null, false), HttpStatus.NOT_FOUND);
            }

            if (tarjeta.getSaldo() < pedido.getTotal()) {
                return new ResponseEntity<>(new ApiRespo<>("Saldo insuficiente en la tarjeta", null, false), HttpStatus.BAD_REQUEST);
            }

            tarjeta.setSaldo(tarjeta.getSaldo() - pedido.getTotal());
            tarjetaCreditoService.guardar(tarjeta);

            pedido.setFechaPedido(LocalDateTime.now());
            pedido.setEstado("EN PREPARACIÓN");
            Pedido pedidoFinalizado = pedidoService.guardar(pedido);

            String rutaPDF = PdfGenerator.generarFacturaPDF(pedidoFinalizado);
            emailService.enviarFacturaConAdjunto(pedidoFinalizado.getUsuario().getCorreo(), rutaPDF);

            PedidoGetDTO dto = PedidoMapper.toDTO(pedidoFinalizado);

            return ResponseEntity.ok(new ApiRespo<>("Compra finalizada con éxito y factura enviada al correo", dto, true));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error interno del servidor: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
