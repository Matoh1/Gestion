package com.example.Residencias.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.Residencias.controller.v2.ResidenciaControllerV2;
import com.example.Residencias.DTO.ResidenciaDTO;

@Component
public class ResidenciaModelAssembler implements RepresentationModelAssembler<ResidenciaDTO, EntityModel<ResidenciaDTO>> {

    @Override
    public EntityModel<ResidenciaDTO> toModel(ResidenciaDTO residencia) {
        return EntityModel.of(residencia,
                linkTo(methodOn(ResidenciaControllerV2.class).porId(residencia.getId())).withSelfRel(),
                linkTo(methodOn(ResidenciaControllerV2.class).todas()).withRel("residencias")
        );
    }
}
