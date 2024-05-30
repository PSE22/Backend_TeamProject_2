package org.example.backend.service.admin;

import org.example.backend.model.dto.BoardManageDto;
import org.example.backend.model.dto.BoardEditDto;
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

    @Transactional
    public CmCode addBoard(BoardEditDto boardEditDto) {
        CmCode cmCode = new CmCode();
        cmCode.setCmCd(boardEditDto.getCmCd());
        cmCode.setUpCmCd(boardEditDto.getUpCmCd());
        cmCode.setCmCdName(boardEditDto.getCmCdName());
        cmCode.setCmCdComment(boardEditDto.getCmCdComment());
        return boardManageRepository.save(cmCode);
    }

    // 게시판 수정
    @Transactional
    public CmCode updateBoard(BoardEditDto boardEditDto) {
        CmCode cmCode = boardManageRepository.findById(boardEditDto.getCmCd()).orElseThrow(() -> new RuntimeException("Board not found"));
        cmCode.setUpCmCd(boardEditDto.getUpCmCd());
        cmCode.setCmCdName(boardEditDto.getCmCdName());
        cmCode.setCmCdComment(boardEditDto.getCmCdComment());
        return boardManageRepository.save(cmCode);
    }

}
