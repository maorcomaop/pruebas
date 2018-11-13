/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.registel.rdw.utils;

/**
 *
 * @author lider_desarrollador
 */
import com.registel.rdw.controladores.ControladorCategoriaLicencia;
import com.registel.rdw.logica.Entorno;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {

    // Cuenta de correo de origen
    private static String username = "richie8344";
    private static String password = "SendGrid-Richie8344**";
    private Properties props;
    private Session session;

    public static String getEmail() {
        return username;
    }

    public static void changeDataContact(String web_path) {
        Entorno entorno_rd = new Entorno(web_path);
        String user = entorno_rd.obtenerPropiedad(Constant.CORREO_CORPORATIVO);
        String pass = entorno_rd.obtenerPropiedad(Constant.PASSWORD_CORPORATIVO);
        if (user != null && user != ""
                && pass != null && pass != "") {
            username = user;
            password = pass;
        }
    }

    public Mail(boolean ssl) {
        props = new Properties();

        if (ssl) {
            props.put("mail.smtp.host", "smtp.sendgrid.net");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
        } else {
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.sendgrid.net");
            props.put("mail.smtp.port", "587");
        }        

        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public boolean send(String msg, String to) {
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    //InternetAddress.parse("mauro_016@msn.com"));
                    InternetAddress.parse(to));
            message.setSubject("Regisdata Web");

            message.setText(msg);

            Transport.send(message);
            return true;

        } catch (MessagingException ex) {
            //throw new RuntimeException(e);
            System.err.println(ex);
            return false;
        }
    }

    public boolean send(String msg, String to, String subject) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("soporte@registelcolombia.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Regisdata Web - Notificación " + subject);

            MimeMultipart multipart = new MimeMultipart("related");

            BodyPart messageBodyPart = new MimeBodyPart();
            //String htmlText = "<img src=\"cid:header\" style=\"max-width: 100%\"><p>" + msg + "</p><img src=\"cid:footer\" style=\"max-width: 100%\">";
            messageBodyPart.setContent(msg, "text/html");
            multipart.addBodyPart(messageBodyPart);

            String pathHeader = getPathImage(String.format("resources%1$simg%1$slogo-correos.png", File.separatorChar));
            String pathFooter = getPathImage(String.format("resources%1$simg%1$slogoregistel-correos.png", 
                    File.separatorChar));

            messageBodyPart = new MimeBodyPart();
            DataSource imagenHeader = new FileDataSource(pathHeader);
            messageBodyPart.setDataHandler(new DataHandler(imagenHeader));
            messageBodyPart.setHeader("Content-ID", "<header>");
            multipart.addBodyPart(messageBodyPart);
            
            messageBodyPart = new MimeBodyPart();
            DataSource imagenFooter = new FileDataSource(pathFooter);
            messageBodyPart.setDataHandler(new DataHandler(imagenFooter));
            messageBodyPart.setHeader("Content-ID", "<footer>");
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            return true;
        } catch (MessagingException ex) {
            System.err.println(ex);
            return false;
        }
    }

    public String getPathImage(String name) {
        String reponsePath = "";
        
        try {
            String path = this.getClass().getClassLoader().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            String pathArr[] = fullPath.split("/WEB-INF/classes/");
            System.out.println(fullPath);
            System.out.println(pathArr[0]);
            fullPath = pathArr[0];
            
            // to read a file from webcontent
            reponsePath = new File(fullPath).getPath() + File.separatorChar + name;
        } catch (UnsupportedEncodingException e) {
            Logger.getLogger(ControladorCategoriaLicencia.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return reponsePath;
    }

}
