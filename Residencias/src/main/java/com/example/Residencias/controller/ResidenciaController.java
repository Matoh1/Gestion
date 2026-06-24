package com.example.Residencias.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Residencias.DTO.ResidenciaDTO;
import com.example.Residencias.model.Residencia;
import com.example.Residencias.service.ResidenciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/residencia")
@Tag(name = "Residencias", description = "API para la gestión de residencias")
public class ResidenciaController {

    @Autowired
    private ResidenciaService residenciaService;

    @GetMapping
    @Operation(summary = "Obtener todas las residencias", description = "Retorna una lista de todas las residencias registradas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de residencias encontrada"),
        @ApiResponse(responseCode = "204", description = "No hay residencias registradas")
    })
    public ResponseEntity<List<ResidenciaDTO>> todasLasResidencias() {
        List<ResidenciaDTO> residencias = residenciaService.obtenerTodos();
        if (residencias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(residencias);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener residencia por ID", description = "Retorna una residencia según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Residencia encontrada"),
        @ApiResponse(responseCode = "404", description = "Residencia no encontrada")
    })
    public ResponseEntity<ResidenciaDTO> residenciaPorId(@PathVariable Integer id) {
        try {
            ResidenciaDTO res = residenciaService.buscarporID(id);
            return ResponseEntity.ok(res);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear residencia", description = "Registra una nueva residencia en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Residencia creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Residencia> agregarResidencia(@Valid @RequestBody Residencia residencia) {
        try {
            Residencia nuevaResidencia = residenciaService.guardarResidencia(residencia);
            return new ResponseEntity<>(nuevaResidencia, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar residencia parcialmente", description = "Actualiza parcialmente los datos de una residencia")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Residencia actualizada"),
        @ApiResponse(responseCode = "404", description = "Residencia no encontrada")
    })
    public ResponseEntity<Residencia> actualizarResidencia(@PathVariable Integer id, @Valid @RequestBody Residencia residencia) {
        try {
            Residencia res = residenciaService.actualizarResidencia(id, residencia);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Reemplazar residencia", description = "Reemplaza completamente los datos de una residencia")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Residencia reemplazada"),
        @ApiResponse(responseCode = "404", description = "Residencia no encontrada")
    })
    public ResponseEntity<Residencia> reemplazarResidencia(@PathVariable Integer id,
            @Valid @RequestBody Residencia casa) {
        try {
            Residencia res = residenciaService.actualizarResidencia(id, casa);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar residencia", description = "Elimina una residencia del sistema por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Residencia eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Residencia no encontrada")
    })
    public ResponseEntity<Residencia> eliminarResidencia(@PathVariable Integer id) {
        String resultado = residenciaService.eliminar(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
