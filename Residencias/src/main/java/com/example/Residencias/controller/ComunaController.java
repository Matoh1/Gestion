package com.example.Residencias.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Residencias.DTO.ComunaDTO;
import com.example.Residencias.model.Comuna;
import com.example.Residencias.service.ComunaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/comuna")
@Tag(name = "Comunas", description = "API para la gestión de comunas")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @GetMapping
    @Operation(summary = "Obtener todas las comunas", description = "Retorna una lista de todas las comunas registradas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de comunas encontrada"),
        @ApiResponse(responseCode = "204", description = "No hay comunas registradas")
    })
    public ResponseEntity<List<ComunaDTO>> obtenerComunas() {
        List<ComunaDTO> comunas = comunaService.obtenerTodos();
        if (comunas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(comunas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener comuna por ID", description = "Retorna una comuna según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Comuna encontrada"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<ComunaDTO> obtenerComunaPorId(@PathVariable Integer id) {
        try {
            ComunaDTO comuna = comunaService.buscarporID(id);
            return new ResponseEntity<>(comuna, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @Operation(summary = "Crear comuna", description = "Registra una nueva comuna en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Comuna creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Comuna> crearComuna(@Valid @RequestBody Comuna comuna) {
        Comuna comunaCreada = comunaService.guardarComuna(comuna);
        return new ResponseEntity<>(comunaCreada, HttpStatus.CREATED);
    }

    @PutMapping("/{comunaId}/residencia/{residenciaId}")
    @Operation(summary = "Asignar residencia a comuna", description = "Asocia una residencia existente a una comuna")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Residencia asignada correctamente"),
        @ApiResponse(responseCode = "404", description = "Comuna o residencia no encontrada")
    })
    public ResponseEntity<String> añadirResidenciaAComuna(@PathVariable Integer comunaId,
            @PathVariable Integer residenciaId) {
        try {
            String mensaje = comunaService.añadirResidenciaAComuna(comunaId, residenciaId);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar comuna", description = "Elimina una comuna del sistema por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Comuna eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Comuna no encontrada")
    })
    public ResponseEntity<Comuna> eliminarComuna(@PathVariable Integer id) {
        String resultado = comunaService.eliminar(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
