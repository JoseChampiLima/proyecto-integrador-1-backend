package com.deportido.services;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deportido.model.TipoEspacio;
import com.deportido.repository.TipoEspacioRepository;


@Service
public class TipoEspacioServiceImpl implements TipoEspacioService{
	 private final TipoEspacioRepository repository;

	    public TipoEspacioServiceImpl(TipoEspacioRepository repository) {
	        this.repository = repository;
	    }

	    @Override
	    public List<TipoEspacio> listar() {
	        return repository.findAll();
	    }

	    @Override
	    public List<TipoEspacio> listarActivos() {
	        return repository.findByEstadoTrue();
	    }

	    @Override
	    public Optional<TipoEspacio> buscarPorId(Long id) {
	        return repository.findById(id);
	    }

	    @Override
	    public Optional<TipoEspacio> buscarPorNombre(String nombre) {
	        return repository.findByNombre(nombre);
	    }

	    @Override
	    public TipoEspacio guardar(TipoEspacio tipoEspacio) {
	        return repository.save(tipoEspacio);
	    }

	    @Override
	    public TipoEspacio actualizar(Long id,
	                                  TipoEspacio tipoEspacio) {

	        TipoEspacio existente = repository.findById(id)
	                .orElseThrow(() ->
	                        new RuntimeException(
	                                "Tipo de espacio no encontrado"));

	        existente.setNombre(tipoEspacio.getNombre());
	        existente.setDescripcion(tipoEspacio.getDescripcion());
	        existente.setEstado(tipoEspacio.getEstado());

	        return repository.save(existente);
	    }

	    @Override
	    public void eliminar(Long id) {
	        repository.deleteById(id);
	    }
}
