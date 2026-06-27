package com.example.Residencias.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.Residencias.controller.v2.ResidenciasControllerV2;
import com.example.Residencias.DTO.ResidenciasDTO;

@Component
public class ResidenciasModelAssembler implements RepresentationModelAssembler<ResidenciasDTO, EntityModel<ResidenciasDTO>> {

    @Override
    public EntityModel<ResidenciasDTO> toModel(ResidenciasDTO asignacion) {
        return EntityModel.of(asignacion,
                linkTo(methodOn(ResidenciasControllerV2.class).todas()).withSelfRel()
        );
    }
}
