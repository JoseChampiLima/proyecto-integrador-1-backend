package com.deportido.services;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deportido.model.Sede;
import com.deportido.repository.SedeRepository;


@Service
public class SedeServiceImpl implements SedeService{
	 private final SedeRepository sedeRepository;

	    public SedeServiceImpl(SedeRepository sedeRepository) {
	        this.sedeRepository = sedeRepository;
	    }

	    @Override
	    public List<Sede> listar() {
	        return sedeRepository.findAll();
	    }

	    @Override
	    public Optional<Sede> buscarPorId(Long id) {
	        return sedeRepository.findById(id);
	    }

	  

	    @Override
	    public List<Sede> listarActivas() {
	        return sedeRepository.findByEstadoTrue();
	    }

	    @Override
	    public Sede guardar(Sede sede) {
	        return sedeRepository.save(sede);
	    }

	    @Override
	    public Sede actualizar(Long id, Sede sede) {

	        Sede existente = sedeRepository.findById(id)
	                .orElseThrow(() ->
	                        new RuntimeException("Sede no encontrada"));

	        existente.setNombre(sede.getNombre());
	        existente.setDireccion(sede.getDireccion());
	        existente.setDistrito(sede.getDistrito());
	        existente.setTelefono(sede.getTelefono());
	        existente.setEstado(sede.getEstado());

	        return sedeRepository.save(existente);
	    }

	    @Override
	    public void eliminar(Long id) {
	        sedeRepository.deleteById(id);
	    }
}
