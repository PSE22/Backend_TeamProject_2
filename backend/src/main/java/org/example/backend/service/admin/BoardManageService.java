package org.example.backend.service.admin;

import org.example.backend.model.dto.BoardManageDto;
import org.example.backend.model.entity.CmCode;
import org.example.backend.repository.admin.BoardManageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName : org.example.backend.service.board
 * fileName : CmCodeService
 * author : GGG
 * date : 2024-05-29
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-29         GGG          최초 생성
 */
@Service
public class BoardManageService {

    @Autowired
    BoardManageRepository boardManageRepository;

//    CmCode 조회
public List<BoardManageDto> getCmCodeList() {
    return boardManageRepository.retrieveCmCode();
}

    // 게시판 추가
    @Transactional
    public CmCode addBoard(CmCode cmCode) {
        return boardManageRepository.save(cmCode);
    }
}
