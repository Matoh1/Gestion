package com.jedi.sables.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jedi.sables.model.Sables;

@Repository
public interface SableRepository extends JpaRepository<Sables, Integer> {
    Sables findByJediId(Integer jediId);
}