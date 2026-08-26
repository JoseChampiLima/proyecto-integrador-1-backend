package com.deportido.services;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deportido.model.Mantenimiento;
import com.deportido.repository.MantenimientoRepository;

@Service
public class MantenimientoServiceImpl implements MantenimientoService{
	 private final MantenimientoRepository repository;

	    public MantenimientoServiceImpl(
	            MantenimientoRepository repository) {

	        this.repository = repository;
	    }

	    @Override
	    public List<Mantenimiento> listar() {
	        return repository.findAll();
	    }

	    @Override
	    public Optional<Mantenimiento> buscarPorId(Long id) {
	        return repository.findById(id);
	    }

	    @Override
	    public List<Mantenimiento> listarPorEspacio(
	            Long idEspacio) {

	        return repository.findByEspacioIdEspacio(idEspacio);
	    }

	    @Override
	    public boolean existeMantenimiento(
	            Long idEspacio,
	            LocalDateTime inicio,
	            LocalDateTime fin) {

	        return repository.contarMantenimientosActivos(
	                idEspacio,
	                inicio,
	                fin) > 0;
	    }

	    @Override
	    public Mantenimiento guardar(
	            Mantenimiento mantenimiento) {

	        return repository.save(mantenimiento);
	    }

	    @Override
	    public Mantenimiento actualizar(
	            Long id,
	            Mantenimiento mantenimiento) {

	        Mantenimiento existente = repository.findById(id)
	                .orElseThrow(() ->
	                        new RuntimeException(
	                                "Mantenimiento no encontrado"));

	        existente.setFechaInicio(
	                mantenimiento.getFechaInicio());
	        existente.setFechaFin(
	                mantenimiento.getFechaFin());
	        existente.setMotivo(
	                mantenimiento.getMotivo());
	        existente.setEstado(
	                mantenimiento.getEstado());
	        existente.setEspacio(
	                mantenimiento.getEspacio());

	        return repository.save(existente);
	    }

	    @Override
	    public void eliminar(Long id) {
	        repository.deleteById(id);
	    }
}
