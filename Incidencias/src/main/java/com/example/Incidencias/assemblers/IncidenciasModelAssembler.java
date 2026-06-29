package com.example.Incidencias.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.Incidencias.DTO.IncidenciasDTO;
import com.example.Incidencias.controller.v2.IncidenciaController;
import com.example.Incidencias.controller.v2.IncidenciasController;

@Component
public class IncidenciasModelAssembler implements RepresentationModelAssembler<IncidenciasDTO, EntityModel<IncidenciasDTO>> {

    @Override
    public EntityModel<IncidenciasDTO> toModel(IncidenciasDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(IncidenciasController.class).buscarIncidenciasPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(IncidenciasController.class).obtenerTodos()).withRel("reportes"),
                linkTo(methodOn(IncidenciaController.class).obtenerTodos()).withRel("detalles"));
    }
}
