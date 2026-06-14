package com.example.Incidencias.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Incidencias.DTO.IncidenciasDTO;
import com.example.Incidencias.model.Incidencias;
import com.example.Incidencias.repository.IncidenciasRepository;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class IncidenciasServiceTest {

    @Mock
    private IncidenciasRepository incidenciasRepository; // Simulamos el acceso a la base de datos

    @InjectMocks
    private IncidenciasService incidenciasService; // Inyectamos el Mock anterior dentro del servicio real

    private Faker faker = new Faker(); // Generador de datos de prueba

    @BeforeEach
    void setUp() {
        // Inicializa los componentes de simulacion antes de ejecutar cada prueba
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorId_Exitoso() {
        // GIVEN: existe un reporte de incidencias con un id conocido
        Integer idSimulado = 7;
        String tituloAleatorio = faker.lorem().sentence();
        Integer residenciaSimulada = faker.number().numberBetween(1, 100);

        Incidencias reporteFalso = new Incidencias();
        reporteFalso.setId(idSimulado);
        reporteFalso.setTituloReporte(tituloAleatorio);
        reporteFalso.setResidenciaId(residenciaSimulada);
        reporteFalso.setFechaReporte(LocalDate.now());
        reporteFalso.setPrioridad("Alta");

        // Entrenamos al Mock: cuando el repositorio busque este id, devuelve nuestro reporte
        when(incidenciasRepository.findById(idSimulado)).thenReturn(Optional.of(reporteFalso));

        // WHEN: pedimos el reporte al servicio
        IncidenciasDTO resultado = incidenciasService.buscarporID(idSimulado);

        // THEN: el DTO se construye con los datos correctos del reporte
        assertNotNull(resultado, "El DTO resultante no deberia ser nulo");
        assertEquals(tituloAleatorio, resultado.getTituloReporte(), "El titulo del DTO debe coincidir con el de la BD");
        assertEquals(residenciaSimulada, resultado.getResidenciaId(), "El id de residencia debe mapearse al DTO");
        // Verificamos que el servicio consulto al repositorio exactamente 1 vez
        verify(incidenciasRepository, times(1)).findById(idSimulado);
    }

    @Test
    void testBuscarPorId_NoExiste() {
        // GIVEN: el repositorio no encuentra ningun reporte con ese id
        Integer idInexistente = 999;
        when(incidenciasRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // WHEN / THEN: el servicio debe lanzar una excepcion controlada
        assertThrows(RuntimeException.class, () -> incidenciasService.buscarporID(idInexistente));
        verify(incidenciasRepository, times(1)).findById(idInexistente);
    }
}
