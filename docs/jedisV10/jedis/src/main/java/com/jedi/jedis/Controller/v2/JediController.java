package com.jedi.jedis.Controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jedi.jedis.DTO.JediDTO;
import com.jedi.jedis.assemblers.JediModelAssembler;
import com.jedi.jedis.model.Jedi;
import com.jedi.jedis.service.JediService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("jediControllerV2")
@RequestMapping("/api/v2/jedis")
public class JediController {

    @Autowired
    private JediService jediService;

    @Autowired
    private JediModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<JediDTO>>> todas() {
        List<EntityModel<JediDTO>> jedis = jediService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (jedis.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                jedis,
                linkTo(methodOn(JediController.class).todas()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<JediDTO>> porId(@PathVariable Integer id) {
        try {
            JediDTO dto = jediService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<JediDTO>> registrar(@Valid @RequestBody Jedi jedi) {
        try {
            JediDTO newJedi = jediService.guardar(jedi);
            return ResponseEntity
                    .created(linkTo(methodOn(JediController.class).porId(newJedi.getId())).toUri())
                    .body(assembler.toModel(newJedi));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}