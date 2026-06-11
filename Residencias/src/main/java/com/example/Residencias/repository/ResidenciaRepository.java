package com.example.Residencias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Residencias.model.Residencia;

@Repository
public interface ResidenciaRepository extends JpaRepository<Residencia, Integer> {

}
