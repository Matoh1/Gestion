package com.jedi.sables.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cristal")
public class Cristal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cristal")
    private Integer id;

    @NotBlank(message = "El color no puede quedar vacío")
    @Size(min = 3, max = 20)
    @Column(name = "color_cristal")
    private String color;

    @NotNull
    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 7, message = "Quantity cannot exceed 100")
    @Column(name = "tamano_cristal")
    private Double tamano;

}
