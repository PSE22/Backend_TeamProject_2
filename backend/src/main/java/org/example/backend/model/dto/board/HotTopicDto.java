package org.example.backend.model.dto.board;

/**
 * packageName : org.example.backend.model.dto.board
 * fileName : HotTopicDto
 * author : GGG
 * date : 2024-06-03
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-03         GGG          최초 생성
 */
public interface HotTopicDto {
    String getBoardId();
    String getBoard();
    String getBoardTitle();
    String getNickName();
    int getGood();
    int getTotalReplyCount();
    String getAddDate();
    String getBoCode();
}
