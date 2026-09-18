package com.deportido.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deportido.services.AuthService;
import com.deportido.services.PasswordRecoveryService;
import com.deportivo.DTO.ForgotPasswordRequest;
import com.deportivo.DTO.LoginRequest;
import com.deportivo.DTO.LoginResponse;
import java.util.Map;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final PasswordRecoveryService passwordRecoveryService;
    
    public AuthController(
            AuthService authService,
            PasswordRecoveryService passwordRecoveryService) {

        this.authService = authService;
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<?> recuperarPassword(
            @RequestBody ForgotPasswordRequest request) {

        passwordRecoveryService.recuperarPassword(request);

        return ResponseEntity.ok(
            Map.of(
                "mensaje",
                "Se envió una nueva contraseña al correo registrado"
            )
        );
    }
}
