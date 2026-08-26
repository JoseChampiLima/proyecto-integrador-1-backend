package com.deportido.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.deportido.model.Pago;

import java.util.List;
import java.util.Optional;
public interface PagoRepository extends JpaRepository<Pago, Long>{

    List<Pago> findByReservaIdReserva(Long idReserva);

    Optional<Pago> findByNroOperacion(String nroOperacion);

}
