package com.example.Residencias.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Residencias.DTO.ComunaDTO;
import com.example.Residencias.DTO.RegionDTO;
import com.example.Residencias.DTO.ResidenciaDTO;
import com.example.Residencias.DTO.ResidenciasDTO;
import com.example.Residencias.DTO.UserExternoDTO;
import com.example.Residencias.assembler.ComunaAssembler;
import com.example.Residencias.assembler.RegionAssembler;
import com.example.Residencias.assembler.ResidenciaAssembler;
import com.example.Residencias.assembler.ResidenciasAssembler;
import com.example.Residencias.model.Comuna;
import com.example.Residencias.model.Region;
import com.example.Residencias.model.Residencia;
import com.example.Residencias.model.Residencias;

import reactor.core.publisher.Mono;

@Service
public class ResidenciaValidaciones {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ComunaAssembler comunaAssembler;

    @Autowired
    private RegionAssembler regionAssembler;

    @Autowired
    private ResidenciaAssembler residenciaAssembler;

    @Autowired
    private ResidenciasAssembler residenciasAssembler;

    public ComunaDTO convertirComunaADTO(Comuna comuna) {
        return comunaAssembler.toDTO(comuna);
    }

    public RegionDTO convertirRegionADTO(Region region) {
        return regionAssembler.toDTO(region);
    }

    public ResidenciaDTO convertirResidenciaADTO(Residencia residencia) {
        return residenciaAssembler.toDTO(residencia);
    }

    public ResidenciasDTO convertirResidenciasADTO(Residencias residencias) {
        ResidenciasDTO dto = residenciasAssembler.toDTO(residencias);

        try {
            UserExternoDTO usuarioExterno = webClientBuilder.build()
                    .get()
                    .uri("http://Espacios/api/v1/user/" + residencias.getUserId())
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

    public UserExternoDTO obtenerUsuarioExterno(Integer userId) {
        try {
            return webClientBuilder.build()
                    .get()
                    .uri("http://Espacios/api/v1/user/" + userId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                    .bodyToMono(UserExternoDTO.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean validarComuna(Comuna comuna) {
        if (comuna.getNombrecomuna() == null || comuna.getNombrecomuna().trim().length() < 5) {
            return false;
        }
        if (comuna.getRegion() == null) {
            return false;
        }
        return true;
    }

    public Boolean validarRegion(Region region) {
        if (region.getNombreregion() == null || region.getNombreregion().trim().length() < 5) {
            return false;
        }
        return true;
    }

    public Boolean validarResidencia(Residencia residencia) {
        if (residencia.getNombre() == null || residencia.getNombre().trim().length() < 3) {
            return false;
        }
        if (residencia.getDireccion() == null || residencia.getDireccion().trim().length() < 3) {
            return false;
        }
        return true;
    }

    public Boolean validarAsignacion(Residencias residencias) {
        if (residencias.getUserId() == null) {
            return false;
        }
        return true;
    }
}
