package com.example.Residencias.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Residencias.DTO.ResidenciasDTO;
import com.example.Residencias.DTO.UserExternoDTO;
import com.example.Residencias.model.Residencia;
import com.example.Residencias.model.Residencias;
import com.example.Residencias.repository.ResidenciaRepository;
import com.example.Residencias.repository.ResidenciasRepository;

@ExtendWith(MockitoExtension.class)
class ResidenciasServiceTest {

    @Mock
    private ResidenciasRepository residenciasRepository;

    @Mock
    private ResidenciaRepository residenciaRepository;

    @Mock
    private ResidenciaValidaciones residenciaValidaciones;

    @InjectMocks
    private ResidenciasService residenciasService;

    private Residencia residencia;
    private Residencias asignacion;
    private ResidenciasDTO asignacionDTO;
    private UserExternoDTO usuarioExterno;

    @BeforeEach
    void setUp() {
        residencia = new Residencia();
        residencia.setId(1);
        residencia.setNombre("Torre A");
        residencia.setDireccion("Av. Siempre Viva #123");

        usuarioExterno = new UserExternoDTO();
        usuarioExterno.setId(1);
        usuarioExterno.setNombre("Juan");
        usuarioExterno.setApellido("Perez");
        usuarioExterno.setRut("12345678-9");

        asignacion = new Residencias();
        asignacion.setId(1);
        asignacion.setResidencia(residencia);
        asignacion.setUserId(1);

        asignacionDTO = new ResidenciasDTO();
        asignacionDTO.setUserId(1);
        asignacionDTO.setResidenciaId(1);
        asignacionDTO.setNombreResidencia("Torre A");
        asignacionDTO.setNombreUsuario("Juan Perez");
    }

    @Test
    void testAñadirUsuarioAResidencia_CuandoExisten_AsignaYRetornaMensaje() {
        // Given
        when(residenciaRepository.findById(1)).thenReturn(Optional.of(residencia));
        when(residenciaValidaciones.obtenerUsuarioExterno(1)).thenReturn(usuarioExterno);
        when(residenciasRepository.save(any(Residencias.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        String resultado = residenciasService.añadirUsuarioAResidencia(1, 1);

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.contains("Juan"));
        assertTrue(resultado.contains("Torre A"));
        verify(residenciaRepository, times(1)).findById(1);
        verify(residenciaValidaciones, times(1)).obtenerUsuarioExterno(1);
        verify(residenciasRepository, times(1)).save(any(Residencias.class));
    }

    @Test
    void testAñadirUsuarioAResidencia_CuandoResidenciaNoExiste_LanzaExcepcion() {
        // Given
        when(residenciaRepository.findById(99)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> residenciasService.añadirUsuarioAResidencia(99, 1));
        assertTrue(exception.getMessage().contains("Residencia no existe"));
        verify(residenciaRepository, times(1)).findById(99);
        verify(residenciaValidaciones, never()).obtenerUsuarioExterno(any());
        verify(residenciasRepository, never()).save(any());
    }

    @Test
    void testEliminarUsuarioDeResidencia_CuandoExisteAsignacion_EliminaYRetornaMensaje() {
        // Given
        when(residenciasRepository.findByResidencia_IdAndUserId(1, 1))
                .thenReturn(Optional.of(asignacion));

        // When
        String resultado = residenciasService.eliminarUsuarioDeResidencia(1, 1);

        // Then
        assertTrue(resultado.contains("desvinculado"));
        verify(residenciasRepository, times(1)).findByResidencia_IdAndUserId(1, 1);
        verify(residenciasRepository, times(1)).delete(asignacion);
    }

    @Test
    void testEliminarUsuarioDeResidencia_CuandoNoExisteAsignacion_LanzaExcepcion() {
        // Given
        when(residenciasRepository.findByResidencia_IdAndUserId(99, 99))
                .thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> residenciasService.eliminarUsuarioDeResidencia(99, 99));
        assertTrue(exception.getMessage().contains("vínculo no existe"));
        verify(residenciasRepository, times(1)).findByResidencia_IdAndUserId(99, 99);
        verify(residenciasRepository, never()).delete(any());
    }

    @Test
    void testObtenerTodasLasAsignaciones_RetornaListaDTOs() {
        // Given
        when(residenciasRepository.findAll()).thenReturn(List.of(asignacion));
        when(residenciaValidaciones.convertirResidenciasADTO(asignacion)).thenReturn(asignacionDTO);

        // When
        List<ResidenciasDTO> resultado = residenciasService.obtenerTodasLasAsignaciones();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getUserId());
        assertEquals("Torre A", resultado.get(0).getNombreResidencia());
        verify(residenciasRepository, times(1)).findAll();
    }

    @Test
    void testObtenerTodasLasAsignaciones_CuandoNoHay_RetornaListaVacia() {
        // Given
        when(residenciasRepository.findAll()).thenReturn(List.of());

        // When
        List<ResidenciasDTO> resultado = residenciasService.obtenerTodasLasAsignaciones();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(residenciasRepository, times(1)).findAll();
        verify(residenciaValidaciones, never()).convertirResidenciasADTO(any());
    }

    @Test
    void testObtenerAsignacionesPorUsuario_RetornaListaDTOs() {
        // Given
        when(residenciasRepository.findByUserId(1)).thenReturn(List.of(asignacion));
        when(residenciaValidaciones.convertirResidenciasADTO(asignacion)).thenReturn(asignacionDTO);

        // When
        List<ResidenciasDTO> resultado = residenciasService.obtenerAsignacionesPorUsuario(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getUserId());
        verify(residenciasRepository, times(1)).findByUserId(1);
    }

    @Test
    void testObtenerAsignacionesPorUsuario_CuandoNoTiene_RetornaListaVacia() {
        // Given
        when(residenciasRepository.findByUserId(99)).thenReturn(List.of());

        // When
        List<ResidenciasDTO> resultado = residenciasService.obtenerAsignacionesPorUsuario(99);

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(residenciasRepository, times(1)).findByUserId(99);
        verify(residenciaValidaciones, never()).convertirResidenciasADTO(any());
    }
}
