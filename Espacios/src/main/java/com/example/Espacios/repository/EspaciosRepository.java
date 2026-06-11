package com.example.Gestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Gestion.model.Espacios;

@Repository
public interface EspaciosRepository extends JpaRepository<Espacios, Integer> {

}
