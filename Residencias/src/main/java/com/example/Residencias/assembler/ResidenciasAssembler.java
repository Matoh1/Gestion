package com.example.Residencias.assembler;

import org.springframework.stereotype.Component;

import com.example.Residencias.DTO.ResidenciasDTO;
import com.example.Residencias.model.Residencias;

@Component
public class ResidenciasAssembler {

    public ResidenciasDTO toDTO(Residencias residencias) {
        ResidenciasDTO dto = new ResidenciasDTO();

        if (residencias.getResidencia() != null) {
            dto.setResidenciaId(residencias.getResidencia().getId());
            dto.setNombreResidencia(residencias.getResidencia().getNombre());
        }

        dto.setUserId(residencias.getUserId());

        return dto;
    }
}
