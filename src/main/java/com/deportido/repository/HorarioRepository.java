package com.deportido.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.deportido.model.Horario;

import java.util.List;
public interface HorarioRepository extends JpaRepository<Horario, Long>{
	  List<Horario> findByEspacioIdEspacio(Long idEspacio);

	    List<Horario> findByEspacioIdEspacioAndDiaSemana(
	            Long idEspacio,
	            String diaSemana
	    );

	    List<Horario> findByEstadoTrue();
}
