package com.jedi.jedis.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jedi.jedis.DTO.JediDTO;
import com.jedi.jedis.Repository.JediRepository;
import com.jedi.jedis.model.Jedi;

@Service
public class JediService {

    @Autowired
    private JediValidaciones jediValidaciones;

    @Autowired
    private JediRepository jediRepository;

    public List<JediDTO> obtenerTodos() {
        List<JediDTO> listaDTOs = new ArrayList<>();
        for (Jedi j : jediRepository.findAll()) {
            listaDTOs.add(jediValidaciones.convertirADTO(j));
        }
        return listaDTOs;
    }

    public JediDTO buscarPorId(Integer id) {
        Jedi j = jediRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Guerrero no encontrado en los archivos Jedi"));
        return jediValidaciones.convertirADTO(j);
    }

    public JediDTO guardar(Jedi nuevoJedi) {
        if(jediValidaciones.validarNullVacio(nuevoJedi)){
            Jedi guardado = jediRepository.save(nuevoJedi);
            return jediValidaciones.convertirADTO(guardado);
        }
        return null;
    }
}