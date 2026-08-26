package com.deportido.services;
import java.util.List;
import java.util.Optional;

import com.deportido.model.MetodoPago;

public interface MetodoPagoService {
	 List<MetodoPago> listar();

	    List<MetodoPago> listarActivos();

	    Optional<MetodoPago> buscarPorId(Long id);

	    Optional<MetodoPago> buscarPorNombre(String nombre);

	    MetodoPago guardar(MetodoPago metodoPago);

	    MetodoPago actualizar(
	            Long id,
	            MetodoPago metodoPago);

	    void eliminar(Long id);
}
