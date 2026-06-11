package com.example.Gestion.DTO;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncidenciasDTO {

    private Integer id;
    private Integer residenciaId;
    private String nombreResidencia;
    private String tituloReporte;
    private LocalDate fechaReporte;
    private String prioridad;
    private List<String> incidencias;
}
