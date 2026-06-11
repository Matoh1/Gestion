package com.example.Gestion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Gestion.DTO.EspaciosDTO;
import com.example.Gestion.model.Espacio;
import com.example.Gestion.model.Espacios;
import com.example.Gestion.model.Residencia;
import com.example.Gestion.repository.EspacioRepository;
import com.example.Gestion.repository.EspaciosRepository;
import com.example.Gestion.repository.ResidenciaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EspaciosService {

    private final EspaciosRepository espaciosRepository;
    private final EspacioRepository espacioRepository;
    private final ResidenciaRepository residenciaRepository;

    // todos
    public List<EspaciosDTO> obtenerTodos() {
        return espaciosRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    // por id
    public EspaciosDTO buscarporID(Integer Id) {
        Espacios espacios = espaciosRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("No se encontro La Union con la ID" + Id));
        return convertirADTO(espacios);
    }

    // guardar
    public Espacios guardarEspacios(Integer espacioId, Integer residenciaId) {
        Espacio espacio = espacioRepository.findById(espacioId)
                .orElseThrow(() -> new RuntimeException("No se encontro el Espacio con la ID" + espacioId));

        Residencia residencia = residenciaRepository.findById(residenciaId)
                .orElseThrow(() -> new RuntimeException("No se encontro la Residencia con la ID" + residenciaId));

        Espacios espacios = new Espacios();
        espacios.setEspacio(espacio);
        espacios.setResidencia(residencia);

        return espaciosRepository.save(espacios);
    }

    // borrar vinculo
    public String eliminarVinculo(Integer Id) {
        try {
            Espacios linkER = espaciosRepository.findById(Id)
                    .orElseThrow(() -> new RuntimeException("No se encontro la relacion con la ID" + Id));

            espaciosRepository.delete(linkER);

            return "Relacion con ID " + Id + " fue eliminada exitosamente";
        } catch (Exception e) {
            return "Error al eliminar la relacion " + Id + ": " + e.getMessage();
        }
    }

    private EspaciosDTO convertirADTO(Espacios espacios) {
        EspaciosDTO dto = new EspaciosDTO();

        dto.setId(espacios.getId());

        if (espacios.getEspacio() != null) {
            dto.setEspacio(espacios.getEspacio().getNombre());
        }

        if (espacios.getResidencia() != null) {
            dto.setResidencia(espacios.getResidencia().getNombre());
        }
        return dto;
    }

}