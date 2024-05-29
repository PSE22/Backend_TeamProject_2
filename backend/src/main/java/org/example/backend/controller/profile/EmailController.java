package org.example.backend.controller.profile;

import org.example.backend.model.entity.auth.Member;
import org.example.backend.service.profile.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    public String sendEmail(@PathVariable String to, @PathVariable String memberId) {
        emailService.sendSimpleEmail(to, memberId);

        return "Email sent successfully";
    }
}
