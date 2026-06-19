package com.example.Residencias.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Residencias.model.Residencias;

@Repository
public interface ResidenciasRepository extends JpaRepository<Residencias, Integer> {
    Optional<Residencias> findByResidencia_IdAndUserId(Integer residenciaId, Integer userId);

    List<Residencias> findByUserId(Integer userId);

}
