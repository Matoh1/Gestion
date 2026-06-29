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

import com.example.Incidencias.DTO.Tipo_IncidenciasDTO;
import com.example.Incidencias.assemblers.TipoIncidenciasModelAssembler;
import com.example.Incidencias.model.Tipo_Incidencia;
import com.example.Incidencias.service.Tipo_IncidenciasService;

import jakarta.validation.Valid;

@RestController("tipoIncidenciasControllerV2")
@RequestMapping("/api/v2/tipo_incidencias")
public class Tipo_IncidenciasController {

    @Autowired
    private Tipo_IncidenciasService tipoIncidenciasService;

    @Autowired
    private TipoIncidenciasModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Tipo_IncidenciasDTO>>> obtenerTodos() {
        List<EntityModel<Tipo_IncidenciasDTO>> tipos = tipoIncidenciasService.obtenerTodos().stream()
                .map(assembler::toModel)
                .toList();

        if (tipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                tipos,
                linkTo(methodOn(Tipo_IncidenciasController.class).obtenerTodos()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Tipo_IncidenciasDTO>> buscarTipoPorId(@PathVariable Integer id) {
        try {
            Tipo_IncidenciasDTO dto = tipoIncidenciasService.buscarporID(id);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Tipo_IncidenciasDTO>> agregarTipo(@Valid @RequestBody Tipo_Incidencia tipoIncidencia) {
        try {
            Tipo_Incidencia nuevoTipo = tipoIncidenciasService.guardarTipo_Incidencia(tipoIncidencia);
            Tipo_IncidenciasDTO dto = tipoIncidenciasService.buscarporID(nuevoTipo.getId());
            return ResponseEntity
                    .created(linkTo(methodOn(Tipo_IncidenciasController.class).buscarTipoPorId(dto.getId())).toUri())
                    .body(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTipo(@PathVariable Integer id) {
        String resultado = tipoIncidenciasService.borrarTipo_Incidencia(id);
        if (resultado.startsWith("Error")) {
            return ResponseEntity.badRequest().body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }
}
