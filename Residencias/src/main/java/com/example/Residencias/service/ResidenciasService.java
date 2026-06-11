package com.example.Residencias.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Residencias.DTO.ComunaDTO;
import com.example.Residencias.DTO.ResidenciasDTO;
import com.example.Residencias.model.Comuna;
import com.example.Residencias.model.Residencia;
import com.example.Residencias.model.Residencias;
import com.example.Residencias.repository.ComunaRepository;
import com.example.Residencias.repository.ResidenciaRepository;
import com.example.Residencias.repository.ResidenciasRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
public class ResidenciasService {

    private  ResidenciasRepository residenciasRepository;
    private  UserRepository userRepository;
    private  ResidenciaRepository residenciaRepository;

    public String añadirUsuarioAResidencia(Integer residenciaId, Integer userId) {
        Residencia residencia = residenciaRepository.findById(residenciaId)
                .orElseThrow(() -> new RuntimeException("Error: La Residencia no existe."));
        User usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: El usuario no existe."));

        Residencias asignacion = new Residencias();
        asignacion.setResidencia(residencia);
        asignacion.setUser(usuario);
        residenciasRepository.save(asignacion);

        return "El usuario '" + usuario.getNombre() + "' ahora vive en la residencia: "
                + residencia.getNombre();
    }

    public String eliminarUsuarioDeResidencia(Integer residenciaId, Integer userId) {
        Residencias asignacion = residenciasRepository.findByResidencia_IdAndUser_Id(residenciaId, userId)
                .orElseThrow(() -> new RuntimeException("Error: El vínculo no existe."));
        residenciasRepository.delete(asignacion);
        return "El usuario ha sido desvinculado de la residencia y ahora no tiene hogar asignado.";

    }

    public List<ResidenciasDTO> obtenerTodasLasAsignaciones() {
        return residenciasRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public List<ResidenciasDTO> obtenerAsignacionesPorUsuario(Integer userId) {
        return residenciasRepository.findByUser_Id(userId).stream()
                .map(this::convertirADTO)
                .toList();
    }

    private ResidenciasDTO convertirADTO(Residencias residencias) {
        ResidenciasDTO dto = new ResidenciasDTO();

        // Datos del Usuario
        if (residencias.getUser() != null) {
            dto.setUserId(residencias.getUser().getId());
            dto.setNombreUsuario(residencias.getUser().getNombre() + " " + residencias.getUser().getApellido());
        }

        // Datos de la Residencia
        if (residencias.getResidencia() != null) {
            dto.setResidenciaId(residencias.getResidencia().getId());
            dto.setNombreResidencia(residencias.getResidencia().getNombre());
        }

        return dto;
    }

}
