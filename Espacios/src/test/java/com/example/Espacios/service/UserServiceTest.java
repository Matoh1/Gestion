package com.example.Espacios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Espacios.DTO.UserDTO;
import com.example.Espacios.DTO.ResidenciaExternaDTO;
import com.example.Espacios.model.User;
import com.example.Espacios.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EspaciosValidaciones espaciosValidaciones;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;
    private ResidenciaExternaDTO residenciaExternaDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setNombre("Juan");
        user.setApellido("Perez");
        user.setRut("12345678-9");
        user.setEmail("juan.perez@example.com");
        user.setTelefono("123456789");

        userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setNombre("Juan");
        userDTO.setApellido("Perez");
        userDTO.setRut("12345678-9");
        userDTO.setEmail("juan.perez@example.com");
        userDTO.setTelefono("123456789");

        residenciaExternaDTO = new ResidenciaExternaDTO();
        residenciaExternaDTO.setId(1);
        residenciaExternaDTO.setNombre("Residencia Norte");
    }

    @Test
    void testObtenerTodos_CuandoExistenUsuarios_RetornaListaDTOs() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(espaciosValidaciones.convertirUsuarioADTO(user)).thenReturn(userDTO);

        List<UserDTO> resultado = userService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        verify(userRepository, times(1)).findAll();
        verify(espaciosValidaciones, times(1)).convertirUsuarioADTO(user);
    }

    @Test
    void testBuscarPorID_CuandoExiste_RetornaDTO() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(espaciosValidaciones.convertirUsuarioADTO(user)).thenReturn(userDTO);

        UserDTO resultado = userService.buscarporID(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Perez", resultado.getApellido());
        verify(userRepository, times(1)).findById(1);
        verify(espaciosValidaciones, times(1)).convertirUsuarioADTO(user);
    }

    @Test
    void testBuscarPorID_CuandoNoExiste_LanzaExcepcion() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.buscarporID(99));

        assertTrue(exception.getMessage().contains("No se encontro el usuario"));
        verify(userRepository, times(1)).findById(99);
        verify(espaciosValidaciones, never()).convertirUsuarioADTO(any());
    }

    @Test
    void testGuardarUser_CuandoUsuarioValido_GuardaYRetorna() {
        when(espaciosValidaciones.validarUsuario(user)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);

        User resultado = userService.guardarUser(user);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(espaciosValidaciones, times(1)).validarUsuario(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGuardarUser_CuandoUsuarioInvalido_LanzaExcepcion() {
        when(espaciosValidaciones.validarUsuario(user)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.guardarUser(user));

        assertTrue(exception.getMessage().contains("Datos de usuario inválidos"));
        verify(espaciosValidaciones, times(1)).validarUsuario(user);
        verify(userRepository, never()).save(any());
    }

    @Test
    void testAsignarResidencia_CuandoResidenciaExiste_GuardaYRetornaUsuario() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(espaciosValidaciones.obtenerResidenciaExterna(1)).thenReturn(residenciaExternaDTO);
        when(userRepository.save(user)).thenReturn(user);

        User resultado = userService.asignarResidencia(1, 1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getResidenciaId());
        verify(userRepository, times(1)).findById(1);
        verify(espaciosValidaciones, times(1)).obtenerResidenciaExterna(1);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testAsignarResidencia_CuandoResidenciaNoExiste_LanzaExcepcion() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(espaciosValidaciones.obtenerResidenciaExterna(2)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.asignarResidencia(1, 2));

        assertTrue(exception.getMessage().contains("No se encontro la Residencia"));
        verify(espaciosValidaciones, times(1)).obtenerResidenciaExterna(2);
        verify(userRepository, never()).save(any());
    }

    @Test
    void testEliminarResidenciaDeUsuario_CuandoPertenece_EliminaResidencia() {
        user.setResidenciaId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        String resultado = userService.eliminarResidenciaDeUsuario(1, 1);

        assertEquals("La residencia ha sido eliminada del usuario exitosamente.", resultado);
        assertNull(user.getResidenciaId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testEliminarResidenciaDeUsuario_CuandoNoPertenece_RetornaError() {
        user.setResidenciaId(2);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        String resultado = userService.eliminarResidenciaDeUsuario(1, 1);

        assertEquals("Error: El usuario no pertenece a esa residencia, no puedes eliminarla.", resultado);
        verify(userRepository, never()).save(any());
    }
}
