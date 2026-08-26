package com.deportido.services;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.deportido.model.Mantenimiento;

public interface MantenimientoService {
	List<Mantenimiento> listar();

    Optional<Mantenimiento> buscarPorId(Long id);

    List<Mantenimiento> listarPorEspacio(Long idEspacio);

    boolean existeMantenimiento(
            Long idEspacio,
            LocalDateTime inicio,
            LocalDateTime fin);

    Mantenimiento guardar(Mantenimiento mantenimiento);

    Mantenimiento actualizar(
            Long id,
            Mantenimiento mantenimiento);

    void eliminar(Long id);
}
