package com.example.Espacios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Espacios.model.Espacios;

@Repository
public interface EspaciosRepository extends JpaRepository<Espacios, Integer> {
    Espacios findByEsResId(Integer espacioId, Integer residenciaId);
}
