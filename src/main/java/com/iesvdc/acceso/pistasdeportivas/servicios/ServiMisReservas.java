package com.iesvdc.acceso.pistasdeportivas.servicios;


import com.iesvdc.acceso.pistasdeportivas.modelos.Horario;
import com.iesvdc.acceso.pistasdeportivas.modelos.Reserva;
import com.iesvdc.acceso.pistasdeportivas.modelos.Usuario;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoReserva;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServiMisReservas {

    @Autowired
    private RepoReserva repoReserva;

    @Autowired
    private ServiUsuario serviUsuario;

    @Autowired
    private RepoUsuario repoUsuario;


    /**
     * Obtiene las reservas del usuario que hizo login.
     *
     * @param usuario el usuario del que se quieren obtener las reservas
     * @return lista de reservas realizadas por el usuario
     */
    public List<Reserva> findReservasByUsuario() {
        return repoReserva.findByUsuario(serviUsuario.getLoggedUser());
    }
        
    /**
     * Crea o actualiza una reserva. Si tiene ID la reserva actualizamos.
     * Si actualizamos comprobamos que la reserva esté a nombre del usuario
     * que hizo login. En caso contrario lanzamos excepción.
     * 
     * @param reserva la reserva a guardar
     * @return la reserva guardada
     * @throws Exception 
     */
    public Reserva saveReserva(Reserva reserva) throws Exception {
        Usuario uLogged = serviUsuario.getLoggedUser();
    
        if (reserva.getId() != null) {
            Optional<Reserva> oReserva = repoReserva.findById(reserva.getId());
            if (oReserva.isPresent()) {
                if (!oReserva.get().getUsuario().equals(uLogged) && !uLogged.getTipo().equals("ADMIN")) {
                    throw new Exception("No tienes permiso para modificar esta reserva");    
                }
            } else {
                throw new Exception("Reserva inexistente");
            }
        } else {
            if (reserva.getUsuario() != null) { 
                Optional<Usuario> usuarioSeleccionado = repoUsuario.findById(reserva.getUsuario().getId());
                usuarioSeleccionado.ifPresent(reserva::setUsuario);
            } else {
                reserva.setUsuario(uLogged);
            }
        }
        
        return repoReserva.save(reserva);
    }
    
    /**
     * Elimina una reserva por su identificador. si el usuario no coincide 
     * lo tratamos como si no existe la reserva en la BBDD.
     *
     * @param id el id de la reserva a eliminar
     */
    public Optional<Reserva> deleteReserva(Long id) {
        Optional<Reserva> reserva = repoReserva.findById(id);
        if (reserva.isPresent()) {
            // Verifica si el usuario logueado es el mismo que el de la reserva o si el usuario logueado es admin
            if (reserva.get().getUsuario().equals(serviUsuario.getLoggedUser()) || serviUsuario.getLoggedUser().getTipo().equals("ADMIN")) {
                System.out.println("Eliminando reserva con id: " + id); 
                repoReserva.deleteById(id); 
            } else {
                // Si el usuario no tiene permiso para eliminar la reserva
                System.out.println("Usuario logueado no tiene permiso para eliminar esta reserva. Usuario logueado: " 
                                   + serviUsuario.getLoggedUser().getUsername() 
                                   + " | Usuario de la reserva: "
                                   + reserva.get().getUsuario().getUsername());
                return Optional.empty();  
            }
        } else {
            // Si la reserva no existe en la base de datos
            System.out.println("Reserva con id " + id + " no encontrada.");
        }
        return reserva;
    }
    
    
    public Object existeReservaParaHorario(Horario h, LocalDate fecha) {
        throw new UnsupportedOperationException("Unimplemented method 'existeReservaParaHorario'");
    }
}
