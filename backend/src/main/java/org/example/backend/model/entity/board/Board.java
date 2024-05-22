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
@Table(name = "TB_BOARD")
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
@SQLDelete(sql = "UPDATE TB_BOARD SET STATUS = 'N', DEL_DATE = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE BOARD_ID = ?")
public class Board extends BaseTimeEntity2 {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE
//            , generator = "SEQ_TB_COUPON_CP_ID_GENERATOR"
//    )
    private Long boardId;           // 글번호 pk
    private String memberId;        // 글작성자 fk
    private String boardTitle;      // 제목
    private String boardContent;    // 내용
    private String nickname;        // 별명
    private Integer good;           // 추천수
    private String code;            // 분류코드
}
