package com.deportido.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deportido.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUsuarioIdUsuario(Long idUsuario);
    List<Reserva> findByEspacioIdEspacio(Long idEspacio);
    List<Reserva> findByFechaReserva(LocalDate fechaReserva);
    List<Reserva> findByEstadoReservaIdEstadoReserva(Long idEstadoReserva);
    List<Reserva> findByEspacioIdEspacioAndFechaReserva(Long idEspacio, LocalDate fechaReserva);

    @Query("""
        SELECT COUNT(r)
        FROM Reserva r
        WHERE r.espacio.idEspacio = :idEspacio
        AND r.fechaReserva = :fecha
        AND UPPER(r.estadoReserva.nombre) IN ('PENDIENTE', 'CONFIRMADA')
        AND :horaInicio < r.horaFin
        AND :horaFin > r.horaInicio
    """)
    long contarReservasSolapadas(
            @Param("idEspacio") Long idEspacio,
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin);

    @Query("""
        SELECT COUNT(r)
        FROM Reserva r
        WHERE r.idReserva <> :idReserva
        AND r.espacio.idEspacio = :idEspacio
        AND r.fechaReserva = :fecha
        AND UPPER(r.estadoReserva.nombre) IN ('PENDIENTE', 'CONFIRMADA')
        AND :horaInicio < r.horaFin
        AND :horaFin > r.horaInicio
    """)
    long contarReservasSolapadasExcluyendo(
            @Param("idReserva") Long idReserva,
            @Param("idEspacio") Long idEspacio,
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin);
    
    
    //Consulta de dashboard
    @Query("""
    	    SELECT COUNT(r)
    	    FROM Reserva r
    	    WHERE r.fechaReserva BETWEEN :fechaDesde AND :fechaHasta
    	""")
    	long contarReservasPorRango(
    	        @Param("fechaDesde") LocalDate fechaDesde,
    	        @Param("fechaHasta") LocalDate fechaHasta
    	);


    	@Query("""
    	    SELECT COUNT(DISTINCT r.usuario.idUsuario)
    	    FROM Reserva r
    	    WHERE r.fechaReserva BETWEEN :fechaDesde AND :fechaHasta
    	""")
    	long contarClientesPorRango(
    	        @Param("fechaDesde") LocalDate fechaDesde,
    	        @Param("fechaHasta") LocalDate fechaHasta
    	);


    	@Query("""
    	    SELECT r.estadoReserva.nombre, COUNT(r)
    	    FROM Reserva r
    	    WHERE r.fechaReserva BETWEEN :fechaDesde AND :fechaHasta
    	    GROUP BY r.estadoReserva.nombre
    	    ORDER BY COUNT(r) DESC
    	""")
    	List<Object[]> contarReservasPorEstado(
    	        @Param("fechaDesde") LocalDate fechaDesde,
    	        @Param("fechaHasta") LocalDate fechaHasta
    	);


    	@Query("""
    	    SELECT r.espacio.nombre, COUNT(r)
    	    FROM Reserva r
    	    WHERE r.fechaReserva BETWEEN :fechaDesde AND :fechaHasta
    	    GROUP BY r.espacio.idEspacio, r.espacio.nombre
    	    ORDER BY COUNT(r) DESC
    	""")
    	List<Object[]> contarReservasPorEspacio(
    	        @Param("fechaDesde") LocalDate fechaDesde,
    	        @Param("fechaHasta") LocalDate fechaHasta
    	);
}
