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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Residencias.DTO.ResidenciaDTO;
import com.example.Residencias.assembler.ResidenciaModelAssembler;
import com.example.Residencias.model.Residencia;
import com.example.Residencias.service.ResidenciaService;
import com.example.Residencias.service.ResidenciaValidaciones;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("residenciaControllerV2")
@RequestMapping("/api/v2/residencia")
public class ResidenciaControllerV2 {

    @Autowired
    private ResidenciaService residenciaService;

    @Autowired
    private ResidenciaValidaciones residenciaValidaciones;

    @Autowired
    private ResidenciaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ResidenciaDTO>>> todas() {
        List<EntityModel<ResidenciaDTO>> residencias = residenciaService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (residencias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                residencias,
                linkTo(methodOn(ResidenciaControllerV2.class).todas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ResidenciaDTO>> porId(@PathVariable Integer id) {
        try {
            ResidenciaDTO dto = residenciaService.buscarporID(id);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ResidenciaDTO>> crear(@Valid @RequestBody Residencia residencia) {
        try {
            Residencia guardada = residenciaService.guardarResidencia(residencia);
            ResidenciaDTO dto = residenciaValidaciones.convertirResidenciaADTO(guardada);
            return ResponseEntity
                    .created(linkTo(methodOn(ResidenciaControllerV2.class).porId(dto.getId())).toUri())
                    .body(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ResidenciaDTO>> actualizar(@PathVariable Integer id,
            @Valid @RequestBody Residencia residencia) {
        try {
            Residencia actualizada = residenciaService.actualizarResidencia(id, residencia);
            ResidenciaDTO dto = residenciaValidaciones.convertirResidenciaADTO(actualizada);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ResidenciaDTO>> reemplazar(@PathVariable Integer id,
            @Valid @RequestBody Residencia residencia) {
        try {
            Residencia actualizada = residenciaService.actualizarResidencia(id, residencia);
            ResidenciaDTO dto = residenciaValidaciones.convertirResidenciaADTO(actualizada);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        String resultado = residenciaService.eliminar(id);
        if (resultado.contains("exitosamente")) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
