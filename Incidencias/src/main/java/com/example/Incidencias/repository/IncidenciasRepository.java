package com.example.Gestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Gestion.model.Incidencias;

@Repository
public interface IncidenciasRepository extends JpaRepository<Incidencias, Integer> {

}
