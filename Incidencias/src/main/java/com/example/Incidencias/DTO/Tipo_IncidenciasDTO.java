package com.example.Incidencias.DTO;

import java.util.List;

import lombok.Data;

@Data
public class Tipo_IncidenciasDTO {

    private Integer id;
    private String nombre;
    private List<String> incidencias;
}
