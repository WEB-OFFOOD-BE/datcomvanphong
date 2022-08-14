package com.example.websitedatmon.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


public class MailUtil {

    @Autowired
    JavaMailSender sender;

    public static void sendMail(JavaMailSenderImpl emailSender,String email,String title,String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(title);
        message.setText(text);
        emailSender.send(message);
    }

    public static void sendHtmlMail(JavaMailSenderImpl emailSender,String email,String title,String html)throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        String htmlMsg = html;

        helper.setText(htmlMsg, true);

        helper.setTo(email);

        helper.setSubject(title);


        emailSender.send(message);

    }

//    public void send(String email,String title,String html) throws MessagingException, IOException {
//        // Tạo message
//        MimeMessage message = sender.createMimeMessage();
//        // Sử dụng Helper để thiết lập các thông tin cần thiết cho message
//        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
//        helper.setFrom(title);
//        helper.setTo(email);
//        helper.setSubject(mail.getSubject());
//        helper.setText(html, true);
//        helper.setReplyTo(mail.getFrom());
//
//        // Gửi message đến SMTP server
//        sender.send(message);
//
//    }
}
