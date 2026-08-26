package com.deportido.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.deportido.model.EstadoReserva;

import java.util.Optional;
public interface EstadoReservaRepository extends JpaRepository<EstadoReserva, Long> {
	Optional<EstadoReserva> findByNombre(String nombre);

}
