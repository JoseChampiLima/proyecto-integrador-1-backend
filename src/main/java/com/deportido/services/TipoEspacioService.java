package com.deportido.services;
import java.util.List;
import java.util.Optional;

import com.deportido.model.TipoEspacio;
public interface TipoEspacioService {
	 List<TipoEspacio> listar();

	    List<TipoEspacio> listarActivos();

	    Optional<TipoEspacio> buscarPorId(Long id);

	    Optional<TipoEspacio> buscarPorNombre(String nombre);

	    TipoEspacio guardar(TipoEspacio tipoEspacio);

	    TipoEspacio actualizar(Long id, TipoEspacio tipoEspacio);

	    void eliminar(Long id);
}
