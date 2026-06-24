package com.example.Residencias.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.Residencias.DTO.ComunaDTO;
import com.example.Residencias.model.Comuna;
import com.example.Residencias.model.Residencia;

@Component
public class ComunaAssembler {

    public ComunaDTO toDTO(Comuna comuna) {
        ComunaDTO dto = new ComunaDTO();
        dto.setId(comuna.getId());
        dto.setNombre(comuna.getNombrecomuna());

        if (comuna.getRegion() != null) {
            dto.setRegionId(comuna.getRegion().getId());
            dto.setRegion(comuna.getRegion().getNombreregion());
        }

        List<String> nombresResidencias = new ArrayList<>();
        if (comuna.getResidencias() != null) {
            for (Residencia nexo : comuna.getResidencias()) {
                nombresResidencias.add(nexo.getNombre());
            }
        }
        dto.setResidencia(nombresResidencias);
        return dto;
    }
}
