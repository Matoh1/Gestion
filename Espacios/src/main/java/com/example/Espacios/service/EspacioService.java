package com.example.Gestion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Gestion.DTO.EspacioDTO;
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
public class EspacioService {

    private final EspacioRepository espacioRepository;
    private final EspaciosRepository espaciosRepository;
    private final ResidenciaRepository residenciaRepository;

    public List<EspacioDTO> obtenerTodos() {
        return espacioRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    // por id
    public EspacioDTO buscarporID(Integer id) {
        Espacio espacio = espacioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el Espacio con la ID" + id));
        return convertirADTO(espacio);
    }

    // guardar
    public Espacio guardarEspacio(Espacio espacio) {
        return espacioRepository.save(espacio);
    }

    // borrar
    public String borrarEspacio(Integer id) {
        try {
            Espacio espacio = espacioRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No se encontro el Espacio con la ID" + id));

            espacioRepository.delete(espacio);
            return "Espacio con ID " + id + " fue eliminado exitosamente";
        } catch (Exception e) {
            return "Error al eliminar el Espacio " + id + ": " + e.getMessage();
        }
    }

    // Asignar espacio a residencia
    public Espacio asignarEspacioAResidencia(Integer espacioId, Integer residenciaId) {
        Espacio espacio = espacioRepository.findById(espacioId)
                .orElseThrow(() -> new RuntimeException("no se encontro el Espacio con la ID" + espacioId));

        Residencia res = residenciaRepository.findById(residenciaId)
                .orElseThrow(() -> new RuntimeException("No se encontro la residencia con la ID" + residenciaId));

        Espacios espacios = new Espacios();
        espacios.setEspacio(espacio);
        espacios.setResidencia(res);

        espaciosRepository.save(espacios);

        return espacio;

    }

    private EspacioDTO convertirADTO(Espacio espacio) {
        EspacioDTO dto = new EspacioDTO();
        dto.setId(espacio.getId());

        dto.setNombre(espacio.getNombre());
        dto.setTipo(espacio.getTipo());
        dto.setCapacidad(espacio.getCapacidad());

        if (espacio.getEspacios() != null) {
            dto.setEspacios(espacio.getEspacios().stream()
                    .map(rel -> rel.getResidencia().getNombre())
                    .toList());
        } else {
            dto.setEspacios(new ArrayList<>());
        }

        return dto;
    }

}