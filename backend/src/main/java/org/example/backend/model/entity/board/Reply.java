package org.example.backend.model.entity.board;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backend.model.common.BaseTimeEntity2;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "TB_REPLY")
@SequenceGenerator(
        name = "TB_REPLY_SEQ_GENERATOR"
        , sequenceName = "TB_REPLY_SEQ"
        , allocationSize = 1
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "STATUS = 'Y'")
@SQLDelete(sql = "UPDATE TB_REPLY SET STATUS = 'N', DEL_DATE = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE REPLY_ID = ?")
public class Reply extends BaseTimeEntity2 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            , generator = "TB_REPLY_SEQ_GENERATOR")
    private Long replyId;        // 댓글 pk
    private Long boardId;        // 글번호 fk
    private String memberId;     // 댓글작성자 fk
    private Long reReply;        // 대댓글 fk
    private String reply;        // 내용
}
