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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/residencia")
public class ResidenciaController {

    @Autowired
    private ResidenciaService residenciaService;

    // Endpoint para obtener todas las residencias
    @GetMapping
    public ResponseEntity<List<ResidenciaDTO>> todasLasResidencias() {
        List<ResidenciaDTO> residencias = residenciaService.obtenerTodos();
        if (residencias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(residencias);
    }

    // Endpoint para buscar una residencia por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResidenciaDTO> residenciaPorId(@PathVariable Integer id) {
        try {
            ResidenciaDTO res = residenciaService.buscarporID(id);
            return ResponseEntity.ok(res);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para agregar una nueva residencia
    @PostMapping
    public ResponseEntity<Residencia> agregarResidencia(@Valid @RequestBody Residencia residencia) {
        try {
            Residencia nuevaResidencia = residenciaService.guardarResidencia(residencia);
            return new ResponseEntity<>(nuevaResidencia, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint para actualizar una residencia por ID
    @PatchMapping("/{id}")
    public ResponseEntity<Residencia> actualizarResidencia(@PathVariable Integer id, @Valid @RequestBody Residencia residencia) {
        try {
            Residencia res = residenciaService.actualizarResidencia(id, residencia);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para reemplazar una residencia por ID
    @PutMapping("/{id}")
    public ResponseEntity<Residencia> reemplazarResidencia(@PathVariable Integer id,
            @Valid @RequestBody Residencia casa) {
        try {
            Residencia res = residenciaService.actualizarResidencia(id, casa);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para eliminar una residencia por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Residencia> eliminarResidencia(@PathVariable Integer id) {
        String resultado = residenciaService.eliminar(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
