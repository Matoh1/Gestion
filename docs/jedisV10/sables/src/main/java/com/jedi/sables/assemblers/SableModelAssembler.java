package com.jedi.sables.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.jedi.sables.controller.v2.SableController;
import com.jedi.sables.DTO.SableDTO;

@Component
public class SableModelAssembler implements RepresentationModelAssembler<SableDTO, EntityModel<SableDTO>> {

    @Override
    public EntityModel<SableDTO> toModel(SableDTO sable) {
        return EntityModel.of(sable,
                linkTo(methodOn(SableController.class).obtenerColorPorJedi(sable.getJediId())).withSelfRel(),
                linkTo(methodOn(SableController.class).listar()).withRel("sables")
        );
    }
}