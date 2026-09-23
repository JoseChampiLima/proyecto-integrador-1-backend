package com.deportido.config.SecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class SecurityConfig {

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

	    CorsConfiguration configuration = new CorsConfiguration();

	    // Permitir Angular local
	    configuration.setAllowedOrigins(
	        List.of("http://localhost:4200")
	    );

	    // Métodos permitidos
	    configuration.setAllowedMethods(
	        List.of(
	            "GET",
	            "POST",
	            "PUT",
	            "DELETE",
	            "PATCH",
	            "OPTIONS"
	        )
	    );

	    // Headers permitidos
	    configuration.setAllowedHeaders(
	        List.of("*")
	    );

	    // Permitir Authorization: Bearer TOKEN
	    configuration.setExposedHeaders(
	        List.of("Authorization")
	    );

	    configuration.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source =
	            new UrlBasedCorsConfigurationSource();

	    source.registerCorsConfiguration(
	        "/**",
	        configuration
	    );

	    return source;
	}
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter authoritiesConverter =
                new JwtGrantedAuthoritiesConverter();

        authoritiesConverter.setAuthoritiesClaimName("rol");
        authoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(
                authoritiesConverter
        );

        return converter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http
        	.cors(cors -> {})
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth -> auth

                // LOGIN PÚBLICO
                .requestMatchers(
                    "/api/auth/**"
                ).permitAll()

             // ==========================================
             // REGISTRO DE USUARIO - PÚBLICO
             // ==========================================
             .requestMatchers(
                 HttpMethod.POST,
                 "/api/usuarios"
             ).permitAll()

             
          // ==========================================
          // MOSTRAR IMAGEN - FOTO DE TIPO DE ESPACIO
          // ==========================================
             .requestMatchers(
            		    HttpMethod.GET,
            		    "/uploads/**"
            		).permitAll()
             
             // ==========================================
             // USUARIO AUTENTICADO - VER SU PERFIL
             // ==========================================
             .requestMatchers(
                 HttpMethod.GET,
                 "/api/usuarios/perfil"
             ).authenticated()


             // ==========================================
             // USUARIO AUTENTICADO - MODIFICAR SU PERFIL
             // ==========================================
             .requestMatchers(
                 HttpMethod.PUT,
                 "/api/usuarios/*/perfil"
             ).authenticated()


             // ==========================================
             // ADMINISTRACIÓN DE USUARIOS
             // ==========================================
             .requestMatchers(
                 "/api/usuarios/**"
             ).hasRole("ADMINISTRADOR")

                // PÚBLICO
                .requestMatchers(
                    "/api/espacios/**",
                    "/api/tipos-espacio/**",
                    "/api/horarios/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**"
                ).permitAll()

                // SOLO ADMIN
                .requestMatchers(
                    "/api/sedes/**",
                    "/api/mantenimientos/**",
                    "/api/roles/**"
                ).hasRole("ADMINISTRADOR")

                // CLIENTE Y ADMIN
                .requestMatchers(
                    "/api/reservas/**",
                    "/api/pagos/**"
                ).hasAnyRole(
                    "CLIENTE",
                    "ADMINISTRADOR"
                )

                .anyRequest()
                .authenticated()
            )

            .oauth2ResourceServer(oauth ->
                oauth.jwt(jwt ->
                    jwt.jwtAuthenticationConverter(
                        jwtAuthenticationConverter()
                    )
                )
            );

        return http.build();
    }
}