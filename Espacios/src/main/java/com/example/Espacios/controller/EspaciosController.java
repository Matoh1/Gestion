package com.example.Espacios.controller;

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

import com.example.Gestion.DTO.EspaciosDTO;
import com.example.Gestion.service.EspaciosService;

@RestController
@RequestMapping("/api/v1/espacios")
public class EspaciosController {

    @Autowired
    private EspaciosService espaciosService;

    @GetMapping
    public ResponseEntity<List<EspaciosDTO>> obtenerTodos() {
        List<EspaciosDTO> espacios = espaciosService.obtenerTodos();
        if (espacios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(espacios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspaciosDTO> buscarEspaciosPorId(@PathVariable Integer id) {
        try {
            EspaciosDTO espacio = espaciosService.buscarporID(id);
            return ResponseEntity.ok(espacio);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{espacioId}/{residenciaId}")
    public ResponseEntity<String> relacionar(@PathVariable Integer espacioId, @PathVariable Integer residenciaId) {
        try {
            espaciosService.guardarEspacios(espacioId, residenciaId);
            return new ResponseEntity<>("Vinculación realizada correctamente", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error al vincular: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarVinculo(@PathVariable Integer id) {
        String resultado = espaciosService.eliminarVinculo(id);
        if (resultado.startsWith("Error")) {
            return new ResponseEntity<>(resultado, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        }
    }

}
