package com.deportido.services;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deportido.model.EstadoReserva;
import com.deportido.repository.EstadoReservaRepository;

@Service
public class EstadoReservaServiceImpl implements EstadoReservaService{
	 private final EstadoReservaRepository repository;

	    public EstadoReservaServiceImpl(
	            EstadoReservaRepository repository) {

	        this.repository = repository;
	    }

	    @Override
	    public List<EstadoReserva> listar() {
	        return repository.findAll();
	    }

	    @Override
	    public Optional<EstadoReserva> buscarPorId(Long id) {
	        return repository.findById(id);
	    }

	    @Override
	    public Optional<EstadoReserva> buscarPorNombre(
	            String nombre) {

	        return repository.findByNombre(nombre);
	    }

	    @Override
	    public EstadoReserva guardar(
	            EstadoReserva estadoReserva) {

	        return repository.save(estadoReserva);
	    }

	    @Override
	    public EstadoReserva actualizar(
	            Long id,
	            EstadoReserva estadoReserva) {

	        EstadoReserva existente = repository.findById(id)
	                .orElseThrow(() ->
	                        new RuntimeException(
	                                "Estado de reserva no encontrado"));

	        existente.setNombre(estadoReserva.getNombre());
	        existente.setDescripcion(
	                estadoReserva.getDescripcion());
	        existente.setEstado(estadoReserva.getEstado());

	        return repository.save(existente);
	    }

	    @Override
	    public void eliminar(Long id) {
	        repository.deleteById(id);
	    }
}
