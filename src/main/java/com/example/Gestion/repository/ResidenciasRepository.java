package com.example.Gestion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Gestion.model.Residencias;

@Repository
public interface ResidenciasRepository extends JpaRepository<Residencias, Integer> {
    Optional<Residencias> findByResidencia_IdAndUser_Id(Integer residenciaId, Integer userId);

    List<Residencias> findByUser_Id(Integer userId);

}
