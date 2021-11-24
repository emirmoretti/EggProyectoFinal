package com.springboot.lavitree.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServicio {

    @Autowired
    private JavaMailSender mailSender;
    
    @Async
    public void envioMail(String toEmail, String subjet, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lavitreegg@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subjet);

        mailSender.send(message);
        System.out.println("MAIL ENVIADO CON EXITO");
    }
    @Async
    public void contacto(String toEmail, String subjet, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lavitreegg@gmail.com");
        message.setTo("lavitreegg@gmail.com");
        message.setText(body);
        message.setSubject("UN USUARIO SE QUIERE CONTACTAR CON NOSOTROS");

        mailSender.send(message);
        System.out.println("MAIL ENVIADO CON EXITO AL EQUIPO");
    }
    @Async
    public void contactoConVendedor(String toEmail, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lavitreegg@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject("¡UN USUARIO SE QUIERE CONTACTAR CON USTED POR SU OBRA! ");

        mailSender.send(message);
        System.out.println("MAIL ENVIADO CON EXITO AL VENDEDOR");
    }
}
