package com.example.Incidencias.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.Incidencias.DTO.IncidenciaDTO;
import com.example.Incidencias.controller.v2.IncidenciaController;
import com.example.Incidencias.controller.v2.IncidenciasController;
import com.example.Incidencias.controller.v2.Tipo_IncidenciasController;

@Component
public class IncidenciaModelAssembler implements RepresentationModelAssembler<IncidenciaDTO, EntityModel<IncidenciaDTO>> {

    @Override
    public EntityModel<IncidenciaDTO> toModel(IncidenciaDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(IncidenciaController.class).buscarIncidenciaPorId(dto.getId())).withSelfRel(),
                linkTo(methodOn(IncidenciaController.class).obtenerTodos()).withRel("detalles"),
                linkTo(methodOn(IncidenciasController.class).buscarIncidenciasPorId(dto.getIncidenciasId())).withRel("reporte"),
                linkTo(methodOn(Tipo_IncidenciasController.class).buscarTipoPorId(dto.getTipoIncidenciaId())).withRel("tipo"));
    }
}
