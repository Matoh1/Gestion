package com.example.Espacios.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.Espacios.DTO.EspacioDTO;
import com.example.Espacios.model.Espacio;
import com.example.Espacios.model.Espacios;

@Component
public class EspacioAssembler {

    public EspacioDTO toDTO(Espacio espacio) {
        EspacioDTO dto = new EspacioDTO();
        
        dto.setId(espacio.getId());
        dto.setNombre(espacio.getNombre());
        dto.setTipo(espacio.getTipo());
        dto.setCapacidad(espacio.getCapacidad());

        List<String> nombresEspacios = new ArrayList<>();
        if (espacio.getEspacios() != null) {
            for (Espacios e : espacio.getEspacios()) {
                nombresEspacios.add(String.valueOf(e.getId()));
            }
        }
        dto.setEspacios(nombresEspacios);

        return dto;
    }
}