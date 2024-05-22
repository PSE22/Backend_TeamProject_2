package org.example.backend.model.common;

import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

/**
 * packageName : org.example.backend.model.common
 * fileName : VoteIdMemberIdPk
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
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class VoteIdMemberIdPk implements Serializable {
    private Long voteId;    // 투표 ID(PK)
    private Integer voteList;   // 투표항목(PK)
}
