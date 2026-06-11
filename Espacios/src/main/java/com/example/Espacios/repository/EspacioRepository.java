package com.example.Espacios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import package com.example.Espacios.model;


@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Integer> {

}
