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

import com.example.Gestion.DTO.EspacioDTO;
import com.example.Gestion.model.Espacio;
import com.example.Gestion.service.EspacioService;

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
        String resultado = espacioService.borrarEspacio(id);
        if (resultado.startsWith("Error")) {
            return new ResponseEntity<>(resultado, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
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

}
