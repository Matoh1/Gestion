package com.example.Residencias.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import com.example.Residencias.DTO.ComunaDTO;
import com.example.Residencias.model.Comuna;
import com.example.Residencias.model.Region;
import com.example.Residencias.repository.ComunaRepository;
import com.example.Residencias.repository.ResidenciaRepository;

@ExtendWith(MockitoExtension.class)
class ComunaServiceTest {

    @Mock
    private ComunaRepository comunaRepository;

    @Mock
    private ResidenciaRepository residenciaRepository;

    @Mock
    private ResidenciaValidaciones residenciaValidaciones;

    @InjectMocks
    private ComunaService comunaService;

    private Comuna comuna;
    private ComunaDTO comunaDTO;

    @BeforeEach
    void setUp() {
        Region region = new Region();
        region.setId(1);
        region.setNombreregion("Region Metropolitana");

        comuna = new Comuna();
        comuna.setId(1);
        comuna.setNombrecomuna("Santiago Centro");
        comuna.setRegion(region);

        comunaDTO = new ComunaDTO();
        comunaDTO.setId(1);
        comunaDTO.setNombre("Santiago Centro");
        comunaDTO.setRegionId(1);
        comunaDTO.setRegion("Region Metropolitana");
    }

    @Test
    void testObtenerTodos_CuandoExistenComunas_RetornaListaDTOs() {
        // Given
        when(comunaRepository.findAll()).thenReturn(List.of(comuna));
        when(residenciaValidaciones.convertirComunaADTO(comuna)).thenReturn(comunaDTO);

        // When
        List<ComunaDTO> resultado = comunaService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Santiago Centro", resultado.get(0).getNombre());
        verify(comunaRepository, times(1)).findAll();
        verify(residenciaValidaciones, times(1)).convertirComunaADTO(comuna);
    }

    @Test
    void testObtenerTodos_CuandoNoExistenComunas_RetornaListaVacia() {
        // Given
        when(comunaRepository.findAll()).thenReturn(List.of());

        // When
        List<ComunaDTO> resultado = comunaService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(comunaRepository, times(1)).findAll();
        verify(residenciaValidaciones, never()).convertirComunaADTO(any());
    }

    @Test
    void testBuscarPorID_CuandoExiste_RetornaDTO() {
        // Given
        when(comunaRepository.findById(1)).thenReturn(Optional.of(comuna));
        when(residenciaValidaciones.convertirComunaADTO(comuna)).thenReturn(comunaDTO);

        // When
        ComunaDTO resultado = comunaService.buscarporID(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Santiago Centro", resultado.getNombre());
        verify(comunaRepository, times(1)).findById(1);
    }

    @Test
    void testBuscarPorID_CuandoNoExiste_LanzaExcepcion() {
        // Given
        when(comunaRepository.findById(99)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> comunaService.buscarporID(99));
        assertTrue(exception.getMessage().contains("Comuna no encontrada"));
        verify(comunaRepository, times(1)).findById(99);
    }

    @Test
    void testGuardarComuna_ConDatosValidos_GuardaYRetorna() {
        // Given
        when(residenciaValidaciones.validarComuna(comuna)).thenReturn(true);
        when(comunaRepository.save(comuna)).thenReturn(comuna);

        // When
        Comuna resultado = comunaService.guardarComuna(comuna);

        // Then
        assertNotNull(resultado);
        assertEquals("Santiago Centro", resultado.getNombrecomuna());
        verify(residenciaValidaciones, times(1)).validarComuna(comuna);
        verify(comunaRepository, times(1)).save(comuna);
    }

    @Test
    void testGuardarComuna_ConDatosInvalidos_LanzaExcepcion() {
        // Given
        when(residenciaValidaciones.validarComuna(comuna)).thenReturn(false);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> comunaService.guardarComuna(comuna));
        assertTrue(exception.getMessage().contains("inválidos"));
        verify(residenciaValidaciones, times(1)).validarComuna(comuna);
        verify(comunaRepository, never()).save(any());
    }

    @Test
    void testEliminar_CuandoExiste_EliminaYRetornaMensaje() {
        // Given
        when(comunaRepository.existsById(1)).thenReturn(true);

        // When
        String resultado = comunaService.eliminar(1);

        // Then
        assertEquals("Comuna eliminada exitosamente", resultado);
        verify(comunaRepository, times(1)).existsById(1);
        verify(comunaRepository, times(1)).deleteById(1);
    }

    @Test
    void testEliminar_CuandoNoExiste_RetornaMensajeError() {
        // Given
        when(comunaRepository.existsById(99)).thenReturn(false);

        // When
        String resultado = comunaService.eliminar(99);

        // Then
        assertTrue(resultado.contains("No se encontro"));
        verify(comunaRepository, times(1)).existsById(99);
        verify(comunaRepository, never()).deleteById(any());
    }
}
