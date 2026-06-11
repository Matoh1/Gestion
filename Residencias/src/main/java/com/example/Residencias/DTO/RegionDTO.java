package com.example.Residencias.DTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegionDTO {

    private Integer id;
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    private List<String> comunas;

}
