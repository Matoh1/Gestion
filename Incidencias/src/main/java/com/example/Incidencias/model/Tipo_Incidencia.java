package com.example.Gestion.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Tipo_Incidencia")
public class Tipo_Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Tipo_Incidencia_ID")
    private Integer id;

    @NotBlank(message = "El tipo de incidencia es obligatorio")
    @Size(min = 3, max = 50, message = "El tipo de incidencia debe contener entre 3 y 50 caracteres")
    @Column(name = "Nombre_Tipo", nullable = false, length = 50)
    private String nombretipo;

    @OneToMany(mappedBy = "tipoIncidencia")
    private List<Incidencia> incidencias;
}
