package com.deportido.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.deportido.model.EstadoReserva;

public interface EstadoReservaRepository extends JpaRepository<EstadoReserva, Long> {
    Optional<EstadoReserva> findByNombre(String nombre);
    boolean existsByNombreIgnoreCase(String nombre);
}
