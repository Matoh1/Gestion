package com.example.Incidencias.DTO;

import lombok.Data;

@Data
public class IncidenciaDTO {

    private Integer id;
    private Integer incidenciasId;
    private String tituloReporte;
    private Integer tipoIncidenciaId;
    private String nombreTipo;
    private String descripcion;
    private String estado;
}
