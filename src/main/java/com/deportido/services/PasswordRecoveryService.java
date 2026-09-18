package com.deportido.services;

import java.security.SecureRandom;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.deportido.model.Usuario;
import com.deportido.repository.UsuarioRepository;
import com.deportivo.DTO.ForgotPasswordRequest;

@Service
public class PasswordRecoveryService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final SecureRandom random = new SecureRandom();

    public PasswordRecoveryService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public void recuperarPassword(ForgotPasswordRequest request) {

        // 1. Buscar usuario por correo
        Usuario usuario = usuarioRepository
                .findByCorreo(request.getCorreo())
                .orElseThrow(() ->
                    new RuntimeException(
                        "No existe un usuario registrado con ese correo"
                    )
                );

        // 2. Generar contraseña temporal
        String nuevaClave = generarClaveTemporal();

        // 3. Guardarla encriptada en la BD
        usuario.setClave(
                passwordEncoder.encode(nuevaClave)
        );

        usuarioRepository.save(usuario);

        // 4. Enviar la contraseña al correo
        emailService.enviarNuevaClave(
                usuario.getCorreo(),
                usuario.getNombres(),
                nuevaClave
        );
    }

    private String generarClaveTemporal() {

        int numero = 100000 + random.nextInt(900000);

        return "Law" + numero;
    }
}
