package com.example.Espacios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private EspaciosValidaciones espaciosValidaciones;

    // todos
    public List<UserDTO> obtenerTodos() {
        return userRepository.findAll().stream()
                .map(espaciosValidaciones::convertirUsuarioADTO)
                .toList();
    }

    // por id
    public UserDTO buscarporID(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el usuario con la ID" + id));
        return espaciosValidaciones.convertirUsuarioADTO(user);
    }

    // guardar
    public User guardarUser(User user) {
        if (!espaciosValidaciones.validarUsuario(user)) {
            throw new RuntimeException("Datos de usuario inválidos");
        }
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

        ResidenciaExternaDTO residencia = espaciosValidaciones.obtenerResidenciaExterna(residenciaId);

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
}
