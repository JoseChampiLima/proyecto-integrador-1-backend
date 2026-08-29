package com.deportido.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.deportido.exception.BadRequestException;
import com.deportido.exception.ConflictException;
import com.deportido.exception.NotFoundException;
import com.deportido.model.Rol;
import com.deportido.model.Usuario;
import com.deportido.repository.RolRepository;
import com.deportido.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    public List<Usuario> listar() { return usuarioRepository.findAll(); }
    public Optional<Usuario> buscarPorId(Long id) { return usuarioRepository.findById(id); }
    public Optional<Usuario> buscarPorCorreo(String correo) { return usuarioRepository.findByCorreo(correo); }
    public Optional<Usuario> buscarPorDni(String dni) { return usuarioRepository.findByDni(dni); }

    private Rol resolverRol(Usuario usuario) {
        if (usuario.getRol() == null || usuario.getRol().getIdRol() == null) {
            throw new BadRequestException("Debe indicar el rol del usuario");
        }
        return rolRepository.findById(usuario.getRol().getIdRol())
                .orElseThrow(() -> new NotFoundException("Rol no encontrado"));
    }

    public Usuario guardar(Usuario usuario) {
        if (usuarioRepository.existsByCorreoIgnoreCase(usuario.getCorreo())) {
            throw new ConflictException("El correo ya está registrado");
        }
        if (usuario.getDni() != null && usuarioRepository.existsByDni(usuario.getDni())) {
            throw new ConflictException("El DNI ya está registrado");
        }
        usuario.setRol(resolverRol(usuario));
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario usuario) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        usuarioRepository.findByCorreo(usuario.getCorreo()).ifPresent(otro -> {
            if (!otro.getIdUsuario().equals(id)) {
                throw new ConflictException("El correo ya está registrado por otro usuario");
            }
        });

        if (usuario.getDni() != null) {
            usuarioRepository.findByDni(usuario.getDni()).ifPresent(otro -> {
                if (!otro.getIdUsuario().equals(id)) {
                    throw new ConflictException("El DNI ya está registrado por otro usuario");
                }
            });
        }

        existente.setNombres(usuario.getNombres());
        existente.setApellidos(usuario.getApellidos());
        existente.setDni(usuario.getDni());
        existente.setTelefono(usuario.getTelefono());
        existente.setCorreo(usuario.getCorreo());
        existente.setEstado(usuario.getEstado());
        existente.setRol(resolverRol(usuario));

        if (usuario.getClave() != null && !usuario.getClave().isBlank()) {
            existente.setClave(usuario.getClave());
        }

        return usuarioRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new NotFoundException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
