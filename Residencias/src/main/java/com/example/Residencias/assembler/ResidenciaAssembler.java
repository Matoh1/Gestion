package com.example.Residencias.assembler;

import org.springframework.stereotype.Component;

import com.example.Residencias.DTO.ResidenciaDTO;
import com.example.Residencias.model.Residencia;

@Component
public class ResidenciaAssembler {

    public ResidenciaDTO toDTO(Residencia residencia) {
        ResidenciaDTO dto = new ResidenciaDTO();
        dto.setId(residencia.getId());
        dto.setNombre(residencia.getNombre());
        dto.setDireccion(residencia.getDireccion());

        if (residencia.getComuna() != null) {
            dto.setComunaId(residencia.getComuna().getId());
            dto.setNombreComuna(residencia.getComuna().getNombrecomuna());
        }
        return dto;
    }
}
