package com.example.Gestion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Gestion.DTO.IncidenciasDTO;
import com.example.Gestion.model.Incidencias;
import com.example.Gestion.service.IncidenciasService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/incidencias")
public class IncidenciasController {

    @Autowired
    private IncidenciasService incidenciasService;

    @GetMapping
    public ResponseEntity<List<IncidenciasDTO>> obtenerTodos() {
        List<IncidenciasDTO> incidencias = incidenciasService.obtenerTodos();
        if (incidencias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(incidencias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidenciasDTO> buscarIncidenciasPorId(@PathVariable Integer id) {
        try {
            IncidenciasDTO incidencias = incidenciasService.buscarporID(id);
            return ResponseEntity.ok(incidencias);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Incidencias> agregarIncidencias(@Valid @RequestBody Incidencias incidencias) {
        try {
            Incidencias nuevoReporte = incidenciasService.guardarIncidencias(incidencias);
            return new ResponseEntity<>(nuevoReporte, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarIncidencias(@PathVariable Integer id) {
        String resultado = incidenciasService.borrarIncidencias(id);
        if (resultado.startsWith("Error")) {
            return new ResponseEntity<>(resultado, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        }
    }
}
