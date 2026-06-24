package com.example.Residencias.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.Residencias.DTO.RegionDTO;
import com.example.Residencias.model.Comuna;
import com.example.Residencias.model.Region;

@Component
public class RegionAssembler {

    public RegionDTO toDTO(Region region) {
        RegionDTO dto = new RegionDTO();
        dto.setId(region.getId());
        dto.setNombre(region.getNombreregion());

        List<String> nombresComunas = new ArrayList<>();
        if (region.getComunas() != null) {
            for (Comuna comuna : region.getComunas()) {
                nombresComunas.add(comuna.getNombrecomuna());
            }
        }
        dto.setComunas(nombresComunas);
        return dto;
    }
}
