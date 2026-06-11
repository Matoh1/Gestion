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

import com.example.Gestion.DTO.Tipo_IncidenciasDTO;
import com.example.Gestion.model.Tipo_Incidencia;
import com.example.Gestion.service.Tipo_IncidenciasService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tipo_incidencias")
public class Tipo_IncidenciasController {

    @Autowired
    private Tipo_IncidenciasService tipoIncidenciasService;

    @GetMapping
    public ResponseEntity<List<Tipo_IncidenciasDTO>> obtenerTodos() {
        List<Tipo_IncidenciasDTO> tipos = tipoIncidenciasService.obtenerTodos();
        if (tipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tipo_IncidenciasDTO> buscarTipoPorId(@PathVariable Integer id) {
        try {
            Tipo_IncidenciasDTO tipo = tipoIncidenciasService.buscarporID(id);
            return ResponseEntity.ok(tipo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Tipo_Incidencia> agregarTipo(@Valid @RequestBody Tipo_Incidencia tipoIncidencia) {
        try {
            Tipo_Incidencia nuevoTipo = tipoIncidenciasService.guardarTipo_Incidencia(tipoIncidencia);
            return new ResponseEntity<>(nuevoTipo, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTipo(@PathVariable Integer id) {
        String resultado = tipoIncidenciasService.borrarTipo_Incidencia(id);
        if (resultado.startsWith("Error")) {
            return new ResponseEntity<>(resultado, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        }
    }
}
