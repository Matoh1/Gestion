package com.example.Residencias.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class ResidenciaDTO {

    private Integer id;
    private String nombre;
    private String direccion;
    private Integer comunaId;
    private String nombreComuna;

}
