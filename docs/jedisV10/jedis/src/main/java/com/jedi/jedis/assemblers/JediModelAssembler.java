package com.jedi.jedis.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.jedi.jedis.Controller.v2.JediController;
import com.jedi.jedis.DTO.JediDTO; 

@Component
public class JediModelAssembler implements RepresentationModelAssembler<JediDTO, EntityModel<JediDTO>> {

    @Override
    public EntityModel<JediDTO> toModel(JediDTO jedi) {
        return EntityModel.of(jedi,
                linkTo(methodOn(JediController.class).porId(jedi.getId())).withSelfRel(),
                linkTo(methodOn(JediController.class).todas()).withRel("jedis")
        );
    }
}