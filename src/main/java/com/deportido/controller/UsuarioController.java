package com.deportido.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.deportido.model.Usuario;
import com.deportido.services.UsuarioService;
import com.deportivo.DTO.ActualizarPerfilRequest;
import java.util.Map;
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
	private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {

        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> buscarPorCorreo(
            @PathVariable String correo) {

        return usuarioService.buscarPorCorreo(correo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<Usuario> buscarPorDni(
            @PathVariable String dni) {

        return usuarioService.buscarPorDni(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> guardar(
            @RequestBody Usuario usuario) {

        return ResponseEntity.ok(
                usuarioService.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(
            @PathVariable Long id,
            @RequestBody Usuario usuario) {

        return ResponseEntity.ok(
                usuarioService.actualizar(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/perfil")
    public ResponseEntity<?> actualizarPerfil(
            @PathVariable("id") Long idUsuario,
            @RequestBody ActualizarPerfilRequest request) {

        try {

            Usuario usuario =
                    usuarioService.actualizarPerfil(
                        idUsuario,
                        request
                    );

            return ResponseEntity.ok(
                Map.of(
                    "mensaje", "Perfil actualizado correctamente",
                    "idUsuario", usuario.getIdUsuario(),
                    "nombres", usuario.getNombres(),
                    "apellidos", usuario.getApellidos(),
                    "correo", usuario.getCorreo(),
                    "telefono", usuario.getTelefono()
                )
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                        Map.of(
                            "mensaje",
                            e.getMessage()
                        )
                    );
        }
    }
    
    @GetMapping("/perfil")
    public ResponseEntity<?> obtenerMiPerfil(
            @AuthenticationPrincipal Jwt jwt) {

        String correo = jwt.getSubject();
        System.out.println(correo);
        return usuarioService.buscarPorCorreo(correo)
                .map(usuario ->
                    ResponseEntity.ok(
                        Map.of(
                            "idUsuario", usuario.getIdUsuario(),
                            "nombres", usuario.getNombres(),
                            "apellidos", usuario.getApellidos(),
                            "correo", usuario.getCorreo(),
                            "dni", usuario.getDni(),
                            "telefono", usuario.getTelefono()
                        )
                    )
                )
                .orElse(ResponseEntity.notFound().build());
    }
}
