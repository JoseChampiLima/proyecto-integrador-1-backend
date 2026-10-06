package com.deportido.repository;

import java.util.List; 
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.deportido.model.Pago;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByReservaIdReserva(Long idReserva);
    Optional<Pago> findByNroOperacion(String nroOperacion);
    boolean existsByNroOperacion(String nroOperacion);
    
    @Query("""
    	    SELECT COALESCE(SUM(p.monto), 0)
    	    FROM Pago p
    	    WHERE UPPER(p.estado) = 'APROBADO'
    	    AND p.fechaPago >= :fechaDesde
    	    AND p.fechaPago < :fechaHasta
    	""")
    	BigDecimal obtenerIngresosPorRango(
    	        @Param("fechaDesde") LocalDateTime fechaDesde,
    	        @Param("fechaHasta") LocalDateTime fechaHasta
    	);
}
