package com.jedi.sables.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jedi.sables.model.Cristal;

@Repository
public interface CristalRepository extends JpaRepository<Cristal, Integer> {
}