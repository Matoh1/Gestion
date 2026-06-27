package com.example.Residencias.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResidenciasDTO {

    private Integer userId;
    private Integer residenciaId;
    private String nombreUsuario;
    private String nombreResidencia;

}
