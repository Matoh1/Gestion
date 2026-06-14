package com.jedi.jedis.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jedi.jedis.DTO.JediDTO;
import com.jedi.jedis.Repository.JediRepository;
import com.jedi.jedis.model.Jedi;
import com.jedi.jedis.service.JediService;

import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
class JedisApplicationTests {

	@Mock
	private JediRepository jediRepository; // Simulamos el acceso a la base de datos
	
	@InjectMocks
	private JediService jediService; // Inyectamos el Mock anterior dentro del servicio real
	private Faker faker = new Faker(); // Nuestro generador de datos de Star Wars
	@BeforeEach
	void setUp() {
		// Inicializa los componentes de simulación antes de ejecutar cada prueba
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testBuscarPorId_Exitoso() {
		// GIVEN: Dado un escenario inicial en la galaxia
		Integer idSimulado = 42;
		String nombreAleatorio = faker.starWars().character(); // Genera nombres como Luke,Ahsoka o Vader
		Jedi jediFalso = new Jedi();
		jediFalso.setId(idSimulado);
		jediFalso.setNombre(nombreAleatorio);
		jediFalso.setMidiclorianos(faker.number().numberBetween(10000, 25000));
		// Entrenamos al Mock: Cuando el repositorio busque este ID, responderá con nuestro Jedifalso
		when(jediRepository.findById(idSimulado)).thenReturn(Optional.of(jediFalso));
		// WHEN: Cuando ejecutamos la acción del servicio que queremos evaluar

		JediDTO resultado = jediService.buscarPorId(idSimulado);
		// THEN: Entonces validamos que las compuertas de datos funcionen de forma idónea
		assertNotNull(resultado, "El DTO resultante no debería ser nulo");
		assertEquals(nombreAleatorio, resultado.getNombre(), "El nombre transformado al DTO debe coincidir con el de la DB");
		// Verificamos que el servicio realmente haya consultado al repositorio exactamente 1 vez
		verify(jediRepository, times(1)).findById(idSimulado);
	}

}
