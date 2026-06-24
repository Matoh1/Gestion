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

import com.example.Residencias.DTO.RegionDTO;
import com.example.Residencias.model.Region;
import com.example.Residencias.service.RegionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/region")
@Tag(name = "Regiones", description = "API para la gestión de regiones")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    @Operation(summary = "Obtener todas las regiones", description = "Retorna una lista de todas las regiones registradas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de regiones encontrada"),
        @ApiResponse(responseCode = "204", description = "No hay regiones registradas")
    })
    public ResponseEntity<List<RegionDTO>> obtenerRegiones() {
        List<RegionDTO> regiones = regionService.obtenerTodos();
        if (regiones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(regiones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener región por ID", description = "Retorna una región según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Región encontrada"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<RegionDTO> buscarPorId(@PathVariable Integer id) {
        try {
            RegionDTO region = regionService.buscarporID(id);
            return new ResponseEntity<>(region, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @Operation(summary = "Crear región", description = "Registra una nueva región en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Región creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<Region> agregarRegion(@Valid @RequestBody Region region) {
        try {
            Region nuevaRegion = regionService.guardarRegion(region);
            return new ResponseEntity<>(nuevaRegion, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{regionId}/comuna/{comunaId}")
    @Operation(summary = "Asignar comuna a región", description = "Asocia una comuna existente a una región")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Comuna asignada correctamente"),
        @ApiResponse(responseCode = "404", description = "Región o comuna no encontrada")
    })
    public ResponseEntity<String> añadirComunaARegion(@PathVariable Integer regionId, @PathVariable Integer comunaId) {
        try {
            String mensaje = regionService.añadirComunaARegion(regionId, comunaId);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar región", description = "Elimina una región del sistema por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Región eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Región no encontrada")
    })
    public ResponseEntity<Region> eliminarRegion(@PathVariable Integer id) {
        String resultado = regionService.eliminar(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
