package com.example.Residencias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Residencias.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer>{

}
