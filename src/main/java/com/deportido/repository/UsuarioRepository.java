package com.deportido.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.deportido.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByDni(String dni);
    boolean existsByCorreoIgnoreCase(String correo);
    boolean existsByDni(String dni);
}
