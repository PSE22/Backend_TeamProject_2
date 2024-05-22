package org.example.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName : org.example.backend.model.entity
 * fileName : Notify
 * author : kimtaewan
 * date : 2024-05-22
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-22         kimtaewan          최초 생성
 */
@Getter
@Setter
@Entity
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notifyId;
    private String memberId;
    private String notiCheck;
    private String notiContent;
    private String notiUrl;
    private String addDate;
    private String delDate;
}
