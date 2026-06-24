package com.example.Residencias.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Residencias.DTO.ResidenciasDTO;
import com.example.Residencias.DTO.UserExternoDTO;
import com.example.Residencias.model.Residencia;
import com.example.Residencias.model.Residencias;
import com.example.Residencias.repository.ResidenciaRepository;
import com.example.Residencias.repository.ResidenciasRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ResidenciasService {

    @Autowired
    private ResidenciasRepository residenciasRepository;

    @Autowired
    private ResidenciaRepository residenciaRepository;

    @Autowired
    private ResidenciaValidaciones residenciaValidaciones;

    public String añadirUsuarioAResidencia(Integer residenciaId, Integer userId) {
        Residencia residencia = residenciaRepository.findById(residenciaId)
                .orElseThrow(() -> new RuntimeException("Error: La Residencia no existe."));

        UserExternoDTO usuarioExterno = residenciaValidaciones.obtenerUsuarioExterno(userId);

        Residencias asignacion = new Residencias();
        asignacion.setResidencia(residencia);
        asignacion.setUserId(userId);
        residenciasRepository.save(asignacion);

        return "El usuario '" + usuarioExterno.getNombre() + "' ahora vive en la residencia: "
                + residencia.getNombre();
    }

    public String eliminarUsuarioDeResidencia(Integer residenciaId, Integer userId) {
        Residencias asignacion = residenciasRepository.findByResidencia_IdAndUserId(residenciaId, userId)
                .orElseThrow(() -> new RuntimeException("Error: El vínculo no existe."));
        residenciasRepository.delete(asignacion);
        return "El usuario ha sido desvinculado de la residencia y ahora no tiene hogar asignado.";
    }

    public List<ResidenciasDTO> obtenerTodasLasAsignaciones() {
        return residenciasRepository.findAll().stream()
                .map(residenciaValidaciones::convertirResidenciasADTO)
                .toList();
    }

    public List<ResidenciasDTO> obtenerAsignacionesPorUsuario(Integer userId) {
        return residenciasRepository.findByUserId(userId).stream()
                .map(residenciaValidaciones::convertirResidenciasADTO)
                .toList();
    }

}
