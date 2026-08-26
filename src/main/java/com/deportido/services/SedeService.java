package com.deportido.services;
import java.util.List;
import java.util.Optional;

import com.deportido.model.Sede;
public interface SedeService {
	 List<Sede> listar();

	    Optional<Sede> buscarPorId(Long id);

	 

	    List<Sede> listarActivas();

	    Sede guardar(Sede sede);

	    Sede actualizar(Long id, Sede sede);

	    void eliminar(Long id);
}
