package com.example.Residencias.model;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_region")
    private Integer id;

    @NotBlank(message = "La Region es obligatoria")
    @Size(min = 5, max = 50, message = "La Region debe tener al menos 5 caracteres")
    @Column(name = "Nombre_Region", nullable = false, length = 50)
    private String nombreregion;

    @OneToMany(mappedBy = "region")
    private List<Comuna> comunas;
}
