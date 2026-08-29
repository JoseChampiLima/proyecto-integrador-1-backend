package com.deportido.services;
import java.util.List;
import java.util.Optional;

import com.deportido.model.Rol;
public interface RolService {
	 List<Rol> listar();

	    Optional<Rol> buscarPorId(Long id);

	    Optional<Rol> buscarPorNombre(String nombre);

	    Rol guardar(Rol rol);

	    Rol actualizar(Long id, Rol rol);

	    void eliminar(Long id);
}
