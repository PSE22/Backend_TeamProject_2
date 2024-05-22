package org.example.backend.model.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * packageName : org.example.security.model.common
 * fileName : BaseTimeEntity2
 * author : kimtaewan
 * date : 2024-04-18
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-04-18         kimtaewan          최초 생성
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity2 {

    private String addDate;
    private String modDate;
    private String delDate;
    private String status;

    @PrePersist
    void OnPrePersist() {
        this.addDate = LocalDateTime.now()
                .format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.status = "Y";
    }

    @PreUpdate
    void OnPreUpdate() {
        this.modDate = LocalDateTime.now()
                .format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss"));
//        1. 업데이트 -> 인서트타임과 같이 날라올 때 널로 저장되므로 modDate를 넣어줌(자바 시점에선 과거일시 없음)
//        2. DB에 저장 : 서비스에서 상세조회함, 생성일시 가져와서 객체에 넣어줌 -> 소스가 길어짐
        this.addDate = this.modDate;
        this.status = "Y";
    }
}