package com.example.Residencias.controller;

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

import com.example.Residencias.DTO.ResidenciasDTO;
import com.example.Residencias.service.ResidenciasService;

@RestController
@RequestMapping("/api/v1/residencias")
public class ResidenciasController {

    @Autowired
    private ResidenciasService residenciasService;

    @GetMapping
    public ResponseEntity<List<ResidenciasDTO>> obtenerResidencias() {
        List<ResidenciasDTO> residencias = residenciasService.obtenerTodasLasAsignaciones();
        if (residencias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(residencias, HttpStatus.OK);
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<ResidenciasDTO>> listarPorUsuario(@PathVariable Integer userId) {
        List<ResidenciasDTO> residencias = residenciasService.obtenerAsignacionesPorUsuario(userId);
        if (residencias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(residencias, HttpStatus.OK);
    }

    @PostMapping("/residencia/{residenciaId}/usuario/{userId}")
    public ResponseEntity<String> asignarUsuarioAResidencia(@PathVariable Integer residenciaId,
            @PathVariable Integer userId) {
        try {
            String mensaje = residenciasService.añadirUsuarioAResidencia(residenciaId, userId);
            return new ResponseEntity<>(mensaje, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/residencia/{residenciaId}/usuario/{userId}")
    public ResponseEntity<String> removerUsuarioDeResidencia(@PathVariable Integer residenciaId,
            @PathVariable Integer userId) {
        try {
            String mensaje = residenciasService.eliminarUsuarioDeResidencia(residenciaId, userId);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
