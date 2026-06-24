package com.example.Espacios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Espacios.DTO.UserDTO;
import com.example.Espacios.model.User;
import com.example.Espacios.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> obtenerTodos() {
        List<UserDTO> users = userService.obtenerTodos();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> buscarUserPorId(@PathVariable Integer id) {
        try {
            UserDTO user = userService.buscarporID(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> agregarUser(@Valid @RequestBody User user) {
        try {
            User nuevoUser = userService.guardarUser(user);
            return new ResponseEntity<>(nuevoUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUser(@PathVariable Integer id) {
        try {
            String resultado = userService.borrarUser(id);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Asignar residencia a usuario
    @PostMapping("/{userId}/{residenciaId}")
    public ResponseEntity<User> asignarResidencia(@PathVariable Integer userId, @PathVariable Integer residenciaId) {
        try {
            User user = userService.asignarResidencia(userId, residenciaId);
                return ResponseEntity.ok(user);        
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Desvincular residencia de usuario
    @DeleteMapping("/desvincular/{userId}/{residenciaId}")
    public ResponseEntity<String> eliminarResidenciaDeUsuario(@PathVariable Integer userId, @PathVariable Integer residenciaId) {
        try {
            String resultado = userService.eliminarResidenciaDeUsuario(userId, residenciaId);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
