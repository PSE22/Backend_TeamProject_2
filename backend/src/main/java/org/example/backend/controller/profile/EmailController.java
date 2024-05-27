package org.example.backend.controller.profile;

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

    @PutMapping("/{memberId}/sendEmail")
    public String sendEmail(@PathVariable String memberId, @RequestParam(defaultValue = "") String to) { // rest api 에서 RequestParam 작동 이상함
        emailService.sendSimpleEmail(to, memberId);

        return "Email sent successfully";
    }
}
