package com.deportido.services;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import com.deportido.model.Reserva;
public interface ReservaService {
	 List<Reserva> listar();

	    Optional<Reserva> buscarPorId(Long id);

	    List<Reserva> listarPorUsuario(Long idUsuario);

	    List<Reserva> listarPorEspacio(Long idEspacio);

	    List<Reserva> listarPorFecha(LocalDate fecha);

	    boolean estaDisponible(
	            Long idEspacio,
	            LocalDate fecha,
	            LocalTime horaInicio,
	            LocalTime horaFin);

	    Reserva guardar(Reserva reserva);

	    Reserva actualizar(Long id, Reserva reserva);

	    Reserva cancelar(Long id);

	    void eliminar(Long id);
}
