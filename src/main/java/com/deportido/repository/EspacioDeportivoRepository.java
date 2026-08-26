package com.deportido.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.deportido.model.EspacioDeportivo;

import java.util.List;
public interface EspacioDeportivoRepository extends JpaRepository<EspacioDeportivo, Long>{
	
	List<EspacioDeportivo> findBySedeIdSede(Long idSede);

    List<EspacioDeportivo> findByTipoEspacioIdTipoEspacio(
            Long idTipoEspacio
    );

    List<EspacioDeportivo> findByEstado(String estado);

    List<EspacioDeportivo> findBySedeIdSedeAndEstado(
            Long idSede,
            String estado
    );
    
}
