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

import com.example.Espacios.DTO.EspacioDTO;
import com.example.Espacios.DTO.ResidenciaExternaDTO;
import com.example.Espacios.model.Espacio;
import com.example.Espacios.model.Espacios;
import com.example.Espacios.repository.EspacioRepository;
import com.example.Espacios.repository.EspaciosRepository;

@ExtendWith(MockitoExtension.class)
class EspacioServiceTest {

    @Mock
    private EspacioRepository espacioRepository;

    @Mock
    private EspaciosRepository espaciosRepository;

    @Mock
    private EspaciosValidaciones espaciosValidaciones;

    @InjectMocks
    private EspacioService espacioService;

    private Espacio espacio;
    private Espacios vinculo;
    private EspacioDTO espacioDTO;
    private ResidenciaExternaDTO residenciaExternaDTO;

    @BeforeEach
    void setUp() {
        espacio = new Espacio();
        espacio.setId(1);
        espacio.setNombre("Sala de estudio");
        espacio.setTipo("Estudio");
        espacio.setCapacidad(25);

        vinculo = new Espacios();
        vinculo.setId(1);
        vinculo.setEspacio(espacio);
        vinculo.setResidenciaId(1);

        espacioDTO = new EspacioDTO();
        espacioDTO.setId(1);
        espacioDTO.setNombre("Sala de estudio");
        espacioDTO.setTipo("Estudio");
        espacioDTO.setCapacidad(25);

        residenciaExternaDTO = new ResidenciaExternaDTO();
        residenciaExternaDTO.setId(1);
        residenciaExternaDTO.setNombre("Residencia1");
    }

    @Test
    void testObtenerTodos_CuandoExistenEspacios_RetornaListaDTOs() {
        when(espacioRepository.findAll()).thenReturn(List.of(espacio));
        when(espaciosValidaciones.convertirEspacioADTO(espacio)).thenReturn(espacioDTO);

        List<EspacioDTO> resultado = espacioService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Sala de estudio", resultado.get(0).getNombre());
        verify(espacioRepository, times(1)).findAll();
        verify(espaciosValidaciones, times(1)).convertirEspacioADTO(espacio);
    }

    @Test
    void testObtenerTodos_CuandoNoExistenEspacios_RetornaListaVacia() {
        when(espacioRepository.findAll()).thenReturn(List.of());

        List<EspacioDTO> resultado = espacioService.obtenerTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(espacioRepository, times(1)).findAll();
        verify(espaciosValidaciones, never()).convertirEspacioADTO(any());
    }

    @Test
    void testBuscarPorID_CuandoExiste_RetornaDTO() {
        when(espacioRepository.findById(1)).thenReturn(Optional.of(espacio));
        when(espaciosValidaciones.convertirEspacioADTO(espacio)).thenReturn(espacioDTO);

        EspacioDTO resultado = espacioService.buscarporID(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Sala de estudio", resultado.getNombre());
        verify(espacioRepository, times(1)).findById(1);
        verify(espaciosValidaciones, times(1)).convertirEspacioADTO(espacio);
    }

    @Test
    void testBuscarPorID_CuandoNoExiste_LanzaExcepcion() {
        when(espacioRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> espacioService.buscarporID(99));

        assertTrue(exception.getMessage().contains("No se encontro el Espacio"));
        verify(espacioRepository, times(1)).findById(99);
    }

    @Test
    void testGuardarEspacio_ConDatosValidos_GuardaYRetorna() {
        when(espaciosValidaciones.validarEspacio(espacio)).thenReturn(true);
        when(espacioRepository.save(espacio)).thenReturn(espacio);

        Espacio resultado = espacioService.guardarEspacio(espacio);

        assertNotNull(resultado);
        assertEquals("Sala de estudio", resultado.getNombre());
        verify(espaciosValidaciones, times(1)).validarEspacio(espacio);
        verify(espacioRepository, times(1)).save(espacio);
    }

    @Test
    void testGuardarEspacio_ConDatosInvalidos_LanzaExcepcion() {
        when(espaciosValidaciones.validarEspacio(espacio)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> espacioService.guardarEspacio(espacio));

        assertTrue(exception.getMessage().contains("Datos de espacio inválidos"));
        verify(espaciosValidaciones, times(1)).validarEspacio(espacio);
        verify(espacioRepository, never()).save(any());
    }

    @Test
    void testBorrarEspacio_CuandoExiste_EliminaYRetornaMensaje() {
        when(espacioRepository.findById(1)).thenReturn(Optional.of(espacio));

        String resultado = espacioService.borrarEspacio(1);

        assertEquals("Espacio con ID 1 fue eliminado exitosamente", resultado);
        verify(espacioRepository, times(1)).findById(1);
        verify(espacioRepository, times(1)).delete(espacio);
    }

    @Test
    void testBorrarEspacio_CuandoNoExiste_LanzaExcepcion() {
        when(espacioRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> espacioService.borrarEspacio(99));

        assertTrue(exception.getMessage().contains("No se encontró el Espacio"));
        verify(espacioRepository, times(1)).findById(99);
    }

    @Test
    void testAsignarEspacioAResidencia_CuandoResidenciaExiste_GuardaVinculo() {
        when(espacioRepository.findById(1)).thenReturn(Optional.of(espacio));
        when(espaciosValidaciones.obtenerResidenciaExterna(1)).thenReturn(residenciaExternaDTO);
        when(espaciosRepository.save(any(Espacios.class))).thenReturn(vinculo);

        Espacio resultado = espacioService.asignarEspacioAResidencia(1, 1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(espacioRepository, times(1)).findById(1);
        verify(espaciosValidaciones, times(1)).obtenerResidenciaExterna(1);
        verify(espaciosRepository, times(1)).save(any(Espacios.class));
    }

    @Test
    void testAsignarEspacioAResidencia_CuandoResidenciaNoExiste_LanzaExcepcion() {
        when(espacioRepository.findById(1)).thenReturn(Optional.of(espacio));
        when(espaciosValidaciones.obtenerResidenciaExterna(2)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> espacioService.asignarEspacioAResidencia(1, 2));

        assertTrue(exception.getMessage().contains("No se encontro la residencia"));
        verify(espacioRepository, times(1)).findById(1);
        verify(espaciosRepository, never()).save(any());
    }

    @Test
    void testEliminarVinculo_CuandoExiste_EliminaYRetornaMensaje() {
        when(espacioRepository.findById(1)).thenReturn(Optional.of(espacio));
        when(espaciosRepository.findByEsResId(1, 1)).thenReturn(vinculo);

        String resultado = espacioService.eliminarVinculo(1, 1);

        assertEquals("Vinculo entre espacio con ID 1 y residencia con ID 1 fue eliminado exitosamente", resultado);
        verify(espacioRepository, times(1)).findById(1);
        verify(espaciosRepository, times(1)).delete(vinculo);
    }

    @Test
    void testEliminarVinculo_CuandoNoExiste_LanzaExcepcion() {
        when(espacioRepository.findById(1)).thenReturn(Optional.of(espacio));
        when(espaciosRepository.findByEsResId(1, 2)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> espacioService.eliminarVinculo(1, 2));

        assertTrue(exception.getMessage().contains("No se encontro el vinculo"));
        verify(espacioRepository, times(1)).findById(1);
        verify(espaciosRepository, never()).delete(any());
    }
}
