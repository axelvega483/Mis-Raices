package com.MisRaices.ProyectoFinal.controller;

import com.MisRaices.ProyectoFinal.DTOS.TarjetaDTO.TarjetaCreditoGetDTO;
import com.MisRaices.ProyectoFinal.DTOS.TarjetaDTO.TarjetaCreditoPostDTO;
import com.MisRaices.ProyectoFinal.DTOS.TarjetaDTO.TarjetaCreditoPutDTO;
import com.MisRaices.ProyectoFinal.interfaz.TarjetaCreditoInterfaz;
import com.MisRaices.ProyectoFinal.util.ApiRespo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

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

@CrossOrigin("*")
@RestController
@RequestMapping("tarjeta")
@Tag(name = "Tarjetas de Crédito", description = "Controlador para operaciones de tarjetas de crédito")
public class TarjetaCreditoController {

    @Autowired
    private TarjetaCreditoInterfaz creditoService;

    @Operation(summary = "Listar todas las tarjetas", description = "Devuelve una lista con todas las tarjetas de crédito registradas")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping
    public ResponseEntity<ApiRespo<List<TarjetaCreditoGetDTO>>> listar() {
        List<TarjetaCreditoGetDTO> tarjetas = creditoService.listar();
        String message = tarjetas.isEmpty() ? "No hay tarjetas registradas" : "Todas las tarjetas";
        return new ResponseEntity<>(new ApiRespo<>(message, tarjetas, true), HttpStatus.OK);
    }

    @Operation(summary = "Registrar una nueva tarjeta", description = "Crea una tarjeta de crédito asociada a un usuario y establece un saldo inicial")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Tarjeta creada correctamente"), @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PostMapping
    public ResponseEntity<ApiRespo<TarjetaCreditoGetDTO>> crearTarjeta(@Parameter(description = "Datos de la tarjeta a registrar", required = true) @Valid @RequestBody TarjetaCreditoPostDTO tarjetaPost) {
        TarjetaCreditoGetDTO dto = creditoService.crear(tarjetaPost);
        return new ResponseEntity<>(new ApiRespo<>("Tarjeta creada", dto, true), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar una tarjeta", description = "Actualiza los datos de una tarjeta de crédito existente por ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Tarjeta actualizada correctamente"), @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada"), @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PutMapping("/{id}")
    public ResponseEntity<ApiRespo<TarjetaCreditoGetDTO>> editar(@Parameter(description = "ID de la tarjeta a editar", example = "1", required = true) @PathVariable Integer id, @Parameter(description = "Datos actualizados de la tarjeta", required = true) @Valid @RequestBody TarjetaCreditoPutDTO tarjetaPut) {
        TarjetaCreditoGetDTO dto = creditoService.actualizar(id, tarjetaPut);
        return new ResponseEntity<>(new ApiRespo<>("Tarjeta actualizada", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar una tarjeta", description = "Elimina una tarjeta de crédito por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Tarjeta eliminada correctamente"), @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada"), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRespo<Void>> eliminar(@Parameter(description = "ID de la tarjeta a eliminar", example = "1", required = true) @PathVariable Integer id) {
        creditoService.eliminar(id);
        return new ResponseEntity<>(new ApiRespo<>("Tarjeta eliminada correctamente", null, true), HttpStatus.OK);
    }
}