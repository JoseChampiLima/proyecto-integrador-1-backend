package com.deportido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deportido.model.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface ReservaRepository  extends JpaRepository<Reserva, Long>{
	 List<Reserva> findByUsuarioIdUsuario(Long idUsuario);

	    List<Reserva> findByEspacioIdEspacio(Long idEspacio);

	    List<Reserva> findByFechaReserva(LocalDate fechaReserva);

	    List<Reserva> findByEstadoReservaIdEstadoReserva(
	            Long idEstadoReserva
	    );

	    List<Reserva> findByEspacioIdEspacioAndFechaReserva(
	            Long idEspacio,
	            LocalDate fechaReserva
	    );


	    @Query("""
	        SELECT COUNT(r)
	        FROM Reserva r
	        WHERE r.espacio.idEspacio = :idEspacio
	        AND r.fechaReserva = :fecha
	        AND r.estadoReserva.nombre IN ('PENDIENTE', 'CONFIRMADA')
	        AND :horaInicio < r.horaFin
	        AND :horaFin > r.horaInicio
	    """)
	    long contarReservasSolapadas(
	            @Param("idEspacio") Long idEspacio,
	            @Param("fecha") LocalDate fecha,
	            @Param("horaInicio") LocalTime horaInicio,
	            @Param("horaFin") LocalTime horaFin
	    );
}
