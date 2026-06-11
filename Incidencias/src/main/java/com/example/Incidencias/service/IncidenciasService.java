package com.example.Gestion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Gestion.DTO.IncidenciasDTO;
import com.example.Gestion.model.Incidencia;
import com.example.Gestion.model.Incidencias;
import com.example.Gestion.repository.IncidenciasRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class IncidenciasService {

    private final IncidenciasRepository incidenciasRepository;

    public List<IncidenciasDTO> obtenerTodos() {
        return incidenciasRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public IncidenciasDTO buscarporID(Integer id) {
        Incidencias incidencias = incidenciasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el reporte con la ID" + id));
        return convertirADTO(incidencias);
    }

    public Incidencias guardarIncidencias(Incidencias incidencias) {
        return incidenciasRepository.save(incidencias);
    }

    public String borrarIncidencias(Integer id) {
        try {
            Incidencias incidencias = incidenciasRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No se encontro el reporte con la ID" + id));

            incidenciasRepository.delete(incidencias);
            return "Reporte con ID " + id + " fue eliminado exitosamente";
        } catch (Exception e) {
            return "Error al eliminar el reporte " + id + ": " + e.getMessage();
        }
    }

    private IncidenciasDTO convertirADTO(Incidencias incidencias) {
        IncidenciasDTO dto = new IncidenciasDTO();
        dto.setId(incidencias.getId());
        dto.setTituloReporte(incidencias.getTituloReporte());
        dto.setFechaReporte(incidencias.getFechaReporte());
        dto.setPrioridad(incidencias.getPrioridad());

        if (incidencias.getResidencia() != null) {
            dto.setResidenciaId(incidencias.getResidencia().getId());
            dto.setNombreResidencia(incidencias.getResidencia().getNombre());
        }

        List<String> listaIncidencias = new ArrayList<>();
        if (incidencias.getIncidencias() != null) {
            for (Incidencia incidencia : incidencias.getIncidencias()) {
                listaIncidencias.add(incidencia.getDescripcion());
            }
        }
        dto.setIncidencias(listaIncidencias);
        return dto;
    }
}
