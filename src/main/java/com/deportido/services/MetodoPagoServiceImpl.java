package com.deportido.services;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deportido.model.MetodoPago;
import com.deportido.repository.MetodoPagoRepository;

@Service
public class MetodoPagoServiceImpl implements MetodoPagoService{
	 private final MetodoPagoRepository repository;

	    public MetodoPagoServiceImpl(
	            MetodoPagoRepository repository) {

	        this.repository = repository;
	    }

	    @Override
	    public List<MetodoPago> listar() {
	        return repository.findAll();
	    }

	    @Override
	    public List<MetodoPago> listarActivos() {
	        return repository.findByEstadoTrue();
	    }

	    @Override
	    public Optional<MetodoPago> buscarPorId(Long id) {
	        return repository.findById(id);
	    }

	    @Override
	    public Optional<MetodoPago> buscarPorNombre(
	            String nombre) {

	        return repository.findByNombre(nombre);
	    }

	    @Override
	    public MetodoPago guardar(MetodoPago metodoPago) {
	        return repository.save(metodoPago);
	    }

	    @Override
	    public MetodoPago actualizar(
	            Long id,
	            MetodoPago metodoPago) {

	        MetodoPago existente = repository.findById(id)
	                .orElseThrow(() ->
	                        new RuntimeException(
	                                "Método de pago no encontrado"));

	        existente.setNombre(metodoPago.getNombre());
	        existente.setEstado(metodoPago.getEstado());

	        return repository.save(existente);
	    }

	    @Override
	    public void eliminar(Long id) {
	        repository.deleteById(id);
	    }
}
