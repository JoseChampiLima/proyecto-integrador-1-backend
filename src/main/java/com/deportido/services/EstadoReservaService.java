package com.deportido.services;
import java.util.List;
import java.util.Optional;

import com.deportido.model.EstadoReserva;
public interface EstadoReservaService {
	  List<EstadoReserva> listar();

	    Optional<EstadoReserva> buscarPorId(Long id);

	    Optional<EstadoReserva> buscarPorNombre(String nombre);

	    EstadoReserva guardar(EstadoReserva estadoReserva);

	    EstadoReserva actualizar(
	            Long id,
	            EstadoReserva estadoReserva);

	    void eliminar(Long id);
}
