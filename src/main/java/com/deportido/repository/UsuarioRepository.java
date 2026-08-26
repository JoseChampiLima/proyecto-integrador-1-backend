package com.deportido.repository;
import com.deportido.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByDni(String dni);

    boolean existsByCorreo(String correo);

    boolean existsByDni(String dni);
}
