package com.example.Gestion.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncidenciaDTO {

    private Integer id;
    private Integer incidenciasId;
    private String tituloReporte;
    private Integer tipoIncidenciaId;
    private String nombreTipo;
    private String descripcion;
    private String estado;
}
