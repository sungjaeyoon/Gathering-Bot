package com.kt.yoon.service;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {

    final String username = "gathering-bot@outlook.kr";
    final String password = "PW_1234!";

    public String sendMail(){

        /*
        * mail send setting & Auth
        * */
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        /*
        * send mail
        * */
        try {

            String[] sendUserEmails ={"dbstjdwo1000@naver.com","forhigh@kt.com"};
            String mailTitle="[취합]-주간 보고 회신 부탁드립니다.";
            String htmlText = "<H1>Hello</H1> " +
                    "<form action=\"/lol\" method=\"post\">\n" +
                    "    <button type=\"submit\">제출</button>\n" +
                    "</form>";

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setSubject(mailTitle);
            message.setContent(htmlText, "text/html;charset=\"UTF-8\"");

            for(String sendUserEmail:sendUserEmails ) {
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse( sendUserEmail));
                Transport.send(message);
            }

            System.out.println("success");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return "main";
    }
}
