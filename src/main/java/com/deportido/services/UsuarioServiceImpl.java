package com.deportido.services;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deportido.model.Usuario;
import com.deportido.repository.UsuarioRepository;


@Service
public class UsuarioServiceImpl implements UsuarioService{
	private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    @Override
    public Optional<Usuario> buscarPorDni(String dni) {
        return usuarioRepository.findByDni(dni);
    }

    @Override
    public Usuario guardar(Usuario usuario) {

        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        if (usuario.getDni() != null &&
            usuarioRepository.existsByDni(usuario.getDni())) {

            throw new RuntimeException("El DNI ya está registrado");
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizar(Long id, Usuario usuario) {

        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        existente.setNombres(usuario.getNombres());
        existente.setApellidos(usuario.getApellidos());
        existente.setDni(usuario.getDni());
        existente.setTelefono(usuario.getTelefono());
        existente.setCorreo(usuario.getCorreo());
        existente.setEstado(usuario.getEstado());
        existente.setRol(usuario.getRol());

        return usuarioRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

}
