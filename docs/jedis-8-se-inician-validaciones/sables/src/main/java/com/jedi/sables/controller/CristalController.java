package com.jedi.sables.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jedi.sables.model.Cristal;
import com.jedi.sables.service.CristalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/cristales")
public class CristalController {

    @Autowired
    private CristalService cristalService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getMethodName(@PathVariable Integer id) {
        try{
            Cristal cristal = cristalService.buscarPorId(id);
            return new ResponseEntity<>(cristal, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("Cristal no encontrado", HttpStatus.BAD_REQUEST);
        }
    }
    
}
