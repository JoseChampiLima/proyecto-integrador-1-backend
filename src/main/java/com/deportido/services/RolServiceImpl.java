package com.deportido.services;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deportido.model.Rol;
import com.deportido.repository.RolRepository;


@Service
public class RolServiceImpl implements RolService{
	 private final RolRepository rolRepository;

	    public RolServiceImpl(RolRepository rolRepository) {
	        this.rolRepository = rolRepository;
	    }

	    @Override
	    public List<Rol> listar() {
	        return rolRepository.findAll();
	    }

	    @Override
	    public Optional<Rol> buscarPorId(Long id) {
	        return rolRepository.findById(id);
	    }

	    @Override
	    public Optional<Rol> buscarPorNombre(String nombre) {
	        return rolRepository.findByNombre(nombre);
	    }

	    @Override
	    public Rol guardar(Rol rol) {
	        if (rolRepository.existsByNombreIgnoreCase(rol.getNombre())) {
	            throw new RuntimeException("El rol ya se encuentra registrado");
	        }

	        return rolRepository.save(rol);
	    }

	    @Override
	    public Rol actualizar(Long id, Rol rol) {

	    	  Rol existente = rolRepository.findById(id)
	    	            .orElseThrow(() ->
	    	                    new RuntimeException("Rol no encontrado"));

	    	    rolRepository.findByNombre(rol.getNombre())
	    	            .ifPresent(otroRol -> {
	    	                if (!otroRol.getIdRol().equals(id)) {
	    	                    throw new RuntimeException(
	    	                            "Ya existe otro rol con ese nombre");
	    	                }
	    	            });

	    	    existente.setNombre(rol.getNombre());
	    	    existente.setDescripcion(rol.getDescripcion());
	    	    existente.setEstado(rol.getEstado());

	    	    return rolRepository.save(existente);
	    }

	    @Override
	    public void eliminar(Long id) {
	        rolRepository.deleteById(id);
	    }
}
