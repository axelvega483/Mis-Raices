package com.MisRaices.demo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendActivationEmail(String to, String activationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Activación de tu cuenta en Vivero Mis Raíces");

        StringBuilder text = new StringBuilder();
        text.append("¡Hola!\n\n");
        text.append("Gracias por registrarte en Vivero Mis Raíces, estamos muy emocionados de tenerte con nosotros.\n\n");
        text.append("Para activar tu cuenta y empezar a disfrutar de todos nuestros servicios, por favor ingresa el siguiente código en la aplicación:\n\n");
        text.append("Código de activación: ").append(activationCode).append("\n\n");
        text.append("Este código es válido por 24 horas. Si no solicitaste esta activación, por favor ignora este mensaje.\n\n");
        text.append("¡Te damos la bienvenida a la familia de Mis Raíces! Estamos seguros de que disfrutarás de nuestras ofertas y productos.\n\n");
        text.append("Saludos cordiales,\n");
        text.append("El equipo de Vivero Mis Raíces");

        message.setText(text.toString());
        emailSender.send(message);
    }

    public void sendResetPasswordEmail(String to, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Restablecimiento de Contraseña en Vivero Mis Raíces");

        StringBuilder text = new StringBuilder();
        text.append("¡Hola!\n\n");
        text.append("Recibimos una solicitud para restablecer la contraseña de tu cuenta en Vivero Mis Raíces.\n\n");
        text.append("Para continuar, utiliza el siguiente código de activación para restablecer tu contraseña:\n\n");
        text.append("Código de restablecimiento: ").append(resetToken).append("\n\n");
        text.append("Este código es válido por 1 hora. Si no solicitaste el restablecimiento de tu contraseña, por favor ignora este mensaje.\n\n");
        text.append("Si necesitas ayuda, no dudes en contactarnos. Estamos aquí para ayudarte.\n\n");
        text.append("Saludos cordiales,\n");
        text.append("El equipo de Vivero Mis Raíces");

        message.setText(text.toString());
        emailSender.send(message);
    }

    public void enviarFacturaConAdjunto(String destinatario, String rutaArchivoPDF) throws MessagingException {
        MimeMessage mensaje = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        helper.setTo(destinatario);
        helper.setSubject("Factura de Compra");
        helper.setText("Gracias por su compra. Adjuntamos la factura.");

        FileSystemResource archivo = new FileSystemResource(new File(rutaArchivoPDF));
        helper.addAttachment("factura.pdf", archivo);

        emailSender.send(mensaje);
    }
}
