package org.example.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.model.common.BaseTimeEntity2;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * packageName : org.example.backend.model.entity
 * fileName : Report
 * author : gumiji
 * date : 5/22/24
 * description :    신고
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 5/22/24         gumiji          최초 생성
 */
@Entity
@Table(name="TB_REPORT")
@SequenceGenerator(
        name = "SEQ_TB_REPORT_REPORT_ID_GENERATOR"
        , sequenceName = "SEQ_TB_REPORT_REPORT_ID"
        , initialValue = 1
        , allocationSize = 1
)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
// soft delete
@Where(clause = "STATUS = 'Y'")
@SQLDelete(sql = "UPDATE TB_REPORT SET STATUS = 'N', DEL_DATE = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE REPORT_ID = ?")
public class Report extends BaseTimeEntity2 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            , generator = "SEQ_TB_REPORT_REPORT_ID_GENERATOR"
    )
    private Long reportId; // 신고 ID
    private String memberId;   // 회원 ID
    private Long boardId;  // 글번호
    private Long replyId;  // 댓글번호
    private String reportReason;   // 신고사유
}
