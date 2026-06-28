package com.jedi.sables.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.jedi.sables.controller.v2.CristalController;
import com.jedi.sables.model.Cristal;

@Component
public class CristalModelAssembler implements RepresentationModelAssembler<Cristal, EntityModel<Cristal>> {

    @Override
    public EntityModel<Cristal> toModel(Cristal cristal) {
        return EntityModel.of(cristal,
                linkTo(methodOn(CristalController.class).porId(cristal.getId())).withSelfRel()
        );
    }
}