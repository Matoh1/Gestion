package com.example.Incidencias.controller.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Incidencias.DTO.IncidenciasDTO;
import com.example.Incidencias.assemblers.IncidenciasModelAssembler;
import com.example.Incidencias.model.Incidencias;
import com.example.Incidencias.service.IncidenciasService;

import jakarta.validation.Valid;

@RestController("incidenciasControllerV2")
@RequestMapping("/api/v2/incidencias")
public class IncidenciasController {

    @Autowired
    private IncidenciasService incidenciasService;

    @Autowired
    private IncidenciasModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<IncidenciasDTO>>> obtenerTodos() {
        List<EntityModel<IncidenciasDTO>> reportes = incidenciasService.obtenerTodos().stream()
                .map(assembler::toModel)
                .toList();

        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                reportes,
                linkTo(methodOn(IncidenciasController.class).obtenerTodos()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<IncidenciasDTO>> buscarIncidenciasPorId(@PathVariable Integer id) {
        try {
            IncidenciasDTO dto = incidenciasService.buscarporID(id);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<IncidenciasDTO>> agregarIncidencias(@Valid @RequestBody Incidencias incidencias) {
        try {
            Incidencias nuevoReporte = incidenciasService.guardarIncidencias(incidencias);
            IncidenciasDTO dto = incidenciasService.buscarporID(nuevoReporte.getId());
            return ResponseEntity
                    .created(linkTo(methodOn(IncidenciasController.class).buscarIncidenciasPorId(dto.getId())).toUri())
                    .body(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarIncidencias(@PathVariable Integer id) {
        String resultado = incidenciasService.borrarIncidencias(id);
        if (resultado.startsWith("Error")) {
            return ResponseEntity.badRequest().body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }
}
