package org.example.backend.model.dto.board;

import java.time.LocalDateTime;

public interface FreeNoticeDto {
    Long getBoardId();
    String getBoardTitle();
    String getMemberName();
    LocalDateTime getAddDate();
}
