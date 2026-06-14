package com.example.Incidencias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Incidencias.model.Incidencias;

@Repository
public interface IncidenciasRepository extends JpaRepository<Incidencias, Integer> {

}
