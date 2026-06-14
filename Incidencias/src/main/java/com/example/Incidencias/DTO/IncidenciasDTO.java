package com.example.Incidencias.DTO;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class IncidenciasDTO {

    private Integer id;
    private Integer residenciaId;
    private String nombreResidencia;
    private String tituloReporte;
    private LocalDate fechaReporte;
    private String prioridad;
    private List<String> incidencias;
}
