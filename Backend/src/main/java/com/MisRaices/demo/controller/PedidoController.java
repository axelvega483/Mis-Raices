package com.MisRaices.demo.controller;

import com.MisRaices.demo.entity.Pedido;
import com.MisRaices.demo.entity.PedidoDetalle;
import com.MisRaices.demo.entity.Producto;
import com.MisRaices.demo.service.PedidoService;
import com.MisRaices.demo.service.ProductoService;
import java.time.LocalDate;
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
    ProductoService productoService;

    private Map<String, Object> response;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar() {
        try {
            response = new HashMap<>();
            response.put("Pedidos", pedidoService.listar());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error al listar los pedidos", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Map<String, Object>> obtener(@PathVariable Integer id) {
        try {
            response = new HashMap<>();
            Pedido pedido = pedidoService.obtener(id).orElse(null);
            if (pedido != null) {
                response.put("pedido", pedido);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("error", "pedido no existe");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@RequestBody Pedido pedido) {
        try {
            response = new HashMap<>();
            // Crear los detalles del pedido
            int nuevoStock, stock, cant;
            Double precio = 0.0;
            List<PedidoDetalle> detalles = new ArrayList<>();
            for (PedidoDetalle detalle : pedido.getDetalle()) {
                PedidoDetalle deta = new PedidoDetalle();
                Optional<Producto> productoOptional = productoService.obtener(detalle.getProducto().getId());
                if (!productoOptional.isPresent()) {
                    response.put("error", "Producto no encontrado con ID: " + detalle.getProducto().getId());
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
                Producto producto = productoOptional.get();
                producto.setId(detalle.getProducto().getId());
                deta.setCantidad(detalle.getCantidad());
                deta.setProducto(producto);
                deta.setPedido(pedido);
                detalles.add(deta);
                precio += producto.getPrecio() * detalle.getCantidad();
                pedido.setTotal(precio);
                stock = producto.getStock();
                cant = detalle.getCantidad();
                nuevoStock = stock - cant;
                if (nuevoStock >= 0) {
                    producto.setStock(nuevoStock);
                } else {
                    response.put("error", "el stock no puede ser negativo");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

            }
            pedido.setDetalle(detalles);
            pedido.setTotal(precio);
            pedido.setFechaPedido(LocalDate.now());

            Pedido ped = pedidoService.guardar(pedido);
            response.put("pedido", ped);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@RequestBody Pedido pedido,
            @PathVariable(required = true) Integer id) {
        try {
            response = new HashMap<>();
            Pedido pedidoBD = pedidoService.obtener(id).orElse(null);
            if (pedidoBD == null) {
                response.put("error", "no se encontro pedido");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            actualizar(pedidoBD, pedido);
            response.put("pedido", pedidoService.guardar(pedidoBD));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer id) {
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

    public void actualizar(Pedido viejo, Pedido nuevo) {
        if (nuevo.getDetalle() != null) {
            viejo.setDetalle(nuevo.getDetalle());
        }
        if (nuevo.getTotal() != null) {
            viejo.setTotal(nuevo.getTotal());
        }
    }
}
