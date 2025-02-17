package com.iesvdc.acceso.pistasdeportivas.servicios;

import com.iesvdc.acceso.pistasdeportivas.modelos.Horario;
import com.iesvdc.acceso.pistasdeportivas.modelos.Instalacion;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoHorario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServiHorario {

    @Autowired
    private RepoHorario repoHorario;

    public List<Horario> findAll() {
        return repoHorario.findAll();
    }

    public Optional<Horario> findById(Long id) {
        return repoHorario.findById(id);
    }

    public Horario save(Horario horario) {
        return repoHorario.save(horario);
    }

    public void delete(Horario horario) {
        repoHorario.delete(horario);
    }

    public Optional<Horario> update(Long id, Horario horario) {
        return repoHorario.findById(id).map(existing -> {
            existing.setInstalacion(horario.getInstalacion());
            existing.setHoraInicio(horario.getHoraInicio());
            existing.setHoraFin(horario.getHoraFin());
            return repoHorario.save(existing);
        });
    }

    public List<Horario> findByInstalacion(Instalacion instalacion){
        return repoHorario.findByInstalacion(instalacion);
    }

    public List<Horario> findHorariosDisponibles(Instalacion instalacion, LocalDate fechaReserva) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findHorariosDisponibles'");
    }
}
