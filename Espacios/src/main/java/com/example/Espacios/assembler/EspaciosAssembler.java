package com.example.Espacios.assembler;

import org.springframework.stereotype.Component;

import com.example.Espacios.DTO.EspaciosDTO;
import com.example.Espacios.model.Espacios;

@Component
public class EspaciosAssembler {

    public EspaciosDTO toDTO(Espacios espacios) {
        EspaciosDTO dto = new EspaciosDTO();
        
        dto.setId(espacios.getId());
        dto.setResidenciaId(espacios.getResidenciaId());

        if (espacios.getEspacio() != null) {
            dto.setEspacio(espacios.getEspacio().getNombre());
        }

        return dto;
    }
}