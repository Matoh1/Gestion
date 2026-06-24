package com.example.Espacios.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.Espacios.DTO.UserDTO;
import com.example.Espacios.DTO.ResidenciaExternaDTO;
import com.example.Espacios.model.User;
import com.example.Espacios.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebClient webClient;

    // todos
    public List<UserDTO> obtenerTodos() {
        return userRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    // por id
    public UserDTO buscarporID(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el usuario con la ID" + id));
        return convertirADTO(user);
    }

    // guardar
    public User guardarUser(User user) {
        return userRepository.save(user);
    }

    // borrar
    public String borrarUser(Integer id) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No se encontro el usuario con la ID" + id));

            userRepository.delete(user);

            return "Usuario con ID " + id + " fue eliminado exitosamente";
    }

    // Residencia a usuario
    public User asignarResidencia(Integer userId, Integer residenciaId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No se encontro el usuario con la ID" + userId));

        ResidenciaExternaDTO residencia = webClient.get()
                .uri("/api/v1/residencias/{id}", residenciaId)
                .retrieve()
                .bodyToMono(ResidenciaExternaDTO.class)
                .block();

        if (residencia == null) {
            throw new RuntimeException("No se encontro la Residencia con la ID" + residenciaId);
        }

        user.setResidenciaId(residencia.getId());
        return userRepository.save(user);
    }

    // Eliminar residencia de usuario
    public String eliminarResidenciaDeUsuario(Integer userId, Integer residenciaId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No se encontro el usuario con la ID" + userId));
                
        if (user.getResidenciaId() != null && user.getResidenciaId().equals(residenciaId)) {
            user.setResidenciaId(null);
            userRepository.save(user);
            return "La residencia ha sido eliminada del usuario exitosamente.";
        }

        return "Error: El usuario no pertenece a esa residencia, no puedes eliminarla.";
    }

    private UserDTO convertirADTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNombre(user.getNombre());
        dto.setApellido(user.getApellido());
        dto.setRut(user.getRut());
        dto.setEmail(user.getEmail());
        dto.setTelefono(user.getTelefono());

        List<String> Lresidencias = new ArrayList<>();

        if (user.getResidenciaId() != null) {
            Lresidencias.add(String.valueOf(user.getResidenciaId()));
        }
        dto.setResidencias(Lresidencias);

        return dto;
    }
}
