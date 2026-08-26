package com.deportido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deportido.model.Mantenimiento;

import java.time.LocalDateTime;
import java.util.List;


public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Long>{
	 List<Mantenimiento> findByEspacioIdEspacio(Long idEspacio);


	    @Query("""
	        SELECT COUNT(m)
	        FROM Mantenimiento m
	        WHERE m.espacio.idEspacio = :idEspacio
	        AND m.estado <> 'CANCELADO'
	        AND :inicio < m.fechaFin
	        AND :fin > m.fechaInicio
	    """)
	    long contarMantenimientosActivos(
	            @Param("idEspacio") Long idEspacio,
	            @Param("inicio") LocalDateTime inicio,
	            @Param("fin") LocalDateTime fin
	    );
}
