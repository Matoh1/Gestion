package com.example.Residencias.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.Residencias.controller.v2.ComunaControllerV2;
import com.example.Residencias.DTO.ComunaDTO;

@Component
public class ComunaModelAssembler implements RepresentationModelAssembler<ComunaDTO, EntityModel<ComunaDTO>> {

    @Override
    public EntityModel<ComunaDTO> toModel(ComunaDTO comuna) {
        return EntityModel.of(comuna,
                linkTo(methodOn(ComunaControllerV2.class).porId(comuna.getId())).withSelfRel(),
                linkTo(methodOn(ComunaControllerV2.class).todas()).withRel("comunas")
        );
    }
}
