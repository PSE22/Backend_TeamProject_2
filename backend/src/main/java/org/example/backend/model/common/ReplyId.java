package org.example.backend.model.common;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ReplyId implements Serializable {
    private Long replyId;   // 댓글
    private String uuid;    // 파일ID
}
