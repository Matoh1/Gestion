package com.example.Gestion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Incidencia")
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Incidencia_ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Incidencias_ID", nullable = false)
    private Incidencias incidencias;

    @ManyToOne
    @JoinColumn(name = "Tipo_Incidencia_ID", nullable = false)
    private Tipo_Incidencia tipoIncidencia;

    @NotBlank(message = "La descripcion es obligatoria")
    @Column(name = "Descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @NotBlank(message = "El estado es obligatorio")
    @Size(min = 3, max = 50, message = "El estado debe contener entre 3 y 50 caracteres")
    @Column(name = "Estado", nullable = false, length = 50)
    private String estado;
}
