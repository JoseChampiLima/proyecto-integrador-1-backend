package com.deportido.services;
import java.util.List; 
import java.util.Optional;

import com.deportido.model.Usuario;
import com.deportivo.DTO.ActualizarPerfilRequest;

public interface UsuarioService {
	 List<Usuario> listar();

	    Optional<Usuario> buscarPorId(Long id);

	    Optional<Usuario> buscarPorCorreo(String correo);

	    Optional<Usuario> buscarPorDni(String dni);

	    Usuario guardar(Usuario usuario);

	    Usuario actualizar(Long id, Usuario usuario);

	    void eliminar(Long id);
	    
	    Usuario actualizarPerfil(Long idUsuario,ActualizarPerfilRequest request);
}
