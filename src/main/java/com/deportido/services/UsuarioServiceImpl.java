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
import com.deportivo.DTO.ActualizarPerfilRequest;

import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class UsuarioServiceImpl implements UsuarioService {
	private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository,PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
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
        usuario.setClave(
        	    passwordEncoder.encode(usuario.getClave())
        	);
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
    
    public Usuario actualizarPerfil(
            Long idUsuario,
            ActualizarPerfilRequest request) {

        // ==========================================
        // BUSCAR USUARIO
        // ==========================================
        Usuario usuario = usuarioRepository
                .findById(idUsuario)
                .orElseThrow(() ->
                    new RuntimeException("Usuario no encontrado")
                );


        // ==========================================
        // VALIDAR CONTRASEÑA ACTUAL
        // ==========================================
        if (request.getClaveActual() == null ||
            request.getClaveActual().trim().isEmpty()) {

            throw new RuntimeException(
                "Debe ingresar su contraseña actual"
            );
        }

        // IMPORTANTE:
        // BCrypt compara la contraseña escrita
        // contra el hash guardado en usuario.clave
        if (!passwordEncoder.matches(
                request.getClaveActual(),
                usuario.getClave())) {

            throw new RuntimeException(
                "La contraseña actual es incorrecta"
            );
        }


        // ==========================================
        // VALIDAR NOMBRES
        // ==========================================
        if (request.getNombres() == null ||
            request.getNombres().trim().isEmpty()) {

            throw new RuntimeException(
                "El nombre es obligatorio"
            );
        }


        // ==========================================
        // VALIDAR APELLIDOS
        // ==========================================
        if (request.getApellidos() == null ||
            request.getApellidos().trim().isEmpty()) {

            throw new RuntimeException(
                "Los apellidos son obligatorios"
            );
        }


        // ==========================================
        // VALIDAR TELÉFONO
        // ==========================================
        if (request.getTelefono() == null ||
            request.getTelefono().trim().isEmpty()) {

            throw new RuntimeException(
                "El teléfono es obligatorio"
            );
        }


        String nombres =
                request.getNombres().trim();

        String apellidos =
                request.getApellidos().trim();

        String telefono =
                request.getTelefono().trim();


        // ==========================================
        // VALIDAR FORMATO TELÉFONO
        // ==========================================
        if (!telefono.matches("\\d{9}")) {

            throw new RuntimeException(
                "El teléfono debe contener 9 dígitos"
            );
        }


        // ==========================================
        // VALIDAR TELÉFONO DUPLICADO
        // ==========================================
        boolean telefonoExiste =
                usuarioRepository
                        .existsByTelefonoAndIdUsuarioNot(
                                telefono,
                                idUsuario
                        );

        if (telefonoExiste) {

            throw new RuntimeException(
                "El teléfono ya está registrado por otro usuario"
            );
        }


        // ==========================================
        // DETECTAR CAMBIOS
        // ==========================================
        boolean cambioNombres =
                !nombres.equals(usuario.getNombres());

        boolean cambioApellidos =
                !apellidos.equals(usuario.getApellidos());

        boolean cambioTelefono =
                !telefono.equals(usuario.getTelefono());

        boolean cambioClave =
                request.getNuevaClave() != null &&
                !request.getNuevaClave().trim().isEmpty();


        // ==========================================
        // NO HAY CAMBIOS
        // ==========================================
        if (!cambioNombres &&
            !cambioApellidos &&
            !cambioTelefono &&
            !cambioClave) {

            throw new RuntimeException(
                "No se realizaron cambios en el perfil"
            );
        }


        // ==========================================
        // VALIDAR NUEVA CONTRASEÑA
        // ==========================================
        if (cambioClave) {

            String nuevaClave =
                    request.getNuevaClave().trim();

            if (nuevaClave.length() < 6) {

                throw new RuntimeException(
                    "La nueva contraseña debe tener al menos 6 caracteres"
                );
            }


            // No permitir la misma contraseña actual
            if (passwordEncoder.matches(
                    nuevaClave,
                    usuario.getClave())) {

                throw new RuntimeException(
                    "La nueva contraseña debe ser diferente a la contraseña actual"
                );
            }
        }


        // ==========================================
        // ACTUALIZAR PERFIL
        // ==========================================
        usuario.setNombres(nombres);
        usuario.setApellidos(apellidos);
        usuario.setTelefono(telefono);


        // ==========================================
        // ACTUALIZAR CONTRASEÑA SI CORRESPONDE
        // ==========================================
        if (cambioClave) {

            usuario.setClave(
                passwordEncoder.encode(
                    request.getNuevaClave().trim()
                )
            );
        }


        // ==========================================
        // GUARDAR
        // ==========================================
        return usuarioRepository.save(usuario);
    }
    
}
