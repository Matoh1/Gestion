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

import com.example.Residencias.DTO.RegionDTO;
import com.example.Residencias.assembler.RegionModelAssembler;
import com.example.Residencias.model.Region;
import com.example.Residencias.service.RegionService;
import com.example.Residencias.service.ResidenciaValidaciones;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("regionControllerV2")
@RequestMapping("/api/v2/region")
public class RegionControllerV2 {

    @Autowired
    private RegionService regionService;

    @Autowired
    private ResidenciaValidaciones residenciaValidaciones;

    @Autowired
    private RegionModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<RegionDTO>>> todas() {
        List<EntityModel<RegionDTO>> regiones = regionService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (regiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                regiones,
                linkTo(methodOn(RegionControllerV2.class).todas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RegionDTO>> porId(@PathVariable Integer id) {
        try {
            RegionDTO dto = regionService.buscarporID(id);
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<RegionDTO>> crear(@Valid @RequestBody Region region) {
        try {
            Region guardada = regionService.guardarRegion(region);
            RegionDTO dto = residenciaValidaciones.convertirRegionADTO(guardada);
            return ResponseEntity
                    .created(linkTo(methodOn(RegionControllerV2.class).porId(dto.getId())).toUri())
                    .body(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{regionId}/comuna/{comunaId}")
    public ResponseEntity<String> añadirComuna(@PathVariable Integer regionId, @PathVariable Integer comunaId) {
        try {
            String mensaje = regionService.añadirComunaARegion(regionId, comunaId);
            return ResponseEntity.ok(mensaje);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        String resultado = regionService.eliminar(id);
        if (resultado.contains("exitosamente")) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
