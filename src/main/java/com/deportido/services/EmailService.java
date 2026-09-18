package com.deportido.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remitente;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarNuevaClave(
            String destinatario,
            String nombre,
            String nuevaClave) {

        SimpleMailMessage mensaje = new SimpleMailMessage();

        mensaje.setFrom(remitente);
        mensaje.setTo(destinatario);

        mensaje.setSubject(
                "Recuperación de contraseña - Club Law Tennis"
        );

        mensaje.setText(
                "Hola " + nombre + ",\n\n" +
                "Se solicitó la recuperación de tu contraseña.\n\n" +
                "Tu nueva contraseña temporal es:\n\n" +
                nuevaClave +
                "\n\n" +
                "Utiliza esta contraseña para iniciar sesión. " +
                "Por seguridad, te recomendamos cambiarla después de ingresar.\n\n" +
                "Si no realizaste esta solicitud, comunícate con el administrador.\n\n" +
                "Club Law Tennis"
        );

        mailSender.send(mensaje);
    }
}
