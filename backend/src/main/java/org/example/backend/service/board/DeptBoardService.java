package org.example.backend.service.board;

import org.example.backend.model.dto.board.IClubDto;
import org.example.backend.repository.board.DeptBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName : org.example.backend.service.board
 * fileName : DeptBoardService
 * author : SAMSUNG
 * date : 2024-05-24
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-24         SAMSUNG          최초 생성
 */
@Service
public class DeptBoardService {
    @Autowired
    DeptBoardRepository deptBoardRepository;

    // 부서별 게시판 전체조회 - 공지글
    public List<IClubDto> findAllByBoard(String smcode) {
        List<IClubDto> list = deptBoardRepository.findAllByBoard(smcode);
        return list;
    }

    // 부서별 게시판 전체조회 - 일반글
    public Page<IClubDto> findAllByNotice(String smcode, Pageable pageable) {
        Page<IClubDto> page = deptBoardRepository.findAllByNotice(smcode, pageable);
        return page;
    }
}