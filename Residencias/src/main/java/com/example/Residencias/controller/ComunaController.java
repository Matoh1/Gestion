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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/comuna")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public ResponseEntity<List<ComunaDTO>> obtenerComunas() {
        List<ComunaDTO> comunas = comunaService.obtenerTodos();
        return new ResponseEntity<>(comunas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerComunaPorId(@PathVariable Integer id) {
        try {
            ComunaDTO comuna = comunaService.buscarporID(id);
            return new ResponseEntity<>(comuna, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> crearComuna(@Valid @RequestBody Comuna comuna) {
        try {
            Comuna comunaCreada = comunaService.guardarComuna(comuna);
            return new ResponseEntity<>(comunaCreada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error en la transmisión de datos", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{comunaId}/residencia/{residenciaId}")
    public ResponseEntity<?> añadirResidenciaAComuna(@PathVariable Integer comunaId,
            @PathVariable Integer residenciaId) {
        try {
            String mensaje = comunaService.añadirResidenciaAComuna(comunaId, residenciaId);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarComuna(@PathVariable Integer id) {
        try {
            String resultado = comunaService.eliminar(id);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
