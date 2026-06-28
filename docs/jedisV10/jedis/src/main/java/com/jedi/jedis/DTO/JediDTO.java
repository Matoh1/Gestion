package com.jedi.jedis.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JediDTO {
    private Integer id;
    private String nombre;
    private Integer midiclorianos;
    private SableExternoDTO sable;
}