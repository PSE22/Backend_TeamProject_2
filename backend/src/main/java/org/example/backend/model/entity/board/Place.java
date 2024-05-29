package org.example.backend.model.entity.board;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_PLACE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Place {
    @Id
    private Long boardId;       // 게시글ID pk
    private String placeName;   // 장소명
    private String lat;         // 위도
    private String lng;         // 경도
    private String address;     // 주소
}
