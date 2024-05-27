package org.example.backend.service.profile;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * packageName : org.example.backend.service.profile
 * fileName : EmailService
 * author : sjuk2
 * date : 2024-05-23
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-23         sjuk2          최초 생성
 */
@Slf4j
@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MyRandomService myRandomService;

    public void sendSimpleEmail(String to, String memberId) {
        String randomNumber = myRandomService.generatePassword(memberId);
        String text = "임시 비밀번호 : ";
        String subject = "임시 비밀번호 발급해드립니다.";
        String modifiedText = text + randomNumber;

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, false);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(modifiedText, true);

            mailSender.send(message);
        } catch (Exception e) {
            log.debug("이메일 전송에 실패하였습니다.", e);
            throw new RuntimeException("이메일 전송에 실패하였습니다.", e);
        }
    }
}
