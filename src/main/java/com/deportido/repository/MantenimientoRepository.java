package com.deportido.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deportido.model.Mantenimiento;

public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Long> {

    List<Mantenimiento> findByEspacioIdEspacio(Long idEspacio);

    List<Mantenimiento> findByEspacioIdEspacioAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
            Long idEspacio, LocalDate fecha1, LocalDate fecha2);
}
