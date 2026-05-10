package com.example.Gestion.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EspacioDTO {

    private Integer id;
    private String nombre;
    private String tipo;
    private Integer capacidad;
    private List<String> Espacios;
}