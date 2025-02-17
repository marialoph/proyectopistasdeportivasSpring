package com.iesvdc.acceso.pistasdeportivas.controladores;

import com.iesvdc.acceso.pistasdeportivas.modelos.Instalacion;
import com.iesvdc.acceso.pistasdeportivas.servicios.ServiInstalacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/instalacion")
public class InstalacionAdminController {

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

    @PostMapping
    public ResponseEntity<Instalacion> create(@RequestBody Instalacion instalacion) throws URISyntaxException {
        Instalacion created = instalacionService.save(instalacion);
        return ResponseEntity.created(new URI("/api/instalacion/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instalacion> update(@PathVariable Long id, @RequestBody Instalacion instalacion) {
        Optional<Instalacion> updated = instalacionService.update(id, instalacion);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        Optional<Instalacion> oInstalacion = instalacionService.findById(id);
        if (oInstalacion.isPresent()) {
            instalacionService.delete(oInstalacion.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
