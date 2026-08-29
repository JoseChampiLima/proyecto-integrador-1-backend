package com.deportido.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.deportido.model.TipoEspacio;

public interface TipoEspacioRepository extends JpaRepository<TipoEspacio, Long> {
    Optional<TipoEspacio> findByNombre(String nombre);
    List<TipoEspacio> findByEstadoTrue();
    boolean existsByNombreIgnoreCase(String nombre);
}
