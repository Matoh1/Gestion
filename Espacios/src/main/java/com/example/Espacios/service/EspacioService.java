package com.example.Espacios.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Espacios.DTO.EspacioDTO;
import com.example.Espacios.DTO.ResidenciaExternaDTO;
import com.example.Espacios.model.Espacio;
import com.example.Espacios.model.Espacios;
import com.example.Espacios.repository.EspacioRepository;
import com.example.Espacios.repository.EspaciosRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EspacioService {

    @Autowired
    private EspacioRepository espacioRepository;

    @Autowired
    private EspaciosRepository espaciosRepository;

    @Autowired
    private EspaciosValidaciones espaciosValidaciones;

    public List<EspacioDTO> obtenerTodos() {
        return espacioRepository.findAll().stream()
                .map(espaciosValidaciones::convertirEspacioADTO)
                .toList();
    }

    // por id
    public EspacioDTO buscarporID(Integer id) {
        Espacio espacio = espacioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el Espacio con la ID" + id));
        return espaciosValidaciones.convertirEspacioADTO(espacio);
    }

    // guardar
    public Espacio guardarEspacio(Espacio espacio) {
        if (!espaciosValidaciones.validarEspacio(espacio)) {
            throw new RuntimeException("Datos de espacio inválidos");
        }
        return espacioRepository.save(espacio);
    }

    // borrar
    public String borrarEspacio(Integer id) {
        Espacio espacio = espacioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el Espacio con la ID" + id));

        espacioRepository.delete(espacio);
        return "Espacio con ID " + id + " fue eliminado exitosamente";
    }

    // Asignar espacio a residencia
    public Espacio asignarEspacioAResidencia(Integer espacioId, Integer residenciaId) {
        Espacio espacio = espacioRepository.findById(espacioId)
                .orElseThrow(() -> new RuntimeException("no se encontro el Espacio con la ID" + espacioId));

        ResidenciaExternaDTO res = espaciosValidaciones.obtenerResidenciaExterna(residenciaId);

        if (res == null) {
            throw new RuntimeException("No se encontro la residencia con la ID" + residenciaId);
        }

        Espacios espacios = new Espacios();
        espacios.setEspacio(espacio);
        espacios.setResidenciaId(res.getId());

        espaciosRepository.save(espacios);

        return espacio;
    }
    
    //Eliminar vinculo 
    public String eliminarVinculo(Integer id, Integer residenciaId) {
        Espacio espacio = espacioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el Espacio con la ID" + id));
        Espacios vinculo = espaciosRepository.findByEsResId(espacio.getId(), residenciaId);
        if (vinculo != null) {
            espaciosRepository.delete(vinculo);
            return "Vinculo entre espacio con ID " + id + " y residencia con ID " + residenciaId + " fue eliminado exitosamente";
        }
        throw new RuntimeException("No se encontro el vinculo entre el espacio con ID " + id + " y la residencia con ID " + residenciaId);
    }

    private EspacioDTO convertirADTO(Espacio espacio) {
        EspacioDTO dto = new EspacioDTO();
        dto.setId(espacio.getId());

        dto.setNombre(espacio.getNombre());
        dto.setTipo(espacio.getTipo());
        dto.setCapacidad(espacio.getCapacidad());

        if (espacio.getEspacios() != null) {
            dto.setEspacios(espacio.getEspacios().stream()
                    .map(rel -> String.valueOf(rel.getResidenciaId()))
                    .toList());
        } else {
            dto.setEspacios(new ArrayList<>());
        }

        return dto;
    }

}