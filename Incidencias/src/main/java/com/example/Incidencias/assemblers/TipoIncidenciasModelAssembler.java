package com.example.Incidencias.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.Incidencias.DTO.Tipo_IncidenciasDTO;
import com.example.Incidencias.controller.v2.IncidenciaController;
import com.example.Incidencias.controller.v2.Tipo_IncidenciasController;

@Component
public class TipoIncidenciasModelAssembler implements RepresentationModelAssembler<Tipo_IncidenciasDTO, EntityModel<Tipo_IncidenciasDTO>> {

    @Override
    public EntityModel<Tipo_IncidenciasDTO> toModel(Tipo_IncidenciasDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(Tipo_IncidenciasController.class).buscarTipoPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(Tipo_IncidenciasController.class).obtenerTodos()).withRel("tipos"),
                linkTo(methodOn(IncidenciaController.class).obtenerTodos()).withRel("detalles"));
    }
}
