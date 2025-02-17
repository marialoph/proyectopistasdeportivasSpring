package com.iesvdc.acceso.pistasdeportivas.servicios;

import com.iesvdc.acceso.pistasdeportivas.modelos.Instalacion;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoInstalacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiInstalacion {

    @Autowired
    private RepoInstalacion repoInstalacion;

    public List<Instalacion> findAll() {
        return repoInstalacion.findAll();
    }

    public Optional<Instalacion> findById(Long id) {
        return repoInstalacion.findById(id);
    }

    public Instalacion save(Instalacion instalacion) {
        return repoInstalacion.save(instalacion);
    }

    public void delete(Instalacion instalacion) {
        repoInstalacion.delete(instalacion);
    }

    public Optional<Instalacion> update(Long id, Instalacion instalacion) {
        return repoInstalacion.findById(id).map(existing -> {
            existing.setNombre(instalacion.getNombre());
            return repoInstalacion.save(existing);
        });
    }
}
