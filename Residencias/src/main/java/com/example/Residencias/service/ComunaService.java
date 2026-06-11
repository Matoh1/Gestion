package com.example.Residencias.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Residencias.DTO.ComunaDTO;
import com.example.Residencias.model.Comuna;
import com.example.Residencias.model.Residencia;
import com.example.Residencias.repository.ComunaRepository;
import com.example.Residencias.repository.ResidenciaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComunaService {

    private  ComunaRepository comunaRepository;
    private  ResidenciaRepository residenciaRepository;

    public List<ComunaDTO> obtenerTodos() {
        return comunaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public ComunaDTO buscarporID(Integer id) {
        Comuna comuna = comunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada con ID " + id));
        return convertirADTO(comuna);
    }

    public Comuna guardarComuna(Comuna comuna) {
        return comunaRepository.save(comuna);
    }

    public String añadirResidenciaAComuna(Integer comunaId, Integer residenciaId) {
        Comuna comuna = comunaRepository.findById(comunaId)
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada con ID " + comunaId));
        Residencia residencia = residenciaRepository.findById(residenciaId)
                .orElseThrow(() -> new RuntimeException("Residencia no encontrada con ID " + residenciaId));
        residencia.setComuna(comuna);
        residenciaRepository.save(residencia);

        return "Residencia añadida a la comuna exitosamente";
    }

    public String eliminarResidenciaDeComuna(Integer comunaId, Integer residenciaId) {
        Residencia residencia = residenciaRepository.findById(residenciaId)
                .orElseThrow(() -> new RuntimeException("La residencia no existe."));
        if (residencia.getComuna() != null && residencia.getComuna().getId().equals(comunaId)) {
            residencia.setComuna(null);
            residenciaRepository.save(residencia);
            return "La residencia ha sido eliminada de la comuna exitosamente.";
        }
        return "Error: La residencia no pertenece a esa comuna, no puedes eliminarla.";
    }

    public String eliminar(Integer id) {
        if (comunaRepository.existsById(id)) {
            comunaRepository.deleteById(id);
            return "Comuna eliminada exitosamente";
        }
        return "No se encontro la Comuna con la ID " + id;
    }

    private ComunaDTO convertirADTO(Comuna comuna) {
        ComunaDTO dto = new ComunaDTO();
        dto.setId(comuna.getId());
        dto.setNombre(comuna.getNombrecomuna());

        if (comuna.getRegion() != null) {
            dto.setRegionId(comuna.getRegion().getId());
            dto.setRegion(comuna.getRegion().getNombreregion());
        }

        // Extrae nombres de la lista de residencias
        List<String> nombresResidencias = new ArrayList<>();
        if (comuna.getResidencias() != null) {
            for (Residencia nexo : comuna.getResidencias()) {
                nombresResidencias.add(nexo.getNombre());
            }
        }
        dto.setResidencia(nombresResidencias);
        return dto;
    }
}