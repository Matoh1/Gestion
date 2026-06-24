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

import com.example.Residencias.DTO.RegionDTO;
import com.example.Residencias.model.Comuna;
import com.example.Residencias.model.Region;
import com.example.Residencias.repository.ComunaRepository;
import com.example.Residencias.repository.RegionRepository;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    @Mock
    private ComunaRepository comunaRepository;

    @Mock
    private ResidenciaValidaciones residenciaValidaciones;

    @InjectMocks
    private RegionService regionService;

    private Region region;
    private RegionDTO regionDTO;

    @BeforeEach
    void setUp() {
        region = new Region();
        region.setId(1);
        region.setNombreregion("Region Metropolitana");

        regionDTO = new RegionDTO();
        regionDTO.setId(1);
        regionDTO.setNombre("Region Metropolitana");
    }

    @Test
    void testObtenerTodos_CuandoExistenRegiones_RetornaListaDTOs() {
        // Given
        when(regionRepository.findAll()).thenReturn(List.of(region));
        when(residenciaValidaciones.convertirRegionADTO(region)).thenReturn(regionDTO);

        // When
        List<RegionDTO> resultado = regionService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Region Metropolitana", resultado.get(0).getNombre());
        verify(regionRepository, times(1)).findAll();
        verify(residenciaValidaciones, times(1)).convertirRegionADTO(region);
    }

    @Test
    void testObtenerTodos_CuandoNoExistenRegiones_RetornaListaVacia() {
        // Given
        when(regionRepository.findAll()).thenReturn(List.of());

        // When
        List<RegionDTO> resultado = regionService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(regionRepository, times(1)).findAll();
        verify(residenciaValidaciones, never()).convertirRegionADTO(any());
    }

    @Test
    void testBuscarPorID_CuandoExiste_RetornaDTO() {
        // Given
        when(regionRepository.findById(1)).thenReturn(Optional.of(region));
        when(residenciaValidaciones.convertirRegionADTO(region)).thenReturn(regionDTO);

        // When
        RegionDTO resultado = regionService.buscarporID(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Region Metropolitana", resultado.getNombre());
        verify(regionRepository, times(1)).findById(1);
    }

    @Test
    void testBuscarPorID_CuandoNoExiste_LanzaExcepcion() {
        // Given
        when(regionRepository.findById(99)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> regionService.buscarporID(99));
        assertTrue(exception.getMessage().contains("Región no encontrada"));
        verify(regionRepository, times(1)).findById(99);
    }

    @Test
    void testGuardarRegion_ConDatosValidos_GuardaYRetorna() {
        // Given
        when(residenciaValidaciones.validarRegion(region)).thenReturn(true);
        when(regionRepository.save(region)).thenReturn(region);

        // When
        Region resultado = regionService.guardarRegion(region);

        // Then
        assertNotNull(resultado);
        assertEquals("Region Metropolitana", resultado.getNombreregion());
        verify(residenciaValidaciones, times(1)).validarRegion(region);
        verify(regionRepository, times(1)).save(region);
    }

    @Test
    void testGuardarRegion_ConDatosInvalidos_LanzaExcepcion() {
        // Given
        when(residenciaValidaciones.validarRegion(region)).thenReturn(false);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> regionService.guardarRegion(region));
        assertTrue(exception.getMessage().contains("inválidos"));
        verify(residenciaValidaciones, times(1)).validarRegion(region);
        verify(regionRepository, never()).save(any());
    }

    @Test
    void testAñadirComunaARegion_CuandoExisten_AsignaYRetornaMensaje() {
        // Given
        Comuna comuna = new Comuna();
        comuna.setId(1);
        comuna.setNombrecomuna("Santiago");

        when(regionRepository.findById(1)).thenReturn(Optional.of(region));
        when(comunaRepository.findById(1)).thenReturn(Optional.of(comuna));
        when(comunaRepository.save(comuna)).thenReturn(comuna);

        // When
        String resultado = regionService.añadirComunaARegion(1, 1);

        // Then
        assertEquals("Comuna añadida a la región exitosamente", resultado);
        assertEquals(region, comuna.getRegion());
        verify(regionRepository, times(1)).findById(1);
        verify(comunaRepository, times(1)).findById(1);
        verify(comunaRepository, times(1)).save(comuna);
    }

    @Test
    void testAñadirComunaARegion_CuandoRegionNoExiste_LanzaExcepcion() {
        // Given
        when(regionRepository.findById(99)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> regionService.añadirComunaARegion(99, 1));
        assertTrue(exception.getMessage().contains("Región no encontrada"));
        verify(regionRepository, times(1)).findById(99);
        verify(comunaRepository, never()).findById(any());
    }

    @Test
    void testEliminar_CuandoExiste_EliminaYRetornaMensaje() {
        // Given
        when(regionRepository.existsById(1)).thenReturn(true);

        // When
        String resultado = regionService.eliminar(1);

        // Then
        assertEquals("Region eliminada exitosamente", resultado);
        verify(regionRepository, times(1)).existsById(1);
        verify(regionRepository, times(1)).deleteById(1);
    }

    @Test
    void testEliminar_CuandoNoExiste_RetornaMensajeError() {
        // Given
        when(regionRepository.existsById(99)).thenReturn(false);

        // When
        String resultado = regionService.eliminar(99);

        // Then
        assertTrue(resultado.contains("No se encontro"));
        verify(regionRepository, times(1)).existsById(99);
        verify(regionRepository, never()).deleteById(any());
    }
}
