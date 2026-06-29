package com.example.Espacios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Espacios.DTO.EspacioDTO;
import com.example.Espacios.DTO.EspaciosDTO;
import com.example.Espacios.DTO.ResidenciaExternaDTO;
import com.example.Espacios.DTO.UserDTO;
import com.example.Espacios.assembler.EspacioAssembler;
import com.example.Espacios.assembler.EspaciosAssembler;
import com.example.Espacios.assembler.UserAssembler;
import com.example.Espacios.model.Espacio;
import com.example.Espacios.model.Espacios;
import com.example.Espacios.model.User;

import reactor.core.publisher.Mono;

@Service
public class EspaciosValidaciones {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private EspacioAssembler espacioAssembler;

    @Autowired
    private EspaciosAssembler espaciosAssembler;

    @Autowired
    private UserAssembler userAssembler;

    public EspacioDTO convertirEspacioADTO(Espacio espacio) {
        return espacioAssembler.toDTO(espacio);
    }

    public UserDTO convertirUsuarioADTO(User user) {
        return userAssembler.toDTO(user);
    }

    public EspaciosDTO convertirEspaciosADTO(Espacios espacios) {
        EspaciosDTO dto = espaciosAssembler.toDTO(espacios);

        try {
            ResidenciaExternaDTO residencia = webClientBuilder.build()
                    .get()
                    .uri("/api/v1/residencias/{id}", espacios.getResidenciaId())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                    .bodyToMono(ResidenciaExternaDTO.class)
                    .block();

            if (residencia != null) {
                dto.setResidencia(residencia.getNombre());
            } else {
                dto.setResidencia("Residencia no encontrada");
            }
        } catch (Exception e) {
            dto.setResidencia("Servicio de residencias no disponible");
        }
        return dto;
    }

    public ResidenciaExternaDTO obtenerResidenciaExterna(Integer residenciaId) {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri("/api/v1/residencias/{id}", residenciaId)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Error al obtener residencia externa")))
                    .bodyToMono(ResidenciaExternaDTO.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean validarEspacio(Espacio espacio) {
        if (espacio == null) {
            return false;
        }
        if (espacio.getNombre() == null || espacio.getNombre().trim().length() < 5) {
            return false;
        }
        if (espacio.getTipo() == null || espacio.getTipo().trim().length() < 3) {
            return false;
        }
        if (espacio.getCapacidad() == null || espacio.getCapacidad() < 1) {
            return false;
        }
        return true;
    }

    public Boolean validarEspacios(Espacios espacios) {
        if (espacios == null) {
            return false;
        }
        if (espacios.getEspacio() == null) {
            return false;
        }
        if (espacios.getResidenciaId() == null) {
            return false;
        }
        return true;
    }

    public Boolean validarUsuario(User user) {
        if (user == null) {
            return false;
        }
        if (user.getNombre() == null || user.getNombre().trim().length() < 3) {
            return false;
        }
        if (user.getApellido() == null || user.getApellido().trim().length() < 2) {
            return false;
        }
        if (user.getRut() == null || user.getRut().trim().length() < 9) {
            return false;
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            return false;
        }
        if (user.getResidenciaId() == null) {
            return false;
        }
        return true;
    }
}
