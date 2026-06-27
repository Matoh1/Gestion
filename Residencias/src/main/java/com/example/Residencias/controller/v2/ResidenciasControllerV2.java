package com.example.Residencias.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Residencias.DTO.ResidenciasDTO;
import com.example.Residencias.assembler.ResidenciasModelAssembler;
import com.example.Residencias.service.ResidenciasService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("residenciasControllerV2")
@RequestMapping("/api/v2/residencias")
public class ResidenciasControllerV2 {

    @Autowired
    private ResidenciasService residenciasService;

    @Autowired
    private ResidenciasModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ResidenciasDTO>>> todas() {
        List<EntityModel<ResidenciasDTO>> asignaciones = residenciasService.obtenerTodasLasAsignaciones().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (asignaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                asignaciones,
                linkTo(methodOn(ResidenciasControllerV2.class).todas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/usuario/{userId}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ResidenciasDTO>>> porUsuario(@PathVariable Integer userId) {
        List<EntityModel<ResidenciasDTO>> asignaciones = residenciasService.obtenerAsignacionesPorUsuario(userId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (asignaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                asignaciones,
                linkTo(methodOn(ResidenciasControllerV2.class).porUsuario(userId)).withSelfRel()
        ));
    }

    @PostMapping("/residencia/{residenciaId}/usuario/{userId}")
    public ResponseEntity<String> asignar(@PathVariable Integer residenciaId, @PathVariable Integer userId) {
        try {
            String mensaje = residenciasService.añadirUsuarioAResidencia(residenciaId, userId);
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/residencia/{residenciaId}/usuario/{userId}")
    public ResponseEntity<String> remover(@PathVariable Integer residenciaId, @PathVariable Integer userId) {
        try {
            String mensaje = residenciasService.eliminarUsuarioDeResidencia(residenciaId, userId);
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
