	package com.deportido.services;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deportido.model.EspacioDeportivo;
import com.deportido.repository.EspacioDeportivoRepository;


@Service
public class EspacioDeportivoServiceImpl implements EspacioDeportivoService{
	  private final EspacioDeportivoRepository repository;

	    public EspacioDeportivoServiceImpl(
	            EspacioDeportivoRepository repository) {

	        this.repository = repository;
	    }

	    @Override
	    public List<EspacioDeportivo> listar() {
	        return repository.findAll();
	    }

	    @Override
	    public Optional<EspacioDeportivo> buscarPorId(Long id) {
	        return repository.findById(id);
	    }

	    @Override
	    public List<EspacioDeportivo> listarPorSede(Long idSede) {
	        return repository.findBySedeIdSede(idSede);
	    }

	    @Override
	    public List<EspacioDeportivo> listarPorTipo(
	            Long idTipoEspacio) {

	        return repository
	                .findByTipoEspacioIdTipoEspacio(idTipoEspacio);
	    }

	    @Override
	    public List<EspacioDeportivo> listarPorEstado(
	            String estado) {

	        return repository.findByEstado(estado);
	    }

	    @Override
	    public List<EspacioDeportivo> listarDisponiblesPorSede(
	            Long idSede) {

	        return repository
	                .findBySedeIdSedeAndEstado(
	                        idSede,
	                        "DISPONIBLE");
	    }

	    @Override
	    public EspacioDeportivo guardar(
	            EspacioDeportivo espacio) {

	        return repository.save(espacio);
	    }

	    @Override
	    public EspacioDeportivo actualizar(
	            Long id,
	            EspacioDeportivo espacio) {

	        EspacioDeportivo existente = repository.findById(id)
	                .orElseThrow(() ->
	                        new RuntimeException(
	                                "Espacio deportivo no encontrado"));

	        existente.setNombre(espacio.getNombre());
	        existente.setDescripcion(espacio.getDescripcion());
	        existente.setCapacidad(espacio.getCapacidad());
	        existente.setPrecioHora(espacio.getPrecioHora());
	        existente.setEstado(espacio.getEstado());
	        existente.setSede(espacio.getSede());
	        existente.setTipoEspacio(espacio.getTipoEspacio());
	        existente.setFoto(espacio.getFoto());
	        return repository.save(existente);
	    }

	    @Override
	    public void eliminar(Long id) {
	        repository.deleteById(id);
	    }
}
