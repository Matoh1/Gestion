package com.example.Espacios.controller;

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

import com.example.Espacios.DTO.EspacioDTO;
import com.example.Espacios.model.Espacio;
import com.example.Espacios.service.EspacioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/espacio")
public class EspacioController {

    @Autowired
    private EspacioService espacioService;

    @GetMapping
    public ResponseEntity<List<EspacioDTO>> obtenerTodos() {
        List<EspacioDTO> espacioM = espacioService.obtenerTodos();
        if (espacioM.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(espacioM);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspacioDTO> buscarEspacioPorId(@PathVariable Integer id) {
        try {
            EspacioDTO espacio = espacioService.buscarporID(id);
            return ResponseEntity.ok(espacio);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Espacio> agregarEspacio(@Valid @RequestBody Espacio espacio) {
        try {
            Espacio nuevoEspacio = espacioService.guardarEspacio(espacio);
            return new ResponseEntity<>(nuevoEspacio, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEspacio(@PathVariable Integer id) {
        try {
            String resultado = espacioService.borrarEspacio(id);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Asignar espacio a residencia
    @PostMapping("/{espacioId}/{residenciaId}")
    public ResponseEntity<Espacio> asignarAResidencia(@PathVariable Integer espacioId,
            @PathVariable Integer residenciaId) {
        try {
            Espacio espacio = espacioService.asignarEspacioAResidencia(espacioId, residenciaId);
            return ResponseEntity.ok(espacio);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminarVinculo/{id}/{residenciaId}")
    public ResponseEntity<String> eliminarVinculo(@PathVariable Integer id, @PathVariable Integer residenciaId) {
        try {
            String resultado = espacioService.eliminarVinculo(id, residenciaId);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
