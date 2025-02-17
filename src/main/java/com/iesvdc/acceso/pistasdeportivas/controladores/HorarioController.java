package com.iesvdc.acceso.pistasdeportivas.controladores;

import com.iesvdc.acceso.pistasdeportivas.modelos.Horario;
import com.iesvdc.acceso.pistasdeportivas.servicios.ServiHorario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/horario")
public class HorarioController {

    @Autowired
    private ServiHorario horarioService;

    @GetMapping
    public List<Horario> findAll() {
        return horarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Horario> findOne(@PathVariable @NonNull Long id) {
        Optional<Horario> oHorario = horarioService.findById(id);
        return oHorario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/hello")
    public ResponseEntity<String> getHello() {
        
        return ResponseEntity.ok(new String("hello"));
    }
    
}
