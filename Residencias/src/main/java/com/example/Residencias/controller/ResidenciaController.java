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

    @GetMapping
    public ResponseEntity<List<ResidenciaDTO>> todasLasResidencias() {
        List<ResidenciaDTO> residencias = residenciaService.obtenerTodos();
        return new ResponseEntity<>(residencias, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> residenciaPorId(@PathVariable Integer id) {
        try {
            ResidenciaDTO res = residenciaService.buscarporID(id);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> agregarResidencia(@Valid @RequestBody Residencia residencia) {
        try {
            Residencia nuevaResidencia = residenciaService.guardarResidencia(residencia);
            return new ResponseEntity<>(nuevaResidencia, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error en la transmisión de datos", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarResidencia(@PathVariable Integer id, @Valid @RequestBody Residencia residencia) {
        try {
            Residencia res = residenciaService.actualizarResidencia(id, residencia);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> reemplazarResidencia(@PathVariable Integer id,
            @Valid @RequestBody Residencia casa) {
        try {
            Residencia res = residenciaService.actualizarResidencia(id, casa);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarResidencia(@PathVariable Integer id) {
        try {
            String resultado = residenciaService.eliminar(id);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
