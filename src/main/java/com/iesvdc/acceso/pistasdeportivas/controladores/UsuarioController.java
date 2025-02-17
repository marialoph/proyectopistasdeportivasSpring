package com.iesvdc.acceso.pistasdeportivas.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.iesvdc.acceso.pistasdeportivas.modelos.Usuario;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoUsuario;
import com.iesvdc.acceso.pistasdeportivas.servicios.ServiUsuario;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {    

    private final ServiUsuario serviUsuario;
    private final RepoUsuario repoUsuario;

    @Autowired
    public UsuarioController(RepoUsuario repoUsuario, ServiUsuario serviUsuario) {
        this.repoUsuario = repoUsuario;
        this.serviUsuario = serviUsuario;
    }

    @GetMapping("/me")
    public Usuario getUser() {
        Usuario u = serviUsuario.getLoggedUser();
        System.out.println("👤 Usuario logueado: " + u.getUsername() + " - Rol: " + u.getTipo());
        return u;
    }
    
    //Actualizar usuario logueado
    @PostMapping
    public ResponseEntity<Usuario> update(@RequestBody Usuario u) {
        Usuario loggedUser = serviUsuario.getLoggedUser();
        if (u.getId().equals(loggedUser.getId())) {
            if (u.getPassword() == null || u.getPassword().length() <= 4) {
                u.setPassword(loggedUser.getPassword()); 
            }
            return ResponseEntity.ok(serviUsuario.save(u));
        }
        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        List<Usuario> usuarios = serviUsuario.obtenerTodosUsuarios();  
        return ResponseEntity.ok(usuarios);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        Optional<Usuario> usuario = serviUsuario.obtenerUsuarioPorId(id);
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Optional<Usuario> usuario = serviUsuario.obtenerUsuarioPorId(id);
        if (!usuario.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuarioExistente = usuario.get();
        usuarioExistente.setUsername(usuarioActualizado.getUsername());
        usuarioExistente.setEmail(usuarioActualizado.getEmail());
        usuarioExistente.setEnabled(usuarioActualizado.isEnabled());
        usuarioExistente.setTipo(usuarioActualizado.getTipo());

        if (usuarioActualizado.getPassword() != null && usuarioActualizado.getPassword().length() > 4) {
            usuarioExistente.setPassword(usuarioActualizado.getPassword());
        }

        return ResponseEntity.ok(serviUsuario.save(usuarioExistente));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        System.out.println("Intentando eliminar usuario con ID: " + id);
        Optional<Usuario> usuarioOpt = serviUsuario.findById(id);
        if (usuarioOpt.isEmpty()) {
            System.out.println("Usuario no encontrado.");
            return ResponseEntity.notFound().build();
        }
        
        serviUsuario.eliminarUsuario(id);
        System.out.println("Usuario eliminado correctamente.");
        return ResponseEntity.noContent().build();
    }

@PreAuthorize("hasRole('ADMIN')")
@PostMapping("/add")
public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
    System.out.println("Recibiendo solicitud para crear usuario: " + usuario);
    
    if (usuario.getUsername() == null || usuario.getUsername().isEmpty() ||
        usuario.getEmail() == null || usuario.getEmail().isEmpty() ||
        usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
        
        return ResponseEntity.badRequest().build(); 
    }

    Usuario nuevoUsuario = repoUsuario.save(usuario); 
    System.out.println("Usuario creado con éxito: " + nuevoUsuario);
    return ResponseEntity.status(201).body(nuevoUsuario); 
}

    

}
