package com.deportido.services;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deportido.model.Pago;
import com.deportido.repository.PagoRepository;

@Service
public class PagoServiceImpl implements PagoService{
	 private final PagoRepository repository;

	    public PagoServiceImpl(PagoRepository repository) {
	        this.repository = repository;
	    }

	    @Override
	    public List<Pago> listar() {
	        return repository.findAll();
	    }

	    @Override
	    public Optional<Pago> buscarPorId(Long id) {
	        return repository.findById(id);
	    }

	    @Override
	    public List<Pago> listarPorReserva(Long idReserva) {
	        return repository.findByReservaIdReserva(idReserva);
	    }

	    @Override
	    public Optional<Pago> buscarPorNumeroOperacion(
	            String nroOperacion) {

	        return repository.findByNroOperacion(nroOperacion);
	    }

	    @Override
	    public Pago guardar(Pago pago) {
	        return repository.save(pago);
	    }

	    @Override
	    public Pago actualizar(Long id, Pago pago) {

	        Pago existente = repository.findById(id)
	                .orElseThrow(() ->
	                        new RuntimeException(
	                                "Pago no encontrado"));

	        existente.setMonto(pago.getMonto());
	        existente.setNroOperacion(
	                pago.getNroOperacion());
	        existente.setEstado(pago.getEstado());
	        existente.setMetodoPago(
	                pago.getMetodoPago());

	        return repository.save(existente);
	    }

	    @Override
	    public void eliminar(Long id) {
	        repository.deleteById(id);
	    }
}
