	package com.deportido.services;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deportido.model.EspacioDeportivo;
import com.deportido.repository.EspacioDeportivoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
	                        "ACTIVO");
	    }

	    @Override
	    public EspacioDeportivo guardar(EspacioDeportivo espacio) {

	        validarDatos(espacio);

	        Long idSede = espacio.getSede().getIdSede();
	        String nombre = espacio.getNombre().trim();

	        boolean existe = repository
	                .existsBySedeIdSedeAndNombreIgnoreCase(
	                        idSede,
	                        nombre
	                );

	        if (existe) {
	            throw new ResponseStatusException(
	                    HttpStatus.CONFLICT,
	                    "Ya existe un espacio deportivo con ese nombre en la sede."
	            );
	        }

	        espacio.setIdEspacio(null);
	        espacio.setNombre(nombre);

	        return repository.save(espacio);
	    }

	    @Override
	    public EspacioDeportivo actualizar(
	            Long id,
	            EspacioDeportivo espacio) {

	        EspacioDeportivo existente = repository.findById(id)
	                .orElseThrow(() -> new ResponseStatusException(
	                        HttpStatus.NOT_FOUND,
	                        "Espacio deportivo no encontrado"
	                ));

	        validarDatos(espacio);

	        Long idSede = espacio.getSede().getIdSede();
	        String nombre = espacio.getNombre().trim();

	        boolean duplicado = repository
	                .existsBySedeIdSedeAndNombreIgnoreCaseAndIdEspacioNot(
	                        idSede,
	                        nombre,
	                        id
	                );

	        if (duplicado) {
	            throw new ResponseStatusException(
	                    HttpStatus.CONFLICT,
	                    "Ya existe otro espacio deportivo con ese nombre en la sede."
	            );
	        }

	        existente.setNombre(nombre);
	        existente.setDescripcion(espacio.getDescripcion());
	        existente.setCapacidad(espacio.getCapacidad());
	        existente.setPrecioHora(espacio.getPrecioHora());
	        existente.setEstado(espacio.getEstado());
	        existente.setSede(espacio.getSede());
	        existente.setTipoEspacio(espacio.getTipoEspacio());

	        return repository.save(existente);
	    }


	    @Override
	    public void eliminar(Long id) {
	        repository.deleteById(id);
	    }
	    private void validarDatos(EspacioDeportivo espacio) {

	        if (espacio == null
	                || espacio.getNombre() == null
	                || espacio.getNombre().trim().isEmpty()) {

	            throw new ResponseStatusException(
	                    HttpStatus.BAD_REQUEST,
	                    "El nombre del espacio deportivo es obligatorio."
	            );
	        }

	        if (espacio.getSede() == null
	                || espacio.getSede().getIdSede() == null) {

	            throw new ResponseStatusException(
	                    HttpStatus.BAD_REQUEST,
	                    "Debe seleccionar una sede."
	            );
	        }
	    }
}
