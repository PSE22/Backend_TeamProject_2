package org.example.backend.service.board;

import org.example.backend.model.dto.board.VoteDto;
import org.example.backend.model.entity.board.Vote;
import org.example.backend.repository.board.VoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * packageName : org.example.backend.service.board
 * fileName : VoteService
 * author : kimtaewan
 * date : 2024-05-28
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-05-28         kimtaewan          최초 생성
 */
@Service
public class VoteService {
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    ModelMapper modelMapper;

    public void updateVoteCnt(Long voteId, String memberId, Long boardId) {
        voteRepository.updateVoteCnt(voteId, memberId, boardId);
    }

    public void saveVote(Long boardId, List<VoteDto> voteDtos) {
        // 리스트에 저장된 voteDto를 반복해서 저장
        for (VoteDto voteDto : voteDtos) {
            // 받아온 boardId를 Dto에 세팅
            voteDto.setBoardId(boardId);
            // Dto를 Vote 엔티티로 변환
            Vote vote = modelMapper.map(voteDto, Vote.class);

            voteRepository.save(vote);
        }
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void voteScheduler() {
        String date = LocalDateTime.now()
                .format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd"));
        List<Vote> votes = voteRepository.findByDelDate(date);
        for (Vote vote : votes) {
            vote.setStatus("N");
            voteRepository.save(vote);
        }
    }
}
