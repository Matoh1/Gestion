package com.example.Espacios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Espacios.model.Espacio;

@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Integer> {

}
