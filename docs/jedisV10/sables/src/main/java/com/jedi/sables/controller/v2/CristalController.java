package com.jedi.sables.controller.v2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jedi.sables.assemblers.CristalModelAssembler;
import com.jedi.sables.model.Cristal;
import com.jedi.sables.service.CristalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController("cristalControllerV2")
@RequestMapping("/api/v2/cristales")
public class CristalController {

    @Autowired
    private CristalService cristalService;

    @Autowired
    private CristalModelAssembler assembler;

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Cristal>> porId(@PathVariable Integer id) {
        try {
            Cristal cristal = cristalService.buscarPorId(id);
            if (cristal == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(cristal));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}