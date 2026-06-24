package com.example.Espacios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;

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
    private WebClient webClient;

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

        ResidenciaExternaDTO residencia = webClient.get()
                .uri("/api/v1/residencias/{id}", residenciaId)
                .retrieve()
                .bodyToMono(ResidenciaExternaDTO.class)
                .block();

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

    private EspaciosDTO convertirADTO(Espacios espacios) {
        EspaciosDTO dto = new EspaciosDTO();

        dto.setId(espacios.getId());

        if (espacios.getEspacio() != null) {
            dto.setEspacio(espacios.getEspacio().getNombre());
        }

        dto.setResidenciaId(espacios.getResidenciaId());
        
        //Sacar el nombre de la residencia
        try {
            ResidenciaExternaDTO res = webClient.get()
                    .uri("/api/v1/residencias/{id}", espacios.getResidenciaId())
                    .retrieve()
                    .bodyToMono(ResidenciaExternaDTO.class)
                    .block();
            if (res != null) dto.setResidencia(res.getNombre());
        } catch (Exception e) {
            dto.setResidencia(null);
        }

        return dto;
    }

}