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
import lombok.RequiredArgsConstructor;

@Service
@
@Transactional
public class RegionService {

    private  RegionRepository regionRepository;
    private  ComunaRepository comunaRepository;

    public List<RegionDTO> obtenerTodos() {
        return regionRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public RegionDTO buscarporID(Integer id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Región no encontrada con ID " + id));
        return convertirADTO(region);
    }

    public Region guardarRegion(Region region) {
        return regionRepository.save(region);
    }

    public String añadirComunaARegion(Integer regionId, Integer comunaId) {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new RuntimeException("Región no encontrada con ID " + regionId));
        Comuna comuna = comunaRepository.findById(comunaId)
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada con ID " + comunaId));
        comuna.setRegion(region);
        comunaRepository.save(comuna);
        return "Comuna añadida a la región exitosamente";
    }

    public String eliminar(Integer id) {
        if (regionRepository.existsById(id)) {
            regionRepository.deleteById(id);
            return "Region eliminada exitosamente";
        }
        return "No se encontro la Region con la ID " + id;
    }

    private RegionDTO convertirADTO(Region region) {
        RegionDTO dto = new RegionDTO();
        dto.setId(region.getId());
        dto.setNombre(region.getNombreregion());

        List<String> nombresComunas = new ArrayList<>();
        if (region.getComunas() != null) {
            for (Comuna comuna : region.getComunas()) {
                nombresComunas.add(comuna.getNombrecomuna());
            }
        }
        dto.setComunas(nombresComunas);
        return dto;
    }
}
