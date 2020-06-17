package com.kt.yoon.service;

import com.kt.yoon.domain.Member;
import com.kt.yoon.domain.Sheet;
import com.kt.yoon.repository.SheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailService {

    private final SheetRepository sheetRepository;

    final String username = "gathering-bot@outlook.kr";
    final String password = "PW_1234!";

    public void sendMail(List<Member> memberList, Sheet sheet) {
        /*
         * mail send setting & Auth
         * */
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp-mail.outlook.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.starttls.enable", "true");
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

            String mailTitle = "[Gathering-bot]" + sheet.getTitle();
            String htmlText = "<h2>Gathering-bot에서 발송한 메일입니다.</h2><br>" +
                    sheet.getContent().replaceAll("\n","<br>")+"\n"+
                    "<br> <h3>- 완료 기한: "+String.join(" / ",sheet.getFinishedDate().toString().split("T"))+"</h3>"+
                    "<br><br> 아래 링크를 눌러서 응답해주세요.<br>"+
                    "<a href=\"http://localhost:8081/response/"+sheet.getId()+"/";

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setSubject(mailTitle);
            for (Member member: memberList) {
                String personalHtmlText = htmlText;
                personalHtmlText+=member.getId()+"\"> 답변하기</a><br>";
                personalHtmlText+=member.getName()+"님에게 발송된 메일입니다.";
                message.setContent(personalHtmlText, "text/html;charset=\"UTF-8\"");
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse("dbstjdwo1000@naver.com"));
                Transport.send(message);
            }
            System.out.println("success");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {

        }
    }

//    public String sendMail(List<Member> memberList, Sheet sheet) {
//        /*
//         * mail send setting & Auth
//         * */
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp-mail.outlook.com");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.auth", "true");
//
//        Session session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//        /*
//         * send mail
//         * */
//        try {
//
//            String[] sendUserEmails = {"dbstjdwo1000@naver.com", "sungjae.yoon@kt.com"};
//            String mailTitle = "[취합]-주간 보고 회신 부탁드립니다.";
//            String htmlText = "<H1>Hello</H1> " +
//                    "<form action=\"http://192.168.43.182:8080/response/1/1\" method=\"get\">\n" +
//                    "    <button type=\"submit\">제출</button>\n" +
//                    "</form>";
//
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(username));
//            message.setSubject(mailTitle);
//            message.setContent(htmlText, "text/html;charset=\"UTF-8\"");
//
//            for (String sendUserEmail : sendUserEmails) {
//                message.setRecipients(Message.RecipientType.TO,
//                        InternetAddress.parse(sendUserEmail));
//                Transport.send(message);
//            }
//
//            System.out.println("success");
//
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//
//        return "main";
//    }
}
