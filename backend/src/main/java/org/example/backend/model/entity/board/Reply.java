package org.example.backend.model.entity.board;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backend.model.common.BaseTimeEntity2;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Table(name = "TB_REPLY")
//@SequenceGenerator(
//        name = "SEQ_TB_COUPON_CP_ID_GENERATOR"
//        , sequenceName = "SEQ_TB_COUPON_CP_ID"
//        , initialValue = 1
//        , allocationSize = 1
//)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "STATUS = 'Y'")
@SQLDelete(sql = "UPDATE TB_REPLY SET STATUS = 'N', DEL_DATE = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE REPLY_ID = ?")
public class Reply extends BaseTimeEntity2 {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE
//            , generator = "SEQ_TB_COUPON_CP_ID_GENERATOR"
//    )
    private Long replyId;       // 댓글 pk
    private Long boardId;       // 글번호 fk
    private String memberId;    // 댓글작성자 fk
    private Integer reReply;    // 대댓글 fk
    private String reply;       // 내용
}
