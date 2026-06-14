package com.jedi.jedis.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.jedi.jedis.DTO.JediDTO;
import com.jedi.jedis.DTO.SableExternoDTO;
import com.jedi.jedis.Repository.JediRepository;
import com.jedi.jedis.model.Jedi;

import reactor.core.publisher.Mono;

@Service
public class JediService {

    @Autowired
    private JediRepository jediRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<JediDTO> obtenerTodos() {
        List<JediDTO> listaDTOs = new ArrayList<>();
        List<Jedi> jedisReales = jediRepository.findAll();
        for (Jedi j : jedisReales) {
            listaDTOs.add(convertirADTO(j));
        }
        return listaDTOs;
    }

    public JediDTO buscarPorId(Integer id) {
        Jedi j = jediRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Guerrero no encontrado en los archivos Jedi"));
        return convertirADTO(j);
    }

    public JediDTO guardar(Jedi nuevoJedi) {
        Jedi guardado = jediRepository.save(nuevoJedi);
        return convertirADTO(guardado);
    }

    private JediDTO convertirADTO(Jedi jedi) {
        JediDTO dto = new JediDTO();
        dto.setId(jedi.getId());
        dto.setNombre(jedi.getNombre());
        dto.setMidiclorianos(jedi.getMidiclorianos());

        try {
            SableExternoDTO sableRecuperado = webClientBuilder.build()
                .get()
                .uri("http://localhost:8082/api/v1/sables/buscar-por-jedi/" + jedi.getId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty()) // importante
                .bodyToMono(SableExternoDTO.class)
                .block();

            dto.setSable(sableRecuperado);
            
        } catch (Exception e) {
            dto.setSable(null); 
        }
        return dto;
    }
}
