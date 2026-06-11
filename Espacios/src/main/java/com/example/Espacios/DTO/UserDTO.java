package com.example.Espacios.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {

    private Integer id;
    private String nombre;
    private String apellido;
    private String rut;
    private String email;
    private String telefono;
    private List<String> residencias;

}
