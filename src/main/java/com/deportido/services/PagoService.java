package com.deportido.services;
import java.util.List;
import java.util.Optional;

import com.deportido.model.Pago;
public interface PagoService {
	  List<Pago> listar();

	    Optional<Pago> buscarPorId(Long id);

	    List<Pago> listarPorReserva(Long idReserva);

	    Optional<Pago> buscarPorNumeroOperacion(
	            String nroOperacion);

	    Pago guardar(Pago pago);

	    Pago actualizar(Long id, Pago pago);

	    void eliminar(Long id);
}
