package com.deportido.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;


import com.deportido.model.Usuario;
import com.deportido.repository.UsuarioRepository;
import com.deportivo.DTO.LoginRequest;
import com.deportivo.DTO.LoginResponse;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final UsuarioRepository usuarioRepository;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtEncoder jwtEncoder,
            UsuarioRepository usuarioRepository) {

        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    public LoginResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        request.getCorreo(),
                        request.getClave()
                    )
                );

        Usuario usuario = usuarioRepository
                .findByCorreo(request.getCorreo())
                .orElseThrow(() ->
                    new RuntimeException("Usuario no encontrado"));

        Instant ahora = Instant.now();

        Instant expiracion =
                ahora.plus(1, ChronoUnit.HOURS);

        String rol = usuario.getRol().getNombre();

        JwtClaimsSet claims =
                JwtClaimsSet.builder()
                    .issuer("deportivo-api")
                    .issuedAt(ahora)
                    .expiresAt(expiracion)
                    .subject(usuario.getCorreo())
                    .claim("rol", rol)
                    .build();

        JwsHeader jwsHeader = JwsHeader
                .with(MacAlgorithm.HS256)
                .build();

        String token = jwtEncoder
                .encode(
                    JwtEncoderParameters.from(
                        jwsHeader,
                        claims
                    )
                )
                .getTokenValue();

        return new LoginResponse(
                token,
                "Bearer",
                3600,
                rol
        );
    }
}
