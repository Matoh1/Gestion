package com.example.Gestion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Gestion.DTO.Tipo_IncidenciasDTO;
import com.example.Gestion.model.Incidencia;
import com.example.Gestion.model.Tipo_Incidencia;
import com.example.Gestion.repository.Tipo_IncidenciasRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class Tipo_IncidenciasService {

    private final Tipo_IncidenciasRepository tipoIncidenciasRepository;

    public List<Tipo_IncidenciasDTO> obtenerTodos() {
        return tipoIncidenciasRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public Tipo_IncidenciasDTO buscarporID(Integer id) {
        Tipo_Incidencia tipoIncidencia = tipoIncidenciasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el tipo de incidencia con la ID" + id));
        return convertirADTO(tipoIncidencia);
    }

    public Tipo_Incidencia guardarTipo_Incidencia(Tipo_Incidencia tipoIncidencia) {
        return tipoIncidenciasRepository.save(tipoIncidencia);
    }

    public String borrarTipo_Incidencia(Integer id) {
        try {
            Tipo_Incidencia tipoIncidencia = tipoIncidenciasRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No se encontro el tipo de incidencia con la ID" + id));

            tipoIncidenciasRepository.delete(tipoIncidencia);
            return "Tipo de incidencia con ID " + id + " fue eliminado exitosamente";
        } catch (Exception e) {
            return "Error al eliminar el tipo de incidencia " + id + ": " + e.getMessage();
        }
    }

    private Tipo_IncidenciasDTO convertirADTO(Tipo_Incidencia tipoIncidencia) {
        Tipo_IncidenciasDTO dto = new Tipo_IncidenciasDTO();
        dto.setId(tipoIncidencia.getId());
        dto.setNombre(tipoIncidencia.getNombretipo());

        List<String> incidencias = new ArrayList<>();
        if (tipoIncidencia.getIncidencias() != null) {
            for (Incidencia incidencia : tipoIncidencia.getIncidencias()) {
                incidencias.add(incidencia.getDescripcion());
            }
        }
        dto.setIncidencias(incidencias);
        return dto;
    }
}
