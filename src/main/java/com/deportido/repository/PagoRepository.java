package com.deportido.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.deportido.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByReservaIdReserva(Long idReserva);
    Optional<Pago> findByNroOperacion(String nroOperacion);
    boolean existsByNroOperacion(String nroOperacion);
}
