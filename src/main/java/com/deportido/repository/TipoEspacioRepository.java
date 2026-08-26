package com.deportido.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.deportido.model.TipoEspacio;

import java.util.List;
import java.util.Optional;
public interface TipoEspacioRepository extends JpaRepository<TipoEspacio, Long> {

	  Optional<TipoEspacio> findByNombre(String nombre);

	    List<TipoEspacio> findByEstadoTrue();
}
