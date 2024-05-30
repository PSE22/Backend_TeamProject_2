package org.example.backend.model.dto.board;

import lombok.*;
import org.example.backend.model.entity.board.Board;
import org.example.backend.model.entity.board.File;
import org.example.backend.model.entity.board.Place;

import java.util.List;

/**
 * packageName : org.example.backend.model.dto.board
 * fileName : BoardWriteDto
 * author : gumiji
 * date : 5/30/24
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 5/30/24         gumiji          최초 생성
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardWriteDto {
    private Board board;
    private List<VoteDto> voteDtos;
    private Place place;
    private List<File> files;
    private List<BoardFileDto> boardFileDtos;

}
