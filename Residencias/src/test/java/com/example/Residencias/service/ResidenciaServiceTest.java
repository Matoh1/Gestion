package com.example.Residencias.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Residencias.DTO.ResidenciaDTO;
import com.example.Residencias.model.Comuna;
import com.example.Residencias.model.Residencia;
import com.example.Residencias.repository.ResidenciaRepository;

@ExtendWith(MockitoExtension.class)
class ResidenciaServiceTest {

    @Mock
    private ResidenciaRepository residenciaRepository;

    @Mock
    private ResidenciaValidaciones residenciaValidaciones;

    @InjectMocks
    private ResidenciaService residenciaService;

    private Residencia residencia;
    private ResidenciaDTO residenciaDTO;

    @BeforeEach
    void setUp() {
        Comuna comuna = new Comuna();
        comuna.setId(1);
        comuna.setNombrecomuna("Santiago");

        residencia = new Residencia();
        residencia.setId(1);
        residencia.setNombre("Torre A");
        residencia.setDireccion("Av. Siempre Viva #123");
        residencia.setComuna(comuna);

        residenciaDTO = new ResidenciaDTO();
        residenciaDTO.setId(1);
        residenciaDTO.setNombre("Torre A");
        residenciaDTO.setDireccion("Av. Siempre Viva #123");
        residenciaDTO.setComunaId(1);
        residenciaDTO.setNombreComuna("Santiago");
    }

    @Test
    void testObtenerTodos_CuandoExistenResidencias_RetornaListaDTOs() {
        // Given
        when(residenciaRepository.findAll()).thenReturn(List.of(residencia));
        when(residenciaValidaciones.convertirResidenciaADTO(residencia)).thenReturn(residenciaDTO);

        // When
        List<ResidenciaDTO> resultado = residenciaService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Torre A", resultado.get(0).getNombre());
        verify(residenciaRepository, times(1)).findAll();
        verify(residenciaValidaciones, times(1)).convertirResidenciaADTO(residencia);
    }

    @Test
    void testObtenerTodos_CuandoNoExistenResidencias_RetornaListaVacia() {
        // Given
        when(residenciaRepository.findAll()).thenReturn(List.of());

        // When
        List<ResidenciaDTO> resultado = residenciaService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(residenciaRepository, times(1)).findAll();
        verify(residenciaValidaciones, never()).convertirResidenciaADTO(any());
    }

    @Test
    void testBuscarPorID_CuandoExiste_RetornaDTO() {
        // Given
        when(residenciaRepository.findById(1)).thenReturn(Optional.of(residencia));
        when(residenciaValidaciones.convertirResidenciaADTO(residencia)).thenReturn(residenciaDTO);

        // When
        ResidenciaDTO resultado = residenciaService.buscarporID(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Torre A", resultado.getNombre());
        verify(residenciaRepository, times(1)).findById(1);
    }

    @Test
    void testBuscarPorID_CuandoNoExiste_LanzaExcepcion() {
        // Given
        when(residenciaRepository.findById(99)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> residenciaService.buscarporID(99));
        assertTrue(exception.getMessage().contains("Residencia no encontrada"));
        verify(residenciaRepository, times(1)).findById(99);
    }

    @Test
    void testGuardarResidencia_ConDatosValidos_GuardaYRetorna() {
        // Given
        when(residenciaValidaciones.validarResidencia(residencia)).thenReturn(true);
        when(residenciaRepository.save(residencia)).thenReturn(residencia);

        // When
        Residencia resultado = residenciaService.guardarResidencia(residencia);

        // Then
        assertNotNull(resultado);
        assertEquals("Torre A", resultado.getNombre());
        verify(residenciaValidaciones, times(1)).validarResidencia(residencia);
        verify(residenciaRepository, times(1)).save(residencia);
    }

    @Test
    void testGuardarResidencia_ConDatosInvalidos_LanzaExcepcion() {
        // Given
        when(residenciaValidaciones.validarResidencia(residencia)).thenReturn(false);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> residenciaService.guardarResidencia(residencia));
        assertTrue(exception.getMessage().contains("inválidos"));
        verify(residenciaValidaciones, times(1)).validarResidencia(residencia);
        verify(residenciaRepository, never()).save(any());
    }

    @Test
    void testActualizarResidencia_CuandoExiste_ActualizaCampos() {
        // Given
        Residencia actualizacion = new Residencia();
        actualizacion.setNombre("Torre B");
        actualizacion.setDireccion("Calle Nueva #456");

        when(residenciaRepository.findById(1)).thenReturn(Optional.of(residencia));
        when(residenciaRepository.save(any(Residencia.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Residencia resultado = residenciaService.actualizarResidencia(1, actualizacion);

        // Then
        assertNotNull(resultado);
        assertEquals("Torre B", resultado.getNombre());
        assertEquals("Calle Nueva #456", resultado.getDireccion());
        verify(residenciaRepository, times(1)).findById(1);
        verify(residenciaRepository, times(1)).save(any(Residencia.class));
    }

    @Test
    void testActualizarResidencia_CuandoNoExiste_LanzaExcepcion() {
        // Given
        when(residenciaRepository.findById(99)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> residenciaService.actualizarResidencia(99, new Residencia()));
        assertTrue(exception.getMessage().contains("Residencia no encontrada"));
        verify(residenciaRepository, times(1)).findById(99);
        verify(residenciaRepository, never()).save(any());
    }

    @Test
    void testEliminar_CuandoExiste_EliminaYRetornaMensaje() {
        // Given
        when(residenciaRepository.findById(1)).thenReturn(Optional.of(residencia));
        doNothing().when(residenciaRepository).delete(residencia);

        // When
        String resultado = residenciaService.eliminar(1);

        // Then
        assertTrue(resultado.contains("eliminada exitosamente"));
        verify(residenciaRepository, times(1)).findById(1);
        verify(residenciaRepository, times(1)).delete(residencia);
    }

    @Test
    void testEliminar_CuandoNoExiste_RetornaMensajeError() {
        // Given
        when(residenciaRepository.findById(99)).thenReturn(Optional.empty());

        // When
        String resultado = residenciaService.eliminar(99);

        // Then
        assertTrue(resultado.contains("Residencia no encontrada"));
        verify(residenciaRepository, times(1)).findById(99);
        verify(residenciaRepository, never()).delete(any());
    }
}
