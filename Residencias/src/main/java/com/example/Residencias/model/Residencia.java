package com.example.Residencias.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Residencia")
public class Residencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_residencia")
    private Integer id;

    @NotBlank(message = "EL nombre no puede estar vacio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    @Column(name = "Nombre_Residencia", nullable = false, length = 50)
    private String nombre;

    @NotBlank(message = "La Direccion es obligatoria")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    @Column(name = "Direccion", nullable = false, length = 50)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "Comuna_ID")
    private Comuna comuna;

    @OneToMany(mappedBy = "residencia")
    private List<Residencias> asignaciones;

}