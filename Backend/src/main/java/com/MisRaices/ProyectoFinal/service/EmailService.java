package com.MisRaices.ProyectoFinal.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;




@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Value("${app.support.email:soporte@misraices.com}")
    private String supportEmail;

    @Async
    public void sendActivationEmail(String to, String activationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("🎉 Activación de Cuenta - Vivero Mis Raíces");

        StringBuilder text = new StringBuilder();
        text.append("¡BIENVENIDO A VIVERO MIS RAÍCES!\n");
        text.append("=========================================\n\n");

        text.append("Estimado usuario,\n\n");
        text.append("Nos complace darle la bienvenida a nuestra familia. Su registro ha sido exitoso y estamos encantados de tenerle con nosotros.\n\n");

        text.append("PARA ACTIVAR SU CUENTA:\n");
        text.append("-----------------------\n");
        text.append("Código de verificación: ").append(activationCode).append("\n\n");

        text.append("Ingrese este código en la aplicación para completar la activación de su cuenta.\n\n");

        text.append("ℹ️  INFORMACIÓN IMPORTANTE:\n");
        text.append("• Este código es válido por 24 horas\n");
        text.append("• Si no realizó esta solicitud, ignore este mensaje\n");
        text.append("• Para asistencia, contacte a: ").append(supportEmail).append("\n\n");

        text.append("¡Gracias por elegirnos! Esperamos que disfrute de nuestra amplia variedad de plantas y productos para jardinería.\n\n");
        text.append("Atentamente,\n");
        text.append("El equipo de Vivero Mis Raíces\n");
        text.append("----------------------------------------");

        message.setText(text.toString());
        emailSender.send(message);
    }

    @Async
    public void sendResetPasswordEmail(String to, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("🔒 Restablecimiento de Contraseña - Vivero Mis Raíces");

        StringBuilder text = new StringBuilder();
        text.append("SOLICITUD DE RESTABLECIMIENTO DE CONTRASEÑA\n");
        text.append("===========================================\n\n");

        text.append("Hemos recibido una solicitud para restablecer la contraseña de su cuenta.\n\n");

        text.append("CÓDIGO DE VERIFICACIÓN:\n");
        text.append("-----------------------\n");
        text.append(resetToken).append("\n\n");

        text.append("Utilice este código en la aplicación para crear una nueva contraseña.\n\n");

        text.append("⚡ ACCIÓN REQUERIDA:\n");
        text.append("• Ingrese el código en los próximos 60 minutos\n");
        text.append("• Cree una contraseña segura\n");
        text.append("• Si no solicitó este cambio, ignore este mensaje\n\n");

        text.append("🔒 RECOMENDACIONES DE SEGURIDAD:\n");
        text.append("• No comparta este código con nadie\n");
        text.append("• Utilice una contraseña única\n");
        text.append("• Contacte a ").append(supportEmail).append(" si detecta actividad sospechosa\n\n");

        text.append("Atentamente,\n");
        text.append("Equipo de Seguridad - Vivero Mis Raíces\n");
        text.append("----------------------------------------");

        message.setText(text.toString());
        emailSender.send(message);
    }

    @Async
    public void enviarFacturaConAdjunto(String destinatario, String rutaArchivoPDF, String numeroPedido) throws MessagingException {
        MimeMessage mensaje = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

        helper.setTo(destinatario);
        helper.setSubject("🧾 Factura de Compra #" + numeroPedido + " - Vivero Mis Raíces");

        String texto = "CONFIRMACIÓN DE COMPRA\n" +
                "=====================\n\n" +
                "¡Gracias por su compra en Vivero Mis Raíces!\n\n" +
                "Número de pedido: #" + numeroPedido + "\n" +
                "Fecha: " + java.time.LocalDate.now() + "\n\n" +
                "Adjuntamos la factura correspondiente a su compra para sus registros.\n\n" +
                "📦 INFORMACIÓN DEL PEDIDO:\n" +
                "• Recibirá una notificación cuando su pedido sea enviado\n" +
                "• Tiempo de entrega estimado: 3-5 días hábiles\n" +
                "• Para consultas: " + supportEmail + "\n\n" +
                "¡Agradecemos su preferencia!\n\n" +
                "Atentamente,\n" +
                "Departamento de Ventas - Vivero Mis Raíces\n" +
                "----------------------------------------";

        helper.setText(texto);

        FileSystemResource archivo = new FileSystemResource(new File(rutaArchivoPDF));
        helper.addAttachment("Factura_" + numeroPedido + ".pdf", archivo);

        emailSender.send(mensaje);
    }

    @Async
    public void sendOrderConfirmation(String to, String orderNumber) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("✅ Confirmación de Pedido #" + orderNumber + " - Vivero Mis Raíces");

        StringBuilder text = new StringBuilder();
        text.append("CONFIRMACIÓN DE PEDIDO\n");
        text.append("======================\n\n");

        text.append("Su pedido ha sido confirmado exitosamente.\n\n");

        text.append("📋 DETALLES DEL PEDIDO:\n");
        text.append("Número: #").append(orderNumber).append("\n");
        text.append("Fecha: ").append(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n\n");

        text.append("🚚 PRÓXIMOS PASOS:\n");
        text.append("• Recibirá un email cuando su pedido sea enviado\n");
        text.append("• Tiempo de entrega: 3-5 días hábiles\n");
        text.append("• Para seguimiento: ").append(supportEmail).append("\n\n");

        text.append("¡Gracias por confiar en Vivero Mis Raíces!\n\n");
        text.append("Atentamente,\n");
        text.append("Equipo de Logística - Vivero Mis Raíces\n");
        text.append("----------------------------------------");

        message.setText(text.toString());
        emailSender.send(message);
    }
}
