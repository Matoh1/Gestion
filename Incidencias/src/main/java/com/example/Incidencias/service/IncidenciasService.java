package com.example.Incidencias.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Incidencias.DTO.IncidenciasDTO;
import com.example.Incidencias.DTO.ResidenciaExternoDTO;
import com.example.Incidencias.model.Incidencia;
import com.example.Incidencias.model.Incidencias;
import com.example.Incidencias.repository.IncidenciasRepository;

import reactor.core.publisher.Mono;

@Service
public class IncidenciasService {

    @Autowired
    private IncidenciasRepository incidenciasRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<IncidenciasDTO> obtenerTodos() {
        List<IncidenciasDTO> listaDTOs = new ArrayList<>();
        List<Incidencias> reportes = incidenciasRepository.findAll();
        for (Incidencias r : reportes) {
            listaDTOs.add(convertirADTO(r));
        }
        return listaDTOs;
    }

    public IncidenciasDTO buscarporID(Integer id) {
        Incidencias incidencias = incidenciasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el reporte con la ID " + id));
        return convertirADTO(incidencias);
    }

    public Incidencias guardarIncidencias(Incidencias incidencias) {
        return incidenciasRepository.save(incidencias);
    }

    public String borrarIncidencias(Integer id) {
        try {
            Incidencias incidencias = incidenciasRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No se encontro el reporte con la ID " + id));

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
        dto.setResidenciaId(incidencias.getResidenciaId());

        // Comunicacion REST con el microservicio Residencias para enriquecer el DTO
        // con el nombre real de la residencia (mismo patron que JediService -> Sables).
        try {
            ResidenciaExternoDTO residencia = webClientBuilder.build()
                    .get()
                    .uri("http://Residencias/api/v1/residencia/" + incidencias.getResidenciaId())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty()) // si no existe, no rompe
                    .bodyToMono(ResidenciaExternoDTO.class)
                    .block();

            if (residencia != null) {
                dto.setNombreResidencia(residencia.getNombre());
            }
        } catch (Exception e) {
            dto.setNombreResidencia(null);
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
