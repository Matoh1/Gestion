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
public class ResidenciaDTO {

    private Integer id;
    private String nombre;
    private String direccion;
    private Integer comunaId;
    private String nombreComuna;

}
