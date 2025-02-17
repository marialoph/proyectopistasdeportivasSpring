package com.iesvdc.acceso.pistasdeportivas.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iesvdc.acceso.pistasdeportivas.modelos.Horario;
import com.iesvdc.acceso.pistasdeportivas.modelos.Instalacion;
import com.iesvdc.acceso.pistasdeportivas.modelos.Reserva;
import com.iesvdc.acceso.pistasdeportivas.modelos.Usuario;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoReserva;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoUsuario;
import com.iesvdc.acceso.pistasdeportivas.servicios.ServiHorario;
import com.iesvdc.acceso.pistasdeportivas.servicios.ServiInstalacion;
import com.iesvdc.acceso.pistasdeportivas.servicios.ServiMisReservas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/mis-reservas")
public class ReservaController {

    @Autowired
    ServiMisReservas serviMisReservas;
    @Autowired
    RepoReserva repoReserva;

    @Autowired
    ServiHorario serviHorario;

    @Autowired
    ServiInstalacion serviInstalacion;

     @Autowired
    RepoUsuario repoUsuario;

    @GetMapping
    public List<Reserva> findAll() {        
        return  repoReserva.findAll();
    }



    
@PostMapping
public ResponseEntity<String> create(@RequestBody Reserva reserva) {
    try {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaReserva = reserva.getFecha();

        //No permitir reservas en días anteriores
        if (fechaReserva.isBefore(hoy)) {
            return ResponseEntity.badRequest().body("No se permiten reservas en días anteriores.");
        }

        //No permitir reservas más de dos semanas en el futuro
        if (fechaReserva.isAfter(hoy.plusWeeks(2))) {
            return ResponseEntity.badRequest().body("No puedes reservar con más de dos semanas de anticipación.");
        }

        //No permitir más de una reserva en el mismo día
        List<Reserva> reservasUsuario = serviMisReservas.findReservasByUsuario();
        boolean yaReservado = reservasUsuario.stream()
                .anyMatch(r -> r.getFecha().equals(fechaReserva));

        if (yaReservado) {
            return ResponseEntity.badRequest().body("Ya tienes una reserva para esta fecha.");
        }

        //Si pasa todas las validaciones, guardar la reserva
        reserva = serviMisReservas.saveReserva(reserva);
        return ResponseEntity.ok("Reserva creada con éxito");

    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Error al procesar la reserva.");
    }
}

    
@GetMapping("/{id}")
public ResponseEntity<Reserva> getReserva(@PathVariable long id) {
    Optional<Reserva> reserva = repoReserva.findById(id);
    if (reserva.isPresent()) {
        return ResponseEntity.ok(reserva.get());
    } else {
        return ResponseEntity.notFound().build();
    }
}

@DeleteMapping("/{id}")
public ResponseEntity<Reserva> delete(@PathVariable long id) {
    Optional<Reserva> oReserva;
    try {
        oReserva = serviMisReservas.deleteReserva(id);
        if (oReserva.isPresent()) {
            return ResponseEntity.ok(oReserva.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    } catch (Exception e) {
        return ResponseEntity.badRequest().build();
    }
}

    
@GetMapping("/horario/instalacion/{id}/fecha/{fecha}")
public List<Horario> horariosPorInstalacionFecha(
    @PathVariable long id,
    @PathVariable String fecha) {

    LocalDate fechaReserva = LocalDate.parse(fecha);
    Optional<Instalacion> oInstalacion = serviInstalacion.findById(id);

    if (oInstalacion.isPresent()) {
        List<Horario> todosHorarios = serviHorario.findByInstalacion(oInstalacion.get());
        
        // Filtrar horarios que NO tienen reserva en esa fecha
        List<Horario> horariosDisponibles = new ArrayList<>();
        for (Horario horario : todosHorarios) {
            boolean estaReservado = repoReserva.existsByHorarioAndFecha(horario, fechaReserva);
            if (!estaReservado) {
                horariosDisponibles.add(horario);
            }
        }
        return horariosDisponibles;
    } else {
        return new ArrayList<>();
    }
}

    
// Método para manejar la actualización de la reserva
@PutMapping("/{id}")
public ResponseEntity<Reserva> updateReserva(@PathVariable long id, @RequestBody Reserva reserva) {
    try {
        // Verificar si la reserva existe
        Optional<Reserva> existingReserva = repoReserva.findById(id);
        if (existingReserva.isPresent()) {
            // Actualizar la reserva
            Reserva reservaExistente = existingReserva.get();
            reservaExistente.setFecha(reserva.getFecha());
            reservaExistente.setHorario(reserva.getHorario());
            reservaExistente.setUsuario(reserva.getUsuario());

            // Guardar la reserva actualizada
            Reserva updatedReserva = repoReserva.save(reservaExistente);
            return ResponseEntity.ok(updatedReserva);
        } else {
            return ResponseEntity.notFound().build();
        }
    } catch (Exception e) {
        return ResponseEntity.badRequest().build();
    }
}

@GetMapping("/instalaciones")
public List<Instalacion> getInstalaciones() {
    return serviInstalacion.findAll();
}

@GetMapping("/usuarios")
    public List<Usuario> getUsuarios() {
        return repoUsuario.findAll();  
    }
}
