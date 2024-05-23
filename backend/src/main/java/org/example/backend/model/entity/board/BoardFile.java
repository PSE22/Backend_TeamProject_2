package org.example.backend.model.entity.board;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backend.model.common.BaseTimeEntity3;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Table(name = "TB_BOARD_FILE")
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
@SQLDelete(sql = "UPDATE TB_BOARD_FILE SET STATUS = 'N', DEL_DATE = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE UUID = ?")
public class BoardFile extends BaseTimeEntity3 {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE
//            , generator = "SEQ_TB_COUPON_CP_ID_GENERATOR"
//    )
//    uuid	varchar2(1000 byte)
//    boardId	number
//    uuid2	varchar2(1000 byte)
    private String uuid = UUID.randomUUID().toString();    // 게시판파일ID pk // 무작위 UUID를 생성후 UUID를 문자열로 반환
    @Id
    private Long boardId;   // 글번호 pk
    @Id
    private String uuid2 = UUID.randomUUID().toString();   // 파일ID pk
}
