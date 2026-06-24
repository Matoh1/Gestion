package com.example.Residencias.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Residencias.DTO.ResidenciaDTO;
import com.example.Residencias.model.Residencia;
import com.example.Residencias.repository.ResidenciaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ResidenciaService {

    @Autowired
    private ResidenciaRepository residenciaRepository;

    @Autowired
    private ResidenciaValidaciones residenciaValidaciones;

    public List<ResidenciaDTO> obtenerTodos() {
        return residenciaRepository.findAll().stream()
                .map(residenciaValidaciones::convertirResidenciaADTO)
                .toList();
    }

    public ResidenciaDTO buscarporID(Integer id) {
        Residencia residencia = residenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Residencia no encontrada con ID " + id));
        return residenciaValidaciones.convertirResidenciaADTO(residencia);
    }

    public Residencia guardarResidencia(Residencia residencia) {
        if (!residenciaValidaciones.validarResidencia(residencia)) {
            throw new RuntimeException("Datos de residencia inválidos");
        }
        return residenciaRepository.save(residencia);
    }

    public String eliminar(Integer id) {
        try {
            Residencia residencia = residenciaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Residencia no encontrada con ID " + id));
            residenciaRepository.delete(residencia);
            return "Residencia'" + residencia.getNombre() + "' eliminada exitosamente.";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Residencia actualizarResidencia(Integer id, Residencia Aresidencia) {
        Residencia resi = residenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Residencia no encontrada con ID " + id));
        if (Aresidencia.getNombre() != null) {
            resi.setNombre(Aresidencia.getNombre());
        }
        if (Aresidencia.getDireccion() != null) {
            resi.setDireccion(Aresidencia.getDireccion());
        }
        return residenciaRepository.save(resi);
    }

}
