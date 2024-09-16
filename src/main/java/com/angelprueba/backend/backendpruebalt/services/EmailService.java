package com.angelprueba.backend.backendpruebalt.services;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.IOException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailWithAttachment(String toEmail, String subject, String body, ByteArrayOutputStream pdfStream) throws MessagingException, IOException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body);

        // Adjuntar el PDF
        InputStreamSource attachment = new ByteArrayResource(pdfStream.toByteArray());
        helper.addAttachment("productos.pdf", attachment);

        // Enviar el correo
        mailSender.send(message);
    }
}
