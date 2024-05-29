package org.example.backend.model.dto.board;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardFileDto {
    private String fileBid; // 게시판파일ID pk
    private Long boardId;   // 게시글ID
    private String uuid = UUID.randomUUID().toString(); // 파일ID pk // 무작위 UUID를 생성후 UUID를 문자열로 반환
}