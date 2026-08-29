package com.deportido.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.deportido.model.Mantenimiento;

public interface MantenimientoService {
    List<Mantenimiento> listar();
    Optional<Mantenimiento> buscarPorId(Long id);
    List<Mantenimiento> listarPorEspacio(Long idEspacio);

    boolean existeMantenimiento(
            Long idEspacio,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin);

    Mantenimiento guardar(Mantenimiento mantenimiento);
    Mantenimiento actualizar(Long id, Mantenimiento mantenimiento);
    void eliminar(Long id);
}
