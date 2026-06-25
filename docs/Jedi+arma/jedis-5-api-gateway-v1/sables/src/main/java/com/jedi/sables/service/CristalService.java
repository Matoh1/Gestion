package com.jedi.sables.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jedi.sables.model.Cristal;
import com.jedi.sables.repository.CristalRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class CristalService {
    
    @Autowired
    private CristalRepository cristalRepository;

    public Cristal buscarPorId(Integer id){
        return cristalRepository.findById(id).orElseThrow(() -> new RuntimeException("Cristal no encontrado"));
    }
}
