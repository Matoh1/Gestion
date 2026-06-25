package com.example.Espacios.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.Espacios.DTO.UserDTO;
import com.example.Espacios.model.User;

@Component
public class UserAssembler {

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        
        dto.setId(user.getId());
        dto.setNombre(user.getNombre());
        dto.setApellido(user.getApellido());
        dto.setRut(user.getRut());
        dto.setEmail(user.getEmail());
        dto.setTelefono(user.getTelefono());

        List<String> listaResidencias = new ArrayList<>();
        if (user.getResidenciaId() != null) {
            listaResidencias.add(String.valueOf(user.getResidenciaId()));
        }
        dto.setResidencias(listaResidencias);

        return dto;
    }
}