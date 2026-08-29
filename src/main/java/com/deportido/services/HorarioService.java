package com.deportido.services;
import java.util.List;
import java.util.Optional;

import com.deportido.model.Horario;

public interface HorarioService {
	  List<Horario> listar();

	    Optional<Horario> buscarPorId(Long id);

	    List<Horario> listarPorEspacio(Long idEspacio);

	    List<Horario> listarPorEspacioYDia(
	            Long idEspacio,
	            String diaSemana);

	    Horario guardar(Horario horario);

	    Horario actualizar(Long id, Horario horario);

	    void eliminar(Long id);
}
