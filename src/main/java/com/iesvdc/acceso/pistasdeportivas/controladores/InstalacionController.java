package com.iesvdc.acceso.pistasdeportivas.controladores;

import com.iesvdc.acceso.pistasdeportivas.modelos.Instalacion;
import com.iesvdc.acceso.pistasdeportivas.servicios.ServiInstalacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instalacion")
public class InstalacionController {

    @Autowired
    private ServiInstalacion instalacionService;

    @GetMapping
    public List<Instalacion> findAll() {
        return instalacionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instalacion> findOne(@PathVariable @NonNull Long id) {
        Optional<Instalacion> oInstalacion = instalacionService.findById(id);
        return oInstalacion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/hello")
    public ResponseEntity<String> getHello() {
        
        return ResponseEntity.ok(new String("hello"));
    }
    
}
