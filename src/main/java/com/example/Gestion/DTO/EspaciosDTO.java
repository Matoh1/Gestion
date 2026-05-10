package com.example.Gestion.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EspaciosDTO {

    private Integer id;
    private String espacio;
    private String residencia;
}
