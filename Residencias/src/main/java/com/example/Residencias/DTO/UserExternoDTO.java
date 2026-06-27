package com.example.Residencias.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserExternoDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String rut;
}
