package com.example.Espacios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Espacios.DTO.EspaciosDTO;
import com.example.Espacios.DTO.ResidenciaExternaDTO;
import com.example.Espacios.model.Espacio;
import com.example.Espacios.model.Espacios;
import com.example.Espacios.repository.EspacioRepository;
import com.example.Espacios.repository.EspaciosRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EspaciosService {

    @Autowired
    private EspaciosRepository espaciosRepository;

    @Autowired
    private EspacioRepository espacioRepository;

    @Autowired
    private EspaciosValidaciones espaciosValidaciones;

    // todos
    public List<EspaciosDTO> obtenerTodos() {
        return espaciosRepository.findAll().stream()
                .map(espaciosValidaciones::convertirEspaciosADTO)
                .toList();
    }

    // por id
    public EspaciosDTO buscarporID(Integer Id) {
        Espacios espacios = espaciosRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("No se encontro La Union con la ID" + Id));
        return espaciosValidaciones.convertirEspaciosADTO(espacios);
    }

    // guardar
    public Espacios guardarEspacios(Integer espacioId, Integer residenciaId) {
        Espacio espacio = espacioRepository.findById(espacioId)
                .orElseThrow(() -> new RuntimeException("No se encontro el Espacio con la ID" + espacioId));

        ResidenciaExternaDTO residencia = espaciosValidaciones.obtenerResidenciaExterna(residenciaId);

        if (residencia == null) {
            throw new RuntimeException("No se encontro la Residencia con la ID" + residenciaId);
        }

        Espacios espacios = new Espacios();
        espacios.setEspacio(espacio);
        espacios.setResidenciaId(residenciaId);

        return espaciosRepository.save(espacios);
    }

    // borrar vinculo
    public String eliminarVinculo(Integer Id) {
        Espacios espacios = espaciosRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("No se encontro vinculo con la ID" + Id));

        espaciosRepository.delete(espacios);
        return "El vinculo con ID " + Id + " fue eliminado exitosamente";
    }

}