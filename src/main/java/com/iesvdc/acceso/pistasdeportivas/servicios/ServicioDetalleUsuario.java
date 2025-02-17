package com.iesvdc.acceso.pistasdeportivas.servicios;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.iesvdc.acceso.pistasdeportivas.modelos.Usuario;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoUsuario;

import java.util.List;

@Service
public class ServicioDetalleUsuario implements UserDetailsService {

    @Autowired
    private RepoUsuario repoUsuario;
    
    @Override
    public UserDetails loadUserByUsername(String username) 
      throws UsernameNotFoundException {
        List<Usuario> lu = repoUsuario.findByUsername(username);
        if (lu.size()>0) {
                return User.builder()
                        .username(lu.get(0).getUsername())
                        .password(lu.get(0).getPassword())
                        .disabled(!lu.get(0).isEnabled())
                        .roles(lu.get(0).getTipo().name())
                        .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }        
    }
    
}
