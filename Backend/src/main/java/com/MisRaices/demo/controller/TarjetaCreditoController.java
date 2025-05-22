package com.MisRaices.demo.controller;

import com.MisRaices.demo.entity.TarjetaCredito;
import com.MisRaices.demo.entity.Usuario;
import com.MisRaices.demo.service.TarjetaCreditoService;
import com.MisRaices.demo.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.HashMap;
import java.util.Map;
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
@RequestMapping("tarjeta")
public class TarjetaCreditoController {

    private Map<String, Object> response;

    @Autowired
    private TarjetaCreditoService creditoService;
    @Autowired
    private UsuarioService UsuarioService;

    @Operation(summary = "Listar todas las tarjetas", description = "Devuelve una lista con todas las tarjetas de crédito registradas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping()
    public ResponseEntity<?> listar() {
        try {
            response = new HashMap<>();
            return new ResponseEntity<>(creditoService.listar(), HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Registrar una nueva tarjeta", description = "Crea una tarjeta de crédito asociada a un usuario y establece un saldo inicial.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tarjeta creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Usuario no válido"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping()
    public ResponseEntity<?> crearTarjeta(
            @Parameter(description = "Datos de la tarjeta a registrar", required = true)
            @RequestBody TarjetaCredito tarjetaCredito) {
        try {
            response = new HashMap<>();
            Usuario user = UsuarioService.obtener(tarjetaCredito.getUsuario().getId()).orElse(null);
            if (user == null) {
                response.put("error", "cliente no válido.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            tarjetaCredito.setUsuario(user);
            tarjetaCredito.setSaldo(20000.00);
            return new ResponseEntity<>(creditoService.guardar(tarjetaCredito), HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Actualizar una tarjeta", description = "Actualiza los datos de una tarjeta de crédito existente por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarjeta actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("{id}")
    public ResponseEntity<?> editar(
            @Parameter(description = "Datos actualizados de la tarjeta", required = true)
            @RequestBody TarjetaCredito tarjetaCredito,
            @Parameter(description = "ID de la tarjeta a editar", required = true)
            @PathVariable Integer id) {
        try {
            response = new HashMap<>();
            TarjetaCredito tarjeta = creditoService.obtener(id).orElse(null);
            if (tarjeta != null) {
                actualizar(tarjeta, tarjetaCredito);
                return new ResponseEntity<>(creditoService.guardar(tarjeta), HttpStatus.OK);
            } else {
                response.put("Tarjeta", "no se encontro tarjeta");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar una tarjeta", description = "Elimina una tarjeta de crédito por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarjeta eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID de la tarjeta a eliminar", required = true) @PathVariable Integer id) {
        try {
            response = new HashMap<>();
            TarjetaCredito tarjeta = creditoService.obtener(id).orElse(null);
            if (tarjeta == null) {
                response.put("tarjeta", "error tarjeta no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else {
                creditoService.eliminar(id);
                response.put("tarjeta", " eliminada del sistema");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void actualizar(TarjetaCredito viejo, TarjetaCredito nuevo) {
        if (nuevo.getTitular() != null) {
            viejo.setTitular(nuevo.getTitular());
        }
        if (nuevo.getNumero() != null) {
            viejo.setNumero(nuevo.getNumero());
        }
        if (nuevo.getCodigoSeguridad() != null) {
            viejo.setCodigoSeguridad(nuevo.getCodigoSeguridad());
        }
        if (nuevo.getFechaVencimiento() != null) {
            viejo.setCodigoSeguridad(nuevo.getCodigoSeguridad());
        }
        if (nuevo.getSaldo() != null) {
            viejo.setSaldo(nuevo.getSaldo());
        }
    }
}
