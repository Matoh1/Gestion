package com.jedi.sables.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "sables")
public class Sables {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Debes definir un bando (Luminoso / Oscuro)")
    @Size(min = 3, max = 30)
    private String bando;

    @NotNull(message = "El jedi siempre debe estar")
    private Integer jediId;

    @ManyToOne
    @JoinColumn(name = "id_cristal_fk") 
    private Cristal cristal;
}
