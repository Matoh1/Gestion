package com.jedi.sables.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jedi.sables.DTO.SableDTO;
import com.jedi.sables.model.Sables;
import com.jedi.sables.repository.SableRepository;

@Service
public class SableService {

    @Autowired
    private SableRepository sableRepository;

    public List<SableDTO> obtenerTodos() {
        List<SableDTO> dtos = new ArrayList<>();
        for (Sables s : sableRepository.findAll()) {
            dtos.add(convertirADTO(s));
        }
        return dtos;
    }

    public SableDTO guardar(Sables nuevoSable) {
        Sables guardado = sableRepository.save(nuevoSable);
        return convertirADTO(guardado);
    }

    private SableDTO convertirADTO(Sables s) {
        SableDTO dto = new SableDTO();
        dto.setId(s.getId());
        dto.setColor(s.getColor());
        dto.setCristalKyber(s.getCristal_kaiber());
        dto.setJediId(s.getJedi_id());
        return dto;
    }
}
