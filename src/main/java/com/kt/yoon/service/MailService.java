package com.kt.yoon.service;

import com.kt.yoon.domain.Mail;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.domain.form.MailForm;
import com.kt.yoon.domain.type.MailType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    final String username = "gathering-bot@kt.com";
    final String password = "pw_82216910_!";
    ////    final String SERVER_URL = "http://localhost:8080"; //local
    final String SERVER_URL = "http://10.225.164.166:8080"; // develop
//    final String SERVER_URL = "http://10.225.168.104:8080"; // production

    public Session mailInit() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "14.63.245.51");
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        return session;
    }

    public void sendMail(Sheet sheet, List<MailForm> mailFormList, MailType mailType, String modifiedMessage) {
        try {
            Mail sendMail = Mail.createMail(sheet, mailType);
            Session session = mailInit();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setSubject(MimeUtility.encodeText(sendMail.getMailTitle(), "utf-8", "B"));

            if(mailType==MailType.MODIFIED){
                sendMail.setModifiedMessage(modifiedMessage);
            }

            for (MailForm mailForm : mailFormList) {
                sendMail.setToken(mailForm.getToken());
                sendMail.setUserEmail(mailForm.getEmail());
                sendMail.setUserName(mailForm.getUserName());
                sendMail.setResponseId(mailForm.getResponseId());
                message.setContent(sendMail.getMailContent(), "text/html;charset=\"UTF-8\"");
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailForm.getEmail()));

                Transport.send(message);
                log.info("send mail:" + mailForm.getEmail());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
