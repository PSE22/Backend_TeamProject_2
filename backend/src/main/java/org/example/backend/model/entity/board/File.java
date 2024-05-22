package org.example.backend.model.entity.board;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
@Table(name = "TB_FILE")
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
@SQLDelete(sql = "UPDATE TB_FILE SET STATUS = 'N', DEL_DATE = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE UUID = ?")
public class File extends BaseTimeEntity2 {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE
//            , generator = "SEQ_TB_COUPON_CP_ID_GENERATOR"
//    )
//    uuid	varchar2(1000 byte)
//    fileUrl	varchar2(1000 byte)
//    data	blob
    private String uuid = UUID.randomUUID().toString(); // 무작위 UUID를 생성후 UUID를 문자열로 반환
    private String fileUrl;

    @Lob
    private byte[] data; // BLOB 타입 필드
}
