package com.example.Gestion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Gestion.DTO.UserDTO;
import com.example.Gestion.model.Residencia;
import com.example.Gestion.model.Residencias;
import com.example.Gestion.model.User;
import com.example.Gestion.repository.ResidenciaRepository;
import com.example.Gestion.repository.ResidenciasRepository;
import com.example.Gestion.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ResidenciasRepository residenciasRepository;
    private final ResidenciaRepository residenciaRepository;

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
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No se encontro el usuario con la ID" + id));

            userRepository.delete(user);

            return "Usuario con ID " + id + " fue eliminado exitosamente";

        } catch (Exception e) {
            return "Error al eliminar al usuario " + id + ": " + e.getMessage();
        }
    }

    // Residencia a usuario
    public User asignarResidencia(Integer userId, Integer residenciaId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No se encontro el usuario con la ID" + userId));

        Residencia res = residenciaRepository.findById(residenciaId)
                .orElseThrow(() -> new RuntimeException("No se encontro la residencia con la ID" + residenciaId));

        Residencias residencias = new Residencias();
        residencias.setUser(user);
        residencias.setResidencia(res);

        residenciasRepository.save(residencias);

        return user;
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

        if (user.getResidencias() != null) {
            Lresidencias = user.getResidencias().stream()
                    .map(res -> res.getResidencia().getNombre())
                    .toList();
        }

        dto.setResidencias(Lresidencias);

        return dto;
    }
}
