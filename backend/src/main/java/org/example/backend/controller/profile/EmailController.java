package org.example.backend.controller.profile;

import org.example.backend.service.profile.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * packageName : org.example.backend.controller.profile
 * fileName : EmailController
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
@RestController
@RequestMapping("/api/auth")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PutMapping("/sendEmail/{to}/{memberId}")
    public ResponseEntity<String> sendEmail(@PathVariable String to, @PathVariable String memberId) {
        try {
            emailService.sendSimpleEmail(to, memberId);
            return ResponseEntity.ok().body("임시 비밀번호가 발급되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("임시 비밀번호 발급에 실패했습니다.");
        }
    }
}
