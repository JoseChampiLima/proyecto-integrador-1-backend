package com.deportido.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.deportido.model.Sede;

import java.util.List;
public interface SedeRepository extends JpaRepository<Sede, Long>{


    List<Sede> findByEstadoTrue();
    

}
