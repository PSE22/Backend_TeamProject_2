package org.example.backend.service.dto;

import lombok.*;

/**
 * packageName : org.example.backend.service.dto
 * fileName : LoginRequest
 * author : sjuk2
 * date : 2024-05-22
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-22         sjuk2          최초 생성
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String memberId;
    private String memberPw;
}
