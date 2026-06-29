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

import com.example.Incidencias.DTO.IncidenciaDTO;
import com.example.Incidencias.assemblers.IncidenciaModelAssembler;
import com.example.Incidencias.model.Incidencia;
import com.example.Incidencias.service.IncidenciaService;

import jakarta.validation.Valid;

@RestController("incidenciaControllerV2")
@RequestMapping("/api/v2/incidencia")
public class IncidenciaController {

    @Autowired
    private IncidenciaService incidenciaService;

    @Autowired
    private IncidenciaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<IncidenciaDTO>>> obtenerTodos() {
        List<EntityModel<IncidenciaDTO>> incidencias = incidenciaService.obtenerTodos().stream()
                .map(assembler::toModel)
                .toList();

        if (incidencias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                incidencias,
                linkTo(methodOn(IncidenciaController.class).obtenerTodos()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<IncidenciaDTO>> buscarIncidenciaPorId(@PathVariable Integer id) {
        try {
            IncidenciaDTO dto = incidenciaService.buscarporID(id);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<IncidenciaDTO>> agregarIncidencia(@Valid @RequestBody Incidencia incidencia) {
        try {
            Incidencia nuevaIncidencia = incidenciaService.guardarIncidencia(incidencia);
            IncidenciaDTO dto = incidenciaService.buscarporID(nuevaIncidencia.getId());
            return ResponseEntity
                    .created(linkTo(methodOn(IncidenciaController.class).buscarIncidenciaPorId(dto.getId())).toUri())
                    .body(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarIncidencia(@PathVariable Integer id) {
        String resultado = incidenciaService.borrarIncidencia(id);
        if (resultado.startsWith("Error")) {
            return ResponseEntity.badRequest().body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }
}
