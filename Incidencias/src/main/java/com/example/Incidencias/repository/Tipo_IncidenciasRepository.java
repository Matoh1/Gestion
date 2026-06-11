package com.example.Gestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Gestion.model.Tipo_Incidencia;

@Repository
public interface Tipo_IncidenciasRepository extends JpaRepository<Tipo_Incidencia, Integer> {

}
