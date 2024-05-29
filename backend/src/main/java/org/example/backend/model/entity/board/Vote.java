package org.example.backend.model.entity.board;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.model.common.BaseTimeEntity3;
import org.example.backend.model.common.VoteIdMemberIdPk;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * packageName : org.example.backend.model.entity
 * fileName : Vote
 * author : gumiji
 * date : 5/22/24
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 5/22/24         gumiji          최초 생성
 */
@Entity
@Table(name="TB_VOTE")
@SequenceGenerator(
        name = "VOTE_ID_SEQ_GENERATOR"
        , sequenceName = "VOTE_ID_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
// soft delete
@Where(clause = "STATUS = 'Y'")
@SQLDelete(sql = "UPDATE TB_VOTE SET STATUS = 'N', DEL_DATE = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE VOTE_ID = ?")
public class Vote extends BaseTimeEntity3 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            , generator = "VOTE_ID_SEQ_GENERATOR"
    )
    private Long voteId;    // 투표 ID(PK)
    private Long boardId;   // 글번호
    private String voteName;    // 투표명
    private String voteListName;    // 투표항목명
    private Integer voteCnt = 0;    // 투표수
}
