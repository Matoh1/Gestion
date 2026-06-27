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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Residencias.DTO.ComunaDTO;
import com.example.Residencias.assembler.ComunaModelAssembler;
import com.example.Residencias.model.Comuna;
import com.example.Residencias.service.ComunaService;
import com.example.Residencias.service.ResidenciaValidaciones;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("comunaControllerV2")
@RequestMapping("/api/v2/comuna")
public class ComunaControllerV2 {

    @Autowired
    private ComunaService comunaService;

    @Autowired
    private ResidenciaValidaciones residenciaValidaciones;

    @Autowired
    private ComunaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ComunaDTO>>> todas() {
        List<EntityModel<ComunaDTO>> comunas = comunaService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (comunas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                comunas,
                linkTo(methodOn(ComunaControllerV2.class).todas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ComunaDTO>> porId(@PathVariable Integer id) {
        try {
            ComunaDTO dto = comunaService.buscarporID(id);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ComunaDTO>> crear(@Valid @RequestBody Comuna comuna) {
        try {
            Comuna guardada = comunaService.guardarComuna(comuna);
            ComunaDTO dto = residenciaValidaciones.convertirComunaADTO(guardada);
            return ResponseEntity
                    .created(linkTo(methodOn(ComunaControllerV2.class).porId(dto.getId())).toUri())
                    .body(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{comunaId}/residencia/{residenciaId}")
    public ResponseEntity<String> añadirResidencia(@PathVariable Integer comunaId, @PathVariable Integer residenciaId) {
        try {
            String mensaje = comunaService.añadirResidenciaAComuna(comunaId, residenciaId);
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        String resultado = comunaService.eliminar(id);
        if (resultado.contains("exitosamente")) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
