package com.deportido.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deportido.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long>{
	 Optional<Rol> findByNombre(String nombre);
	 boolean existsByNombreIgnoreCase(String nombre);
}
