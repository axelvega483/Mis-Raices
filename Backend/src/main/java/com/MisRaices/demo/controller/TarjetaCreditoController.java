package com.MisRaices.demo.controller;

import com.MisRaices.demo.entity.TarjetaCredito;
import com.MisRaices.demo.service.TarjetaCreditoService;
import com.MisRaices.demo.service.UsuarioService;
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

    @PostMapping()
    public ResponseEntity<?> crearTarjeta(@RequestBody TarjetaCredito tarjetaCredito) {
        try {
            response = new HashMap<>();
            tarjetaCredito.setSaldo(20000.00);
            return new ResponseEntity<>(creditoService.guardar(tarjetaCredito), HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editar(@RequestBody TarjetaCredito tarjetaCredito,
            @PathVariable(required = true) Integer id) {
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

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
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
