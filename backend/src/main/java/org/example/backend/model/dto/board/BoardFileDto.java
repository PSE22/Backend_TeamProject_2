package org.example.backend.model.dto.board;

import jakarta.persistence.Lob;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardFileDto {
    private Long boardId;   // 게시글ID
    private String uuid = UUID.randomUUID().toString(); // 무작위 UUID를 생성후 UUID를 문자열로 반환 // 파일ID
    private String fileUrl; // 파일 URL
    @Lob
    private byte[] data; // BLOB 타입 필드 // 파일 첨부
}
