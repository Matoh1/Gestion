package com.jedi.sables.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jedi.sables.DTO.SableDTO;
import com.jedi.sables.model.Cristal;
import com.jedi.sables.model.Sables;
import com.jedi.sables.repository.SableRepository;

@Service
public class SableService {

    @Autowired
    private SableRepository sableRepository;

    @Autowired
    private CristalService cristalService;

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

    public SableDTO buscarPorJedi(Integer jedi_id) {
        Sables sable = sableRepository.findByJediId(jedi_id);
        if (sable == null) {
            return null;
        }
        return convertirADTO(sable);
    }

    private SableDTO convertirADTO(Sables s) {
        if (s == null) return null;

        SableDTO dto = new SableDTO();
        dto.setId(s.getId());
        dto.setJediId(s.getJediId());

        try {
            Cristal cristalJedi = cristalService.buscarPorId(s.getCristal().getId());
            dto.setColor(cristalJedi.getColor());
        } catch (Exception e) {
            dto.setColor(null);
        }
        return dto;
    }
}
