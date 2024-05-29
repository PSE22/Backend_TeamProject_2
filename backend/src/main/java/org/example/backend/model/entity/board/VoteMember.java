package org.example.backend.model.entity.board;

import jakarta.persistence.*;
import lombok.*;

/**
 * packageName : org.example.backend.model.entity.board
 * fileName : VoteMember
 * author : kimtaewan
 * date : 2024-05-28
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-28         kimtaewan          최초 생성
 */
@Entity
@Table(name="TB_VOTE_MEMBER")
@SequenceGenerator(
        name = "VOTE_MEMBER_ID_SEQ_GENERATOR"
        , sequenceName = "VOTE_MEMBER_ID_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VoteMember {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            , generator = "VOTE_MEMBER_ID_SEQ_GENERATOR"
    )
    private Long voteMemberId;
    private Long boardId;
    private String memberId;
}
