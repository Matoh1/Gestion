package com.example.Residencias.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Residencias.DTO.RegionDTO;
import com.example.Residencias.model.Comuna;
import com.example.Residencias.model.Region;
import com.example.Residencias.repository.ComunaRepository;
import com.example.Residencias.repository.RegionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private ResidenciaValidaciones residenciaValidaciones;

    public List<RegionDTO> obtenerTodos() {
        return regionRepository.findAll().stream()
                .map(residenciaValidaciones::convertirRegionADTO)
                .toList();
    }

    public RegionDTO buscarporID(Integer id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Región no encontrada con ID " + id));
        return residenciaValidaciones.convertirRegionADTO(region);
    }

    public Region guardarRegion(Region region) {
        if (!residenciaValidaciones.validarRegion(region)) {
            throw new RuntimeException("Datos de región inválidos");
        }
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
}
