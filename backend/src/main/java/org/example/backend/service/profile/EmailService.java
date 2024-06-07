package org.example.backend.service.profile;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.model.entity.auth.Member;
import org.example.backend.repository.auth.MemberRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private final MyRandomService myRandomService;

    final MemberRepository memberRepository;

    public void sendSimpleEmail(String to, String memberId) {
        String randomNumber = myRandomService.generatePassword(memberId);
        String text = "임시 비밀번호 : ";
        String subject = "임시 비밀번호 발급안내.";
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

    public void idAndEmailCheck(String to, String memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        if (!member.getMemberEmail().equals(to)) {
            throw new IllegalArgumentException("이메일이 일치하지 않습니다.");
        }
    }
}
