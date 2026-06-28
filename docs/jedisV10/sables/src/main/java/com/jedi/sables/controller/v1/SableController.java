package com.jedi.sables.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jedi.sables.DTO.SableDTO;
import com.jedi.sables.model.Sables;
import com.jedi.sables.service.SableService;

import jakarta.validation.Valid;

@RestController("sableControllerV1")
@RequestMapping("/api/v1/sables")
public class SableController {

    @Autowired
    private SableService sableService;

    @GetMapping
    public ResponseEntity<?> listar() {
        return new ResponseEntity<>(sableService.obtenerTodos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Sables sable) {
        try {
            SableDTO creado = sableService.guardar(sable);
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Fallo en los sistemas de la Forja", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/buscar-por-jedi/{jediId}")
    public ResponseEntity<SableDTO> obtenerColorPorJedi(@PathVariable Integer jediId) {
        SableDTO sable = sableService.buscarPorJedi(jediId);
        
        if (sable == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(sable);
    }
}

