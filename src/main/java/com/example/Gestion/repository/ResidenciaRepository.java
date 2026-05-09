package com.example.Gestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Gestion.model.Residencia;

@Repository
public interface ResidenciaRepository extends JpaRepository<Residencia, Integer> {

}
