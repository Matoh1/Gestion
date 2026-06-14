package com.example.Incidencias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Incidencias.model.Tipo_Incidencia;

@Repository
public interface Tipo_IncidenciasRepository extends JpaRepository<Tipo_Incidencia, Integer> {

}
