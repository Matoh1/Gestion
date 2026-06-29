package com.example.Incidencias.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

// Espejo del DTO que expone el microservicio Residencias en /api/v1/residencia/{id}.
// Solo declaramos los campos que necesitamos leer del JSON remoto (Jackson ignora el resto).
// Equivale al SableExternoDTO del profe, que recibe el objeto de otro microservicio.
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResidenciaExternoDTO {
    private Integer id;
    private String nombre;
    private String direccion;
}
