package com.example.Gestion.model;

import java.time.LocalDate;
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
import jakarta.validation.constraints.NotNull;
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
@Table(name = "Incidencias")
public class Incidencias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Incidencias_ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Residencia_ID", nullable = false)
    private Residencia residencia;

    @NotBlank(message = "El titulo del reporte es obligatorio")
    @Size(min = 5, max = 200, message = "El titulo del reporte debe contener entre 5 y 200 caracteres")
    @Column(name = "Titulo_Reporte", nullable = false, length = 200)
    private String tituloReporte;

    @NotNull(message = "La fecha del reporte es obligatoria")
    @Column(name = "Fecha_Reporte", nullable = false)
    private LocalDate fechaReporte;

    @NotBlank(message = "La prioridad es obligatoria")
    @Size(min = 3, max = 50, message = "La prioridad debe contener entre 3 y 50 caracteres")
    @Column(name = "Prioridad", nullable = false, length = 50)
    private String prioridad;

    @OneToMany(mappedBy = "incidencias")
    private List<Incidencia> incidencias;
}
