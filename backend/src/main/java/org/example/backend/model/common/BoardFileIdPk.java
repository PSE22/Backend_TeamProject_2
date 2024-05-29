package org.example.backend.model.common;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BoardFileIdPk implements Serializable {
    private String fileBid;
    private Long boardId;
    private String uuid;
}
