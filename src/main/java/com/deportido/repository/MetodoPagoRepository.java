package com.deportido.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.deportido.model.MetodoPago;

import java.util.List;
import java.util.Optional;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long>{
    Optional<MetodoPago> findByNombre(String nombre);

    List<MetodoPago> findByEstadoTrue();
}
