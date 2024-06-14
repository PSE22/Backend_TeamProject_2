package org.example.backend.service.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.example.backend.model.common.BoardIdMemberIdPk;
import org.example.backend.model.dto.NotifyDto;
import org.example.backend.model.dto.board.DelBoardDto;
import org.example.backend.model.dto.board.IBoardDetailDto;
import org.example.backend.model.dto.board.IBoardDto;
import org.example.backend.model.dto.board.IUserDto;
import org.example.backend.model.entity.board.*;
import org.example.backend.repository.board.*;
import org.example.backend.service.auth.NotifyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * packageName : org.example.backend.service.board
 * fileName : BoardDetailService
 * author : SAMSUNG
 * date : 2024-05-29
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-29         SAMSUNG          최초 생성
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BoardDetailService {
    private final NotifyService notifyService;
    private final BoardDetailRepository boardDetailRepository;
    private final RecommendRepository recommendRepository;
    private final ReportRepository reportRepository;
    private final ModelMapper modelMapper;
    private final FileRepository fileRepository;
    private final BoardFileRepository boardFileRepository;
    private final ReplyFileRepository replyFileRepository;
    private final ReplyRepository replyRepository;
    private final PlaceRepository placeRepository;
    private final VoteMemberRepository voteMemberRepository;
    private final VoteRepository voteRepository;
    private final ReplyService replyService;

    // 로그인된 회원 정보 조회
    public Optional<IUserDto> findMember(String memberId) {
        Optional<IUserDto> optional = boardDetailRepository.findMember(memberId);
        return optional;
    }

    // 게시글, 작성자 정보 조회
    public Optional<IBoardDto> findBoardAndMember(Long boardId) {
        Optional<IBoardDto> optional = boardDetailRepository.findBoardAndMember(boardId);
        return optional;
    }

    // 코드번호로 코드명 조회
    public Optional<IBoardDetailDto> findCmCdName(String cmCd) {
        Optional<IBoardDetailDto> optional = boardDetailRepository.findCmCdName(cmCd);
        return optional;
    }

    // 글번호로 투표 조회
    public List<Vote> findVote(Long boardId) {
        List<Vote> list = boardDetailRepository.findVote(boardId);
        return list;
    }

    // 글번호로 장소 조회 (게시글 하나당 장소 하나)
    public Optional<Place> findPlace(Long boardId) {
        log.debug("Service 문제?? boardId :: ", boardId);
        Optional<Place> optional = boardDetailRepository.findPlace(boardId);
        log.debug("Service 문제?? optional :: ", optional);

        return optional;
    }

    // 글번호로 이미지 조회
    public List<IBoardDetailDto> findBoardImg(Long boardId) {
        List<IBoardDetailDto> list = boardDetailRepository.findBoardImg(boardId);
        return list;
    }

    // 첨부파일 다운로드
    public Optional<File> fileDownload(String uuid) {
        return fileRepository.findById(uuid);
    }

    // 추천 데이터 존재하는지 확인
    public Integer existsRecommend(Long boardId, String memberId) {
        Integer recommend = boardDetailRepository.existsRecommend(boardId, memberId);
        return recommend;
    }

    // 추천 저장
    public Recommend saveRecommend(Recommend recommend) {
        Recommend recommend2 = recommendRepository.save(recommend);

        // 베스트 알림
        Long boardId = recommend2.getBoardId();
        int count = countRecommend(boardId);
        NotifyDto notifyDto = new NotifyDto();
//        notifyDto.setNotiUrl();
        if (count >= 10) {
            notifyService.createBestNotify(boardId, notifyDto);
        }

        return recommend2;
    }

    // 추천 수 카운트
    public Integer countRecommend(Long boardId) {
        Integer count = boardDetailRepository.countRecommend(boardId);
        return count;
    }

    // 글 신고 데이터 저장
    public Report saveReport(Report report) {
        Report report2 = reportRepository.save(report);
        return report2;
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        List<DelBoardDto> delData = boardDetailRepository.findByBoardId(boardId);



        // 게시글 파일 삭제
       // for (DelBoardDto item : delData) {
       //     String uuid = item.getUuid();
       //     if (uuid != null) {

        // 댓글 파일 삭제 및 게시글 파일 삭제
        List<String> uuidsToDelete = new ArrayList<>(); // 삭제할 UUID 목록
        for (DelBoardDto item : delData) {
            String uuid = item.getUuid();
            if (uuid != null) {
                replyFileRepository.deleteByUuid(uuid);

                boardFileRepository.deleteByUuid(uuid);
                uuidsToDelete.add(uuid); // UUID 목록에 추가
            }
        }

        // 파일 삭제
        for (String uuid : uuidsToDelete) {
            File file = fileRepository.findById(uuid).orElse(null);
            if (file != null) {
                fileRepository.delete(file);
            }
        }

              // 파일 삭제
    //    for (DelBoardDto item : delData) {
    //        String uuid = item.getUuid();
    //        if (uuid != null) {
    //            boardFileRepository.deleteByUuid(uuid);
    //        }
    //    }
      
      
        // 댓글 삭제
        for (DelBoardDto item : delData) {
            Long replyId = item.getReplyId();
            if (replyId != null) {
                replyService.removeReply(replyId);
            }
        }

        // 장소 삭제
        placeRepository.deleteByBoardId(boardId);
        // 투표멤버 삭제
        voteMemberRepository.deleteByBoardId(boardId);
        // 투표 삭제
        voteRepository.deleteByBoardId(boardId);
        // 추천 삭제
        recommendRepository.deleteByBoardId(boardId);
        // 게시물 삭제
        boardDetailRepository.deleteById(boardId);
    }

    public void updateBoard(Long boardId, IBoardDto boardDto) {
        Board board2 = boardDetailRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        board2.setBoardContent(boardDto.getBoardContent());
        board2.setBoardTitle(boardDto.getBoardTitle());
        boardDetailRepository.save(board2);
    }
}
