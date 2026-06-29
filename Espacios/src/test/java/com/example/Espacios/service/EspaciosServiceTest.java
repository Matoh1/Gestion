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

import com.example.Espacios.DTO.EspaciosDTO;
import com.example.Espacios.DTO.ResidenciaExternaDTO;
import com.example.Espacios.model.Espacio;
import com.example.Espacios.model.Espacios;
import com.example.Espacios.repository.EspacioRepository;
import com.example.Espacios.repository.EspaciosRepository;

@ExtendWith(MockitoExtension.class)
class EspaciosServiceTest {

    @Mock
    private EspaciosRepository espaciosRepository;

    @Mock
    private EspacioRepository espacioRepository;

    @Mock
    private EspaciosValidaciones espaciosValidaciones;

    @InjectMocks
    private EspaciosService espaciosService;

    private Espacios espacios;
    private Espacio espacio;
    private EspaciosDTO espaciosDTO;
    private ResidenciaExternaDTO residenciaExternaDTO;

    @BeforeEach
    void setUp() {
        espacio = new Espacio();
        espacio.setId(1);
        espacio.setNombre("Sala multiuso");

        espacios = new Espacios();
        espacios.setId(1);
        espacios.setEspacio(espacio);
        espacios.setResidenciaId(1);

        espaciosDTO = new EspaciosDTO();
        espaciosDTO.setId(1);
        espaciosDTO.setEspacio("Sala multiuso");
        espaciosDTO.setResidenciaId(1);
        espaciosDTO.setResidencia("Residencia Central");

        residenciaExternaDTO = new ResidenciaExternaDTO();
        residenciaExternaDTO.setId(1);
        residenciaExternaDTO.setNombre("Residencia Central");
    }

    @Test
    void testObtenerTodos_CuandoExistenVinculos_RetornaListaDTOs() {
        when(espaciosRepository.findAll()).thenReturn(List.of(espacios));
        when(espaciosValidaciones.convertirEspaciosADTO(espacios)).thenReturn(espaciosDTO);

        List<EspaciosDTO> resultado = espaciosService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Sala multiuso", resultado.get(0).getEspacio());
        verify(espaciosRepository, times(1)).findAll();
        verify(espaciosValidaciones, times(1)).convertirEspaciosADTO(espacios);
    }

    @Test
    void testBuscarPorID_CuandoExiste_RetornaDTO() {
        when(espaciosRepository.findById(1)).thenReturn(Optional.of(espacios));
        when(espaciosValidaciones.convertirEspaciosADTO(espacios)).thenReturn(espaciosDTO);

        EspaciosDTO resultado = espaciosService.buscarporID(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Residencia Central", resultado.getResidencia());
        verify(espaciosRepository, times(1)).findById(1);
        verify(espaciosValidaciones, times(1)).convertirEspaciosADTO(espacios);
    }

    @Test
    void testBuscarPorID_CuandoNoExiste_LanzaExcepcion() {
        when(espaciosRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> espaciosService.buscarporID(99));

        assertTrue(exception.getMessage().contains("No se encontro La Union"));
        verify(espaciosRepository, times(1)).findById(99);
        verify(espaciosValidaciones, never()).convertirEspaciosADTO(any());
    }

    @Test
    void testGuardarEspacios_CuandoResidenciaExiste_GuardaYRetornaVinculo() {
        when(espacioRepository.findById(1)).thenReturn(Optional.of(espacio));
        when(espaciosValidaciones.obtenerResidenciaExterna(1)).thenReturn(residenciaExternaDTO);
        when(espaciosRepository.save(any(Espacios.class))).thenReturn(espacios);

        Espacios resultado = espaciosService.guardarEspacios(1, 1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getResidenciaId());
        verify(espacioRepository, times(1)).findById(1);
        verify(espaciosValidaciones, times(1)).obtenerResidenciaExterna(1);
        verify(espaciosRepository, times(1)).save(any(Espacios.class));
    }

    @Test
    void testGuardarEspacios_CuandoResidenciaNoExiste_LanzaExcepcion() {
        when(espacioRepository.findById(1)).thenReturn(Optional.of(espacio));
        when(espaciosValidaciones.obtenerResidenciaExterna(2)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> espaciosService.guardarEspacios(1, 2));

        assertTrue(exception.getMessage().contains("No se encontro la Residencia"));
        verify(espaciosValidaciones, times(1)).obtenerResidenciaExterna(2);
        verify(espaciosRepository, never()).save(any());
    }

    @Test
    void testEliminarVinculo_CuandoExiste_EliminaYRetornaMensaje() {
        when(espaciosRepository.findById(1)).thenReturn(Optional.of(espacios));

        String resultado = espaciosService.eliminarVinculo(1);

        assertEquals("El vinculo con ID 1 fue eliminado exitosamente", resultado);
        verify(espaciosRepository, times(1)).findById(1);
        verify(espaciosRepository, times(1)).delete(espacios);
    }

    @Test
    void testEliminarVinculo_CuandoNoExiste_LanzaExcepcion() {
        when(espaciosRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> espaciosService.eliminarVinculo(99));

        assertTrue(exception.getMessage().contains("No se encontro vinculo"));
        verify(espaciosRepository, times(1)).findById(99);
        verify(espaciosRepository, never()).delete(any());
    }
}
