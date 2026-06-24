package com.example.Espacios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_ID")
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 40, message = "El nombre debe contener entre 3 y 40 caracteres")
    @Column(nullable = false, length = 40)
    private String nombre;

    @Size(min = 2, max = 50, message = "El apellido debe contener entre 2 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String apellido;

    @NotBlank(message = "Se requere el rut")
    @Size(min = 9, max = 11, message = "El rut es obligatorio, no uses puntos solo guion")
    @Column(nullable = false, length = 11)
    private String rut;

    @Size(min = 13, max = 45, message = "La direccion de correo electronico debe contener entre 13 y 45 caracteres")
    @Email(message = "El correo electronico debe ser valido")
    @Column(nullable = false, length = 45, unique = true)
    private String email;

    @Size(min = 10, max = 13, message = "El numero de telefono debe contener entre 10 y 13 caracteres, incluyendo el identificador '+' y el/los numero(s)")
    private String telefono;

    @NotNull(message = "La residencia es obligatoria")
    @Column(name = "Residencia_ID", nullable = false)
    private Integer residenciaId;
}
