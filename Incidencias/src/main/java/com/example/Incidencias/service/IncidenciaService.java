package com.example.Incidencias.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Incidencias.DTO.IncidenciaDTO;
import com.example.Incidencias.model.Incidencia;
import com.example.Incidencias.repository.IncidenciaRepository;

@Service
public class IncidenciaService {

    @Autowired
    private IncidenciaRepository incidenciaRepository;

    public List<IncidenciaDTO> obtenerTodos() {
        List<IncidenciaDTO> listaDTOs = new ArrayList<>();
        List<Incidencia> incidencias = incidenciaRepository.findAll();
        for (Incidencia i : incidencias) {
            listaDTOs.add(convertirADTO(i));
        }
        return listaDTOs;
    }

    public IncidenciaDTO buscarporID(Integer id) {
        Incidencia incidencia = incidenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro la incidencia con la ID " + id));
        return convertirADTO(incidencia);
    }

    public Incidencia guardarIncidencia(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }

    public String borrarIncidencia(Integer id) {
        try {
            Incidencia incidencia = incidenciaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No se encontro la incidencia con la ID " + id));

            incidenciaRepository.delete(incidencia);
            return "Incidencia con ID " + id + " fue eliminada exitosamente";
        } catch (Exception e) {
            return "Error al eliminar la incidencia " + id + ": " + e.getMessage();
        }
    }

    private IncidenciaDTO convertirADTO(Incidencia incidencia) {
        IncidenciaDTO dto = new IncidenciaDTO();
        dto.setId(incidencia.getId());
        dto.setDescripcion(incidencia.getDescripcion());
        dto.setEstado(incidencia.getEstado());

        if (incidencia.getIncidencias() != null) {
            dto.setIncidenciasId(incidencia.getIncidencias().getId());
            dto.setTituloReporte(incidencia.getIncidencias().getTituloReporte());
        }

        if (incidencia.getTipoIncidencia() != null) {
            dto.setTipoIncidenciaId(incidencia.getTipoIncidencia().getId());
            dto.setNombreTipo(incidencia.getTipoIncidencia().getNombretipo());
        }
        return dto;
    }
}
