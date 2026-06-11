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

import com.example.Gestion.DTO.IncidenciaDTO;
import com.example.Gestion.model.Incidencia;
import com.example.Gestion.service.IncidenciaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/incidencia")
public class IncidenciaController {

    @Autowired
    private IncidenciaService incidenciaService;

    @GetMapping
    public ResponseEntity<List<IncidenciaDTO>> obtenerTodos() {
        List<IncidenciaDTO> incidencias = incidenciaService.obtenerTodos();
        if (incidencias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(incidencias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidenciaDTO> buscarIncidenciaPorId(@PathVariable Integer id) {
        try {
            IncidenciaDTO incidencia = incidenciaService.buscarporID(id);
            return ResponseEntity.ok(incidencia);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Incidencia> agregarIncidencia(@Valid @RequestBody Incidencia incidencia) {
        try {
            Incidencia nuevaIncidencia = incidenciaService.guardarIncidencia(incidencia);
            return new ResponseEntity<>(nuevaIncidencia, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarIncidencia(@PathVariable Integer id) {
        String resultado = incidenciaService.borrarIncidencia(id);
        if (resultado.startsWith("Error")) {
            return new ResponseEntity<>(resultado, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        }
    }
}
