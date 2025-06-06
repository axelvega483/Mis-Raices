package com.MisRaices.demo.controller;

import com.MisRaices.demo.DTOS.TarjetaDTO.TarjetaCreditoGetDTO;
import com.MisRaices.demo.DTOS.TarjetaDTO.TarjetaCreditoPostDTO;
import com.MisRaices.demo.DTOS.TarjetaDTO.TarjetaCreditoPutDTO;
import com.MisRaices.demo.DTOS.TarjetaDTO.TarjetaMapper;
import com.MisRaices.demo.entity.TarjetaCredito;
import com.MisRaices.demo.entity.Usuario;
import com.MisRaices.demo.service.TarjetaCreditoService;
import com.MisRaices.demo.service.UsuarioService;
import com.MisRaices.demo.util.ApiRespo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

    @Autowired
    private TarjetaCreditoService creditoService;
    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar todas las tarjetas", description = "Devuelve una lista con todas las tarjetas de crédito registradas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<ApiRespo<?>> listar() {
        try {
            List<TarjetaCreditoGetDTO> dto = creditoService.listar()
                    .stream()
                    .map(TarjetaMapper::toDTO).collect(Collectors.toList());
            return new ResponseEntity<>(new ApiRespo<>("Todas las tarjetas", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Registrar una nueva tarjeta", description = "Crea una tarjeta de crédito asociada a un usuario y establece un saldo inicial.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tarjeta creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Usuario no válido"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<ApiRespo<?>> crearTarjeta(
            @Parameter(description = "Datos de la tarjeta a registrar", required = true)
            @Valid @RequestBody TarjetaCreditoPostDTO tarjetaPost,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return new ResponseEntity<>(new ApiRespo<>(errors, null, false), HttpStatus.BAD_REQUEST);
        }

        if (tarjetaPost.getUsuario() == null) {
            return new ResponseEntity<>(new ApiRespo<>("El usuario es obligatorio", null, false), HttpStatus.BAD_REQUEST);
        }

        Optional<Usuario> userOpt = usuarioService.obtener(tarjetaPost.getUsuario().getId());
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(new ApiRespo<>("Usuario no válido", null, false), HttpStatus.BAD_REQUEST);
        }

        try {
            TarjetaCredito tarjeta = new TarjetaCredito();
            tarjeta.setCodigoSeguridad(tarjetaPost.getCodigoSeguridad());
            tarjeta.setFechaVencimiento(tarjetaPost.getFechaVencimiento());
            tarjeta.setNumero(tarjetaPost.getNumero());
            tarjeta.setSaldo(20000.0);
            tarjeta.setTipo(tarjetaPost.getTipo());
            tarjeta.setTitular(tarjetaPost.getTitular());
            tarjeta.setUsuario(userOpt.get());

            TarjetaCredito tarjetaGuardada = creditoService.guardar(tarjeta);
            TarjetaCreditoGetDTO dto = TarjetaMapper.toDTO(tarjetaGuardada);

            return new ResponseEntity<>(new ApiRespo<>("Tarjeta creada", dto, true), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Actualizar una tarjeta", description = "Actualiza los datos de una tarjeta de crédito existente por ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarjeta actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("{id}")
    public ResponseEntity<ApiRespo<?>> editar(
            @Parameter(description = "Datos actualizados de la tarjeta", required = true)
            @RequestBody TarjetaCreditoPutDTO tarjetaPut,
            @Parameter(description = "ID de la tarjeta a editar", required = true)
            @PathVariable Integer id
    ) {
        try {

            TarjetaCredito tarjeta = creditoService.obtener(id).orElse(null);
            if (tarjeta == null) {
                return new ResponseEntity<>(new ApiRespo<>("Tarjeta no encontrada", null, false), HttpStatus.NOT_FOUND);
            }
            tarjeta.setCodigoSeguridad(tarjetaPut.getCodigoSeguridad());
            tarjeta.setFechaVencimiento(tarjetaPut.getFechaVencimiento());
            tarjeta.setNumero(tarjetaPut.getNumero());
            if (tarjetaPut.getSaldo() != null) {
                tarjeta.setSaldo(tarjetaPut.getSaldo());
            }
            tarjeta.setTipo(tarjetaPut.getTipo());
            tarjeta.setTitular(tarjetaPut.getTitular());
            TarjetaCreditoGetDTO dto = TarjetaMapper.toDTO(creditoService.guardar(tarjeta));

            return new ResponseEntity<>(new ApiRespo<>("Tarjeta actualizada", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar una tarjeta", description = "Elimina una tarjeta de crédito por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarjeta eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<ApiRespo<?>> eliminar(
            @Parameter(description = "ID de la tarjeta a eliminar", required = true)
            @PathVariable Integer id
    ) {
        try {
            TarjetaCredito tarjeta = creditoService.obtener(id).orElse(null);
            if (tarjeta == null) {
                return new ResponseEntity<>(new ApiRespo<>("Tarjeta no encontrada", null, false), HttpStatus.NOT_FOUND);

            }

            creditoService.eliminar(id);
            return new ResponseEntity<>(new ApiRespo<>("Tarjeta eliminada correctamente", null, true), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiRespo<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
