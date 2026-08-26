package com.deportido.services;
import java.util.List;
import java.util.Optional;

import com.deportido.model.EspacioDeportivo;
public interface EspacioDeportivoService {
	List<EspacioDeportivo> listar();

    Optional<EspacioDeportivo> buscarPorId(Long id);

    List<EspacioDeportivo> listarPorSede(Long idSede);

    List<EspacioDeportivo> listarPorTipo(Long idTipoEspacio);

    List<EspacioDeportivo> listarPorEstado(String estado);

    List<EspacioDeportivo> listarDisponiblesPorSede(Long idSede);

    EspacioDeportivo guardar(EspacioDeportivo espacio);

    EspacioDeportivo actualizar(Long id,
                                EspacioDeportivo espacio);

    void eliminar(Long id);
}
