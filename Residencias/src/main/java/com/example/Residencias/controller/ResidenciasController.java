package com.example.Residencias.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Residencias.DTO.ResidenciasDTO;
import com.example.Residencias.service.ResidenciasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/residencias")
@Tag(name = "Asignaciones", description = "API para la asignación de usuarios a residencias")
public class ResidenciasController {

    @Autowired
    private ResidenciasService residenciasService;

    @GetMapping
    @Operation(summary = "Obtener todas las asignaciones", description = "Retorna todas las asignaciones de usuarios a residencias")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de asignaciones encontrada"),
        @ApiResponse(responseCode = "204", description = "No hay asignaciones registradas")
    })
    public ResponseEntity<List<ResidenciasDTO>> obtenerResidencias() {
        List<ResidenciasDTO> residencias = residenciasService.obtenerTodasLasAsignaciones();
        if (residencias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(residencias, HttpStatus.OK);
    }

    @GetMapping("/usuario/{userId}")
    @Operation(summary = "Obtener asignaciones por usuario", description = "Retorna las residencias asignadas a un usuario específico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Asignaciones encontradas"),
        @ApiResponse(responseCode = "204", description = "El usuario no tiene asignaciones")
    })
    public ResponseEntity<List<ResidenciasDTO>> listarPorUsuario(@PathVariable Integer userId) {
        List<ResidenciasDTO> residencias = residenciasService.obtenerAsignacionesPorUsuario(userId);
        if (residencias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(residencias, HttpStatus.OK);
    }

    @PostMapping("/residencia/{residenciaId}/usuario/{userId}")
    @Operation(summary = "Asignar usuario a residencia", description = "Asigna un usuario existente a una residencia")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario asignado correctamente"),
        @ApiResponse(responseCode = "400", description = "Error en la asignación")
    })
    public ResponseEntity<String> asignarUsuarioAResidencia(@PathVariable Integer residenciaId,
            @PathVariable Integer userId) {
        try {
            String mensaje = residenciasService.añadirUsuarioAResidencia(residenciaId, userId);
            return new ResponseEntity<>(mensaje, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/residencia/{residenciaId}/usuario/{userId}")
    @Operation(summary = "Remover usuario de residencia", description = "Elimina la asignación de un usuario a una residencia")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario desvinculado correctamente"),
        @ApiResponse(responseCode = "404", description = "Asignación no encontrada")
    })
    public ResponseEntity<String> removerUsuarioDeResidencia(@PathVariable Integer residenciaId,
            @PathVariable Integer userId) {
        try {
            String mensaje = residenciasService.eliminarUsuarioDeResidencia(residenciaId, userId);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
