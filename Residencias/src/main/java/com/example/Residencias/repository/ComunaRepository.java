package com.example.Residencias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Residencias.model.Comuna;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Integer>{

}
