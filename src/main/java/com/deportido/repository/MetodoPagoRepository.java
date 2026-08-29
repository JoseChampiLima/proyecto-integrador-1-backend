package com.deportido.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.deportido.model.MetodoPago;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {
    Optional<MetodoPago> findByNombre(String nombre);
    List<MetodoPago> findByEstadoTrue();
    boolean existsByNombreIgnoreCase(String nombre);
}
