package com.example.Residencias.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class ResidenciasDTO {

    private Integer userId;
    private Integer residenciaId;
    private String nombreUsuario;
    private String nombreResidencia;

}
