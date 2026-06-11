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

import com.example.Residencias.DTO.RegionDTO;
import com.example.Residencias.model.Region;
import com.example.Residencias.service.RegionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    public ResponseEntity<List<RegionDTO>> obtenerRegiones() {
        List<RegionDTO> regiones = regionService.obtenerTodos();
        if (regiones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(regiones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> buscarPorId(@PathVariable Integer id) {
        try {
            RegionDTO region = regionService.buscarporID(id);
            return new ResponseEntity<>(region, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Region> agregarRegion(@Valid @RequestBody Region region) {
        try {
            Region nuevaRegion = regionService.guardarRegion(region);
            return new ResponseEntity<>(nuevaRegion, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{regionId}/comuna/{comunaId}")
    public ResponseEntity<String> añadirComunaARegion(@PathVariable Integer regionId, @PathVariable Integer comunaId) {
        try {
            String mensaje = regionService.añadirComunaARegion(regionId, comunaId);
            return new ResponseEntity<>(mensaje, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Region> eliminarRegion(@PathVariable Integer id) {
        String resultado = regionService.eliminar(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
