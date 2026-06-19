package com.example.Residencias.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Residencias.DTO.ComunaDTO;
import com.example.Residencias.DTO.ResidenciasDTO;
import com.example.Residencias.DTO.UserExternoDTO;
import com.example.Residencias.model.Comuna;
import com.example.Residencias.model.Residencia;
import com.example.Residencias.model.Residencias;
import com.example.Residencias.repository.ComunaRepository;
import com.example.Residencias.repository.ResidenciaRepository;
import com.example.Residencias.repository.ResidenciasRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ResidenciasService {

    @Autowired

    private  ResidenciasRepository residenciasRepository;
    @Autowired
    private  ResidenciaRepository residenciaRepository;
    
    @Autowired
    private WebClient.Builder webClientBuilder;


public String añadirUsuarioAResidencia(Integer residenciaId, Integer userId) {
        Residencia residencia = residenciaRepository.findById(residenciaId)
                .orElseThrow(() -> new RuntimeException("Error: La Residencia no existe."));
        
        UserExternoDTO usuarioExterno = obtenerUsuarioExterno(userId);
        
        Residencias asignacion = new Residencias();
        asignacion.setResidencia(residencia);
        asignacion.setUserId(userId); 
        residenciasRepository.save(asignacion);

        return "El usuario '" + usuarioExterno.getNombre() + "' ahora vive en la residencia: "
                + residencia.getNombre();
    }

    private UserExternoDTO obtenerUsuarioExterno(Integer userId) {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8081/api/v1/user/" + userId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                    .bodyToMono(UserExternoDTO.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

    public String eliminarUsuarioDeResidencia(Integer residenciaId, Integer userId) {
        Residencias asignacion = residenciasRepository.findByResidencia_IdAndUserId(residenciaId, userId)
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
        return residenciasRepository.findByUserId(userId).stream()
                .map(this::convertirADTO)
                .toList();
    }

private ResidenciasDTO convertirADTO(Residencias residencias) {
        ResidenciasDTO dto = new ResidenciasDTO();

        if (residencias.getResidencia() != null) {
            dto.setResidenciaId(residencias.getResidencia().getId());
            dto.setNombreResidencia(residencias.getResidencia().getNombre());
        }
        
        dto.setUserId(residencias.getUserId());

        try {
            UserExternoDTO usuarioExterno = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8081/api/v1/user/" + residencias.getUserId()) 
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                    .bodyToMono(UserExternoDTO.class)
                    .block();

            if (usuarioExterno != null) {
                dto.setNombreUsuario(usuarioExterno.getNombre() + " " + usuarioExterno.getApellido());
            } else {
                dto.setNombreUsuario("Usuario no encontrado");
            }
        } catch (Exception e) {

            dto.setNombreUsuario("Servicio de usuarios no disponible");
        }

        return dto;
    }

}
