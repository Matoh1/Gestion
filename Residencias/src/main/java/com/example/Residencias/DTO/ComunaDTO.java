package com.example.Residencias.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComunaDTO {

    private Integer id;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    private Integer regionId;
    @NotBlank(message = "El nombre de la región no puede estar vacío")
    private String region;
    private List<String> residencia;

}
